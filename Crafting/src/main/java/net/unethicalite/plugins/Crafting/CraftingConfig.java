package net.unethicalite.plugins.Crafting;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("Crafting")
public interface CraftingConfig extends Config {

    @ConfigItem(
            position = 0,
            keyName = "method",
            name = "Crafting Method",
            description = "Choose what you want to craft"
    )
    default CraftingMethods method() {
        return CraftingMethods.Green_Dhide_Body;
    }

    @ConfigItem(
            position = 1,
            keyName = "mode",
            name = "Crafting Mode",
            description = "Choose"
    )
    default CraftingTypes.Mode mode() { return CraftingTypes.Mode.FURNACE; }

    @ConfigItem(
            position = 2,
            keyName = "consumeClicks",
            name = "Consume Clicks",
            description = "Consume clicks while moving/crafting"
    )
    default boolean consumeClicks() {
        return true;
    }
}
