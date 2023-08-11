package net.unethicalite.plugins.Tanner;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.runelite.api.ItemID;

@Getter(AccessLevel.PUBLIC)
@RequiredArgsConstructor
public enum CraftingMethods {
    Green_Dragon_Leather(ItemID.GREEN_DRAGONHIDE,ItemID.GREEN_DRAGON_LEATHER,21233792),
    Blue_Dragon_Leather(ItemID.BLUE_DRAGONHIDE,ItemID.BLUE_DRAGON_LEATHER,21233793),
    Red_Dragon_Leather(ItemID.RED_DRAGONHIDE,ItemID.RED_DRAGON_LEATHER,21233794),
    Black_Dragon_Leather(ItemID.BLACK_DRAGONHIDE,ItemID.BLACK_DRAGON_LEATHER,21233795);


    public int material;
    public int product;
    public int opcode;


    CraftingMethods(int material, int product, int opcode) {
        this.material = material;
        this.product = product;
        this.opcode = opcode;
    }

}
