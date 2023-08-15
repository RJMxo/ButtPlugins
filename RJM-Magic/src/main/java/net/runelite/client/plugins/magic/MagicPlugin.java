package net.runelite.client.plugins.magic;

import java.util.Arrays;
import javax.inject.Inject;

import com.google.inject.Provides;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.events.*;
import net.runelite.api.queries.BankItemQuery;
import net.runelite.api.queries.GameObjectQuery;
import net.runelite.api.queries.NPCQuery;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.api.widgets.WidgetItem;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import org.pf4j.Extension;
import net.unethicalite.api.items.Inventory;
import net.runelite.api.Point;
import net.runelite.api.events.ClientTick;
import net.runelite.api.events.GameTick;
import net.runelite.api.events.MenuOptionClicked;
import net.runelite.api.NPC;
import net.unethicalite.api.items.Bank;
import net.unethicalite.api.magic.Magic;
import net.unethicalite.api.magic.SpellBook;
import net.unethicalite.api.entities.Players;


import static net.runelite.api.AnimationID.*;

@Extension
@PluginDescriptor(
        name = "RJM-Magic",
        enabledByDefault = false,
        description = "One Click Glassblowing/Superglass Make. Default bank is North of Fossil Island. Check Discord for setup info")
@Slf4j
public class MagicPlugin extends Plugin {

    @Inject
    private Client client;

    @Inject
    private MagicConfig config;

    @Provides
    MagicConfig getConfig(ConfigManager configManager)
    {
        return configManager.getConfig(MagicConfig.class);
    }

    private int bankingState = 1;
    private int castingState = 1;
    private int timeout;

    protected void startUp() throws Exception {
        bankingState = 1;
        timeout = 0;
    }

    @Subscribe
    public void onGameTick(GameTick event) {
        //cballs slow as fuck!
        if (client.getLocalPlayer().getAnimation()==899 || client.getLocalPlayer().getAnimation()==827) timeout = 10;
        if (timeout>0) timeout--;
        if (getInventoryItem(config.CastEnchant().material)==null) timeout = 0;
    }

    @Subscribe
    public void onMenuOptionClicked(MenuOptionClicked event) {
        if (event.getMenuOption().equals("<col=00ff00>RJM"))
            handleClick(event);
    }

    @Subscribe
    private void onClientTick(ClientTick event) {
        if (this.client.getLocalPlayer() == null || client.getGameState() != GameState.LOGGED_IN) return;

        String text= "<col=00ff00>RJM";
        client.insertMenuItem(text, "", MenuAction.UNKNOWN.getId(), 0, 0, 0, true);
        client.setTempMenuEntry(Arrays.stream(client.getMenuEntries()).filter(x->x.getOption().equals(text)).findFirst().orElse(null));
    }

    private void handleClick(MenuOptionClicked event) {

        if (bankOpen()) {
            //set bank quantity to 1
            if (client.getVarbitValue(6590) != 0) {
                setMenuEntry(event, createMenuEntry(1, MenuAction.CC_OP, -1, 786460, false));
                return;
            }
            //set bank tab to main tab
            if (client.getVarbitValue(Varbits.CURRENT_BANK_TAB) != 0) {
                setMenuEntry(event, createMenuEntry(1, MenuAction.CC_OP, 10, WidgetInfo.BANK_TAB_CONTAINER.getId(), false));
                return;
            }

            switch (bankingState) {
                case 1:
                    if (getInventoryItem(config.CastEnchant().product) == null){
                        bankingState = 2;
                        return;
                    }
                    setMenuEntry(event, depositProduct());
                    return;
                case 2:
                    client.addChatMessage(ChatMessageType.BROADCAST,"","Withdrawing All Products","");
                    setMenuEntry(event, withdrawMaterial());
                    if(getInventoryItem(config.CastEnchant().material) != null){
                        Bank.close();
                    }
                    return;
            }
        }
        if (!bankOpen() && getInventoryItem(config.CastEnchant().material) == null) {
            setMenuEntry(event, bank());
        }
        if (!bankOpen() && getInventoryItem(config.CastEnchant().material) != null && !Players.getLocal().isAnimating()) {
            setMenuEntry(event, usePipeOnGlass());
        }

    }
    private MenuEntry usePipeOnGlass(){
        final Widget widget = client.getWidget(WidgetInfo.SPELL_LVL_1_ENCHANT);
        client.setSelectedSpellName("<col=00ff00>" + "Lvl-1 Enchant" + "</col>");
        client.setSelectedSpellWidget(widget.getId());
        client.setSelectedSpellChildIndex(-1);
        Widget mat = getInventoryItem(config.CastEnchant().material);
        //setSelectedInventoryItem(widget);
        return createMenuEntry(0, MenuAction.WIDGET_TARGET_ON_WIDGET, mat.getIndex(), 9764864, true);
    }
    private MenuEntry CastSpell(){
        client.addChatMessage(ChatMessageType.BROADCAST,"","123123k","");
        return createMenuEntry(1, MenuAction.CC_OP, -1, WidgetInfo.SPELL_LVL_1_ENCHANT.getId(), false);
    }
    private void setSelectedInventoryItem(Widget item) {
        client.setSelectedSpellWidget(WidgetInfo.INVENTORY.getId());
        client.setSelectedSpellChildIndex(item.getIndex());
        client.setSelectedSpellItemId(item.getItemId());
    }
    private MenuEntry bank() {
        bankingState = 1;
        NPC banker = getNPC("Banker");
        client.addChatMessage(ChatMessageType.BROADCAST,"","Attempting to open Bank","");
        return createMenuEntry(banker.getIndex(), MenuAction.NPC_THIRD_OPTION, 0, 0, false);
    }
    private MenuEntry withdrawMaterial() {
        int bankIndex = getBankIndex(config.CastEnchant().material);
        return createMenuEntry(7, MenuAction.CC_OP_LOW_PRIORITY, bankIndex, WidgetInfo.BANK_ITEM_CONTAINER.getId(), false);
    }
    private MenuEntry openBank(){
        if (config.bankType() == Types.Banks.BOOTH) {
            GameObject gameObject = getGameObject(config.bankID());
            return createMenuEntry(
                    gameObject.getId(),
                    MenuAction.GAME_OBJECT_SECOND_OPTION,
                    getLocation(gameObject).getX(),
                    getLocation(gameObject).getY(),
                    false);
        }

        if (config.bankType() == Types.Banks.CHEST) {
            GameObject gameObject = getGameObject(config.bankID());
            return createMenuEntry(
                    gameObject.getId(),
                    MenuAction.GAME_OBJECT_FIRST_OPTION,
                    getLocation(gameObject).getX(),
                    getLocation(gameObject).getY(),
                    false);
        }

        if (config.bankType() == Types.Banks.NPC) {
            NPC npc = getNpc(config.bankID());
            return createMenuEntry(
                    npc.getIndex(),
                    MenuAction.NPC_THIRD_OPTION,
                    0,
                    0,
                    false);
        }
        return null;
    }
    private NPC getNPC(String name) {
        return new NPCQuery()
                .nameEquals(name)
                .result(client)
                .nearestTo(client.getLocalPlayer());
    }
    private MenuEntry depositProduct() {
        Widget item = getInventoryItem(config.CastEnchant().product);
        return createMenuEntry(8, MenuAction.CC_OP_LOW_PRIORITY, item.getIndex(), WidgetInfo.BANK_INVENTORY_ITEMS_CONTAINER.getId(), false);
    }
    private int getBankIndex(int id){
        WidgetItem bankItem = new BankItemQuery()
                .idEquals(id)
                .result(client)
                .first();
        if (bankItem == null) return -1;
        return bankItem.getWidget().getIndex();
    }
    private Widget getInventoryItem(int id) {
        client.runScript(6009, 9764864, 28, 1, -1); //rebuild inventory ty pajeet
        Widget inventoryWidget = client.getWidget(WidgetInfo.INVENTORY);
        Widget bankInventoryWidget = client.getWidget(WidgetInfo.BANK_INVENTORY_ITEMS_CONTAINER);
        if (bankInventoryWidget!=null && !bankInventoryWidget.isHidden())
        {
            return getWidgetItem(bankInventoryWidget,id);
        }
        if (inventoryWidget!=null) //if hidden check exists then you can't access inventory from any tab except inventory
        {
            return getWidgetItem(inventoryWidget,id);
        }
        return null;
    }

    private Widget getWidgetItem(Widget widget,int id) {
        for (Widget item : widget.getDynamicChildren())
        {
            if (item.getItemId() == id)
            {
                return item;
            }
        }
        return null;
    }

    private GameObject getGameObject(int ID) {
        return new GameObjectQuery()
                .idEquals(ID)
                .result(client)
                .nearestTo(client.getLocalPlayer());
    }

    private Point getLocation(TileObject tileObject) {
        if (tileObject == null) {
            return new Point(0, 0);
        }
        if (tileObject instanceof GameObject) {
            return ((GameObject) tileObject).getSceneMinLocation();
        }
        return new Point(tileObject.getLocalLocation().getSceneX(), tileObject.getLocalLocation().getSceneY());
    }

    private NPC getNpc(int id) {
        return new NPCQuery()
                .idEquals(id)
                .result(client)
                .nearestTo(client.getLocalPlayer());
    }

    private boolean bankOpen() {
        return client.getItemContainer(InventoryID.BANK) != null;
    }

    public MenuEntry createMenuEntry(int identifier, MenuAction type, int param0, int param1, boolean forceLeftClick) {
        return client.createMenuEntry(0).setOption("").setTarget("").setIdentifier(identifier).setType(type)
                .setParam0(param0).setParam1(param1).setForceLeftClick(forceLeftClick);
    }

    private void setMenuEntry(MenuOptionClicked event, MenuEntry menuEntry){
        event.setId(menuEntry.getIdentifier());
        event.setMenuAction(menuEntry.getType());
        event.setParam0(menuEntry.getParam0());
        event.setParam1(menuEntry.getParam1());
    }
}