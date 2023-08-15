package net.runelite.client.plugins.magic;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.runelite.api.ItemID;

@Getter(AccessLevel.PUBLIC)
@RequiredArgsConstructor
public class Types {
    public enum Banks {
        NPC,
        BOOTH,
        CHEST,
    }

    public enum CastEnchant {
        OPAL_NECKLACE(ItemID.OPAL_NECKLACE, ItemID.DODGY_NECKLACE),
        OPAL_BRACELET(ItemID.OPAL_NECKLACE, ItemID.DODGY_NECKLACE);

        public int material;
        public int product;

        CastEnchant(int material, int product) {
            this.material = material;
            this.product = product;
        }
    }
}