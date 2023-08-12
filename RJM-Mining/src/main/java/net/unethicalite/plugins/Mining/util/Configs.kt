package net.unethicalite.plugins.Mining.util

import net.runelite.api.ItemID

enum class Rock(val item: Int, var rockId: IntArray) {
    CLAY(ItemID.CLAY, intArrayOf(11364, 11365)),
    COPPER(ItemID.IRON_ORE, intArrayOf(11364, 11365)),
    TIN(ItemID.IRON_ORE, intArrayOf(11364, 11365)),
    IRON(ItemID.IRON_ORE, intArrayOf(11364, 11365)),
    GOLD(ItemID.IRON_ORE, intArrayOf(11364, 11365)),
    MITHRIL(ItemID.IRON_ORE, intArrayOf(11364, 11365)),
    ADAMANTITE(ItemID.IRON_ORE, intArrayOf(11364, 11365)),
    RUNE(ItemID.IRON_ORE, intArrayOf(11364, 11365));

}