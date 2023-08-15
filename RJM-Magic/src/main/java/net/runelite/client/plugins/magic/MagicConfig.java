package net.runelite.client.plugins.magic;
import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("oneclickglassblowing")
public interface MagicConfig extends Config {

    @ConfigItem(
            position = 1,
            keyName = "CastEnchant",
            name = "CastEnchant",
            description = "Choose item to enchant"
    )
    default Types.CastEnchant CastEnchant() {
        return Types.CastEnchant.OPAL_NECKLACE;
    }

    @ConfigItem(
            position = 2,
            keyName = "bankType",
            name = "Bank Type",
            description = "Choose"
    )
    default Types.Banks bankType() { return Types.Banks.CHEST; }

    @ConfigItem(
            position = 3,
            keyName = "bankID",
            name = "Bank ID",
            description = "Input bank ID, supports chests/NPCs/Booths. Default is Fossil Island north Bank"
    )
    default int bankID()
    {
        return 30796;
    }
}
