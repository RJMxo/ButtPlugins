package net.unethicalite.plugins.Hider;

import lombok.Getter;
import net.runelite.api.ItemID;

@Getter
public enum hideType {
    Green("Green dragon leather", ItemID.GREEN_DRAGON_LEATHER,ItemID.GREEN_DRAGON_LEATHER),
    Blue("Blue dragon leather", ItemID.UNCUT_EMERALD,ItemID.EMERALD),
    Red("Red dragon leather", ItemID.UNCUT_RUBY,ItemID.RUBY),
    Black("Black dragon leather", ItemID.UNCUT_DIAMOND,ItemID.DIAMOND);

    private final String name;
    private final int leatherID;
    private final int productID;

    hideType(String name, int leatherID, int productID)
    {
        this.name = name;
        this.leatherID = leatherID;
        this.productID = productID;

    }

}