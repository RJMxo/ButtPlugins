package net.unethicalite.miner

import com.google.inject.Provides
import net.runelite.api.GameState
import net.runelite.api.NPC
import net.runelite.api.TileObject
import net.runelite.api.coords.WorldPoint
import net.runelite.api.events.ConfigButtonClicked
import net.runelite.client.config.ConfigManager
import net.runelite.client.eventbus.Subscribe
import net.runelite.client.plugins.PluginDescriptor
import net.unethicalite.api.commons.Time
import net.unethicalite.api.entities.NPCs
import net.unethicalite.api.entities.Players
import net.unethicalite.api.entities.TileObjects
import net.unethicalite.api.items.Bank
import net.unethicalite.api.items.Inventory
import net.unethicalite.api.movement.Movement
import net.unethicalite.api.movement.Reachable
import net.unethicalite.api.plugins.LoopedPlugin
import net.unethicalite.api.utils.MessageUtils
import net.unethicalite.client.Static
import net.unethicalite.miner.util.Calculation
import net.unethicalite.miner.util.Functions
import net.unethicalite.miner.util.Log
import net.unethicalite.miner.util.ReflectBreakHandler
import org.pf4j.Extension
import java.time.Duration
import java.time.Instant
import javax.inject.Inject
import net.runelite.api.*
import net.unethicalite.api.items.DepositBox
import net.unethicalite.api.packets.WidgetPackets
import net.runelite.api.MenuAction
import net.runelite.api.widgets.Widget
import net.runelite.api.widgets.WidgetInfo

@Extension
@PluginDescriptor(
    name = "RJM-Mining",
    description = "Automatic Miner",
    tags = ["mining"]
)
class MinerPlugin : LoopedPlugin() {

    @Inject
    lateinit var config: MinerConfig

    @Inject
    lateinit var functions: Functions

    @Inject
    lateinit var calculation: Calculation

    @Inject
    lateinit var chinBreakHandler: ReflectBreakHandler

    var sleepLength: Long = -1
    var startLocation: WorldPoint? = null

    private var startTime: Instant = Instant.now()

    private val runtime: Duration get() = Duration.between(startTime, Instant.now())

    var startPlugin: Boolean = false

    companion object : Log()

    @Provides
    fun provideConfig(configManager: ConfigManager): MinerConfig {
        return configManager.getConfig(MinerConfig::class.java)
    }


    override fun startUp() {
        log.info("${this::class.simpleName} started at $startTime")
        chinBreakHandler.registerPlugin(this)
        reset()
    }

    override fun shutDown() {
        log.info("${this::class.simpleName} stopped at ${Instant.now()} with runtime $runtime")
        chinBreakHandler.unregisterPlugin(this)
        reset()
    }

    override fun loop(): Int {
        val BankTile = WorldPoint(config.ChosenBank().X, config.ChosenBank().Y, config.ChosenBank().Z)
        if (!startPlugin || chinBreakHandler.isBreakActive(this)) return 100

        with(functions) {
            when (getState()) {
                States.HANDLE_BREAK -> {
                    MessageUtils.addMessage("Attempting to break")
                    chinBreakHandler.startBreak(this@MinerPlugin)
                }

                States.MINE_ROCK -> {
                    val tree: TileObject? =
                        TileObjects.getNearest { config.rockType().rockId.contains(it.id) && it.distanceTo(startLocation) < config.radius() }
                    tree?.let {
                        it.interact("Mine")
                        Time.sleepUntil({ Players.getLocal().isAnimating }, 1350)
                    }
                }

                States.DROP_INVENTORY -> {
                    for (Item in Inventory.getAll { it.id == config.rockType().item || "Uncut" in it.name }) {
                        Item.interact("Drop")
                        Time.sleep(sleepDelay())
                        States.BANK
                    }
                }
                States.WALKTOBANK -> {
                    Movement.walkTo(BankTile)
                    Time.sleepUntil({ (BankTile.distanceTo(Players.getLocal().getWorldLocation()) <= 2) }, 1350)
                }

                States.BANK -> {
                    var banker: TileObject? = TileObjects.getNearest { it.hasAction("Deposit") }
                    if (banker != null) {
                        banker.interact("Deposit")
                        Time.sleepUntil({ DepositBox.isOpen() }, 1500)
                        Static.getClient().interact(1, 57, 1, 12582914)
                        Time.sleepUntil({ Inventory.isEmpty() }, 1500)
                        DepositBox.close()
                        }
                    if (banker == null || !Reachable.isInteractable(banker)) {
                        Movement.walkTo(config.ChosenBank().X, config.ChosenBank().Y, config.ChosenBank().Z)
                    }
                }

                States.WALKHOME -> {
                    val StartSpot = WorldPoint(config.mineLocation().X, config.mineLocation().Y, config.mineLocation().Z)
                    Movement.walkTo(StartSpot)
                    Time.sleepUntil({ (StartSpot.distanceTo(Players.getLocal().getWorldLocation()) <= 2) }, 1350)

                }

            }
            return sleepDelay().toInt()
        }
    }

    private fun reset() {
        sleepLength = -1
        startPlugin = false
        startLocation = null
    }

    @Subscribe
    private fun onConfigButtonPressed(configButtonClicked: ConfigButtonClicked) {
        if (!configButtonClicked.group.equals("MinerConfig", ignoreCase = true) || Static.getClient().gameState != GameState.LOGGED_IN || Players.getLocal() == null) return
        if (configButtonClicked.key.equals("startHelper", ignoreCase = true)) {
            startPlugin = !startPlugin
            startLocation = Players.getLocal().worldLocation
            MessageUtils.addMessage("Plugin running: $startPlugin")
            if(startPlugin)
                chinBreakHandler.startPlugin(this)
            else
                chinBreakHandler.stopPlugin(this)
        }
    }

}