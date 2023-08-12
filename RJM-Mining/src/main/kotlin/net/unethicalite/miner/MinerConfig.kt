package net.unethicalite.miner

import net.runelite.client.config.*
import net.unethicalite.miner.util.Rock
import net.unethicalite.miner.util.Banks
import net.unethicalite.miner.util.MineLocation

@ConfigGroup("MinerConfig")
interface MinerConfig : Config {

    companion object {
        @ConfigSection(
            name = "Sleep Delays",
            description = "",
            position = 3,
            keyName = "sleepDelays",
            closedByDefault = true
        )
        const val sleepDelays: String = "Sleep Delays"

        @ConfigSection(
            name = "Rock Types",
            description = "",
            position = 10,
            keyName = "rockTypes",
            closedByDefault = true
        )
        const val rockType: String = "Rock Type"

        @ConfigSection(
            name = "Banking Settings",
            description = "",
            position = 15,
            keyName = "Banking",
            closedByDefault = true
        )
        const val Banking: String = "Banking Settings"
    }


    @Range(min = 0, max = 160)
    @ConfigItem(keyName = "sleepMin", name = "Sleep Min", description = "", position = 4, section = sleepDelays)
    @JvmDefault

    fun sleepMin(): Int {
        return 60
    }

    @Range(min = 0, max = 160)
    @ConfigItem(keyName = "sleepMax", name = "Sleep Max", description = "", position = 5, section = sleepDelays)
    @JvmDefault

    fun sleepMax(): Int {
        return 350
    }

    @Range(min = 0, max = 160)
    @ConfigItem(keyName = "sleepTarget", name = "Sleep Target", description = "", position = 6, section = sleepDelays)
    @JvmDefault

    fun sleepTarget(): Int {
        return 100
    }

    @Range(min = 0, max = 160)
    @ConfigItem(
        keyName = "sleepDeviation",
        name = "Sleep Deviation",
        description = "",
        position = 7,
        section = sleepDelays
    )
    @JvmDefault
    fun sleepDeviation(): Int {
        return 10
    }

    @ConfigItem(
        keyName = "sleepWeightedDistribution",
        name = "Sleep Weighted Distribution",
        description = "Shifts the random distribution towards the lower end at the target, otherwise it will be an even distribution",
        position = 8,
        section = sleepDelays
    )
    @JvmDefault

    fun sleepWeightedDistribution(): Boolean {
        return false
    }

    @ConfigItem(
        keyName = "Location",
        name = "Mine Location",
        description = "Choose Location to Mine",
        position = 11,
        section = rockType
    )
    @JvmDefault
    fun mineLocation(): MineLocation {
        return MineLocation.RIMMINGTON
    }

    @ConfigItem(
        keyName = "tree",
        name = "Rock Type",
        description = "Choose Rock to Mine",
        position = 12,
        section = rockType
    )
    @JvmDefault
    fun rockType(): Rock {
        return Rock.RUNITE
    }
    @ConfigItem(
        keyName = "radius",
        name = "Radius",
        description = "Radius from start location",
        position = 13,
        section = rockType
    )
    @JvmDefault
    fun radius(): Int {
        return 20
    }
    @ConfigItem(
        keyName = "Bank",
        name = "Choose Bank",
        description = "Select Which Bank to run to",
        position = 14,
        section = Banking
    )
    @JvmDefault
    fun ChosenBank(): Banks {
        return Banks.FALLY_EAST
    }

    @ConfigItem(
        keyName = "toggleBank",
        name = "Toggle Banking",
        description = "Toggle Banking",
        position = 15,
        section = Banking
    )
    @JvmDefault
    fun toggleBank(): Boolean {
        return false
    }
    @ConfigItem(
        keyName = "startHelper",
        name = "Start / Stop",
        description = "Press button to start / stop plugin",
        position = 20
    )
    @JvmDefault
    fun startButton(): Button? {
        return Button()
    }

}


