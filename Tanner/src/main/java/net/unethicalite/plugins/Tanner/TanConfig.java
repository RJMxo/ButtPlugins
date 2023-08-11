package net.unethicalite.plugins.Tanner;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("oneclickfurnace")
public interface TanConfig extends Config {

    @ConfigItem(
            position = 0,
            keyName = "Preference 1",
            name = "Preference 1",
            description = "Choose what you want to craft first"
    )
    default CraftingMethods method() {
        return CraftingMethods.Black_Dragon_Leather;
    }

    @ConfigItem(
            position = 1,
            keyName = "Preference 2",
            name = "Preference 2",
            description = "Choose what you want to craft first"
    )
    default CraftingMethods method2() {
        return CraftingMethods.Black_Dragon_Leather;
    }

    @ConfigItem(
            position = 2,
            keyName = "Preference 3",
            name = "Preference 3",
            description = "Choose what you want to craft first"
    )
    default CraftingMethods method3() {
        return CraftingMethods.Black_Dragon_Leather;
    }

    @ConfigItem(
            position = 3,
            keyName = "Preference 4",
            name = "Preference 4",
            description = "Choose what you want to craft first"
    )
    default CraftingMethods method4() {
        return CraftingMethods.Black_Dragon_Leather;
    }

    @ConfigItem(
            position = 4,
            keyName = "consumeClicks",
            name = "Consume Clicks",
            description = "Consume clicks while moving/crafting"
    )
    default boolean consumeClicks() {
        return true;
    }
}