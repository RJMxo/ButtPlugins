package net.unethicalite.miner.util

import net.runelite.api.ItemID


enum class Rock(val item: Int, var rockId: IntArray) {
    IRON(ItemID.IRON_ORE, intArrayOf(11364, 11365)),
    CLAY(ItemID.CLAY, intArrayOf(11362, 11363)),
    COPPER(ItemID.COPPER_ORE, intArrayOf(10943, 11161)),
    TIN(ItemID.TIN_ORE, intArrayOf(11364, 11365)),
    SILVER(ItemID.SILVER_ORE, intArrayOf(11364, 11365)),
    COAL(ItemID.COAL, intArrayOf(11364, 11365)),
    GOLD(ItemID.GOLD_ORE, intArrayOf(11364, 11365)),
    MITHRIL(ItemID.MITHRIL_ORE, intArrayOf(11364, 11365)),
    ADAMANTITE(ItemID.ADAMANTITE_ORE, intArrayOf(11364, 11365)),
    RUNITE(ItemID.RUNITE_ORE, intArrayOf(11364, 11365));
}

enum class Banks(val id: Int, var X: Int, var Y: Int, var Z: Int) {
    FALLY_WEST(123,11364, 11365, 0),
    PORT_SARIM(26254,3045, 3234, 0),
    FALLY_EAST(123,11364, 11365, 0) ;
}

enum class MineLocation(var X: Int, var Y: Int, var Z: Int) {
    RIMMINGTON(2969,3239,0);
}