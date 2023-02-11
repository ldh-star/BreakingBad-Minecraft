package org.liangguo.breakingbad.utils

import it.unimi.dsi.fastutil.ints.Int2ObjectMap
import net.minecraft.world.entity.npc.VillagerTrades
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import net.minecraft.world.item.trading.MerchantOffer


/**
 * @author ldh
 * 时间: 2023/1/28 10:45
 * 邮箱: ldh.liangguo@outlook.com
 */

const val LOW_TIER_PRICE_MULTIPLIER = 0.02f
const val MEDIUM_TIER_PRICE_MULTIPLIER = 0.05f
const val HIGH_TIER_PRICE_MULTIPLIER = 0.2f

/**
 * 1个绿宝石
 */
val AN_EMERALD: ItemStack
    get() = ItemStack(Items.EMERALD, 1)

/**
 * 添加新的村民交易规则
 *
 * @param villagerLevel 村民等级，范围 1~5，等级高能买到好东西
 * @param inputItem 购买物品所花费的物品及数量，即价钱
 * @param outputItem 交易成功后所得到的物品，默认是一个绿宝石
 * @param extraInputItem 当交易需要花两种物品来购买时，可以使用此参数来作为第二种购买货币
 * @param uses 我暂时也不太清楚这个参数是干嘛的
 * @param maxUses 失效前可交易次数
 * @param xp 村民获得的经验值
 * @param priceMultiplier 价格乘数
 * @param demand 需求，我暂时也不太清楚这个参数是干嘛的
 */
fun Int2ObjectMap<MutableList<VillagerTrades.ItemListing>>.newTrade(
    villagerLevel: Int,
    inputItem: ItemStack,
    outputItem: ItemStack = AN_EMERALD,
    extraInputItem: ItemStack = ItemStack.EMPTY,
    uses: Int = 0,
    maxUses: Int = 10,
    xp: Int = defaultXp(villagerLevel, outputItem.item != AN_EMERALD.item),
    priceMultiplier: Float = MEDIUM_TIER_PRICE_MULTIPLIER,
    demand: Int = 0,
) {
    get(villagerLevel).add { _, _ ->
        MerchantOffer(
            inputItem,
            extraInputItem,
            outputItem,
            uses,
            maxUses,
            xp,
            priceMultiplier,
            demand
        )
    }
}

/**
 * 不想手动设置交易时的经验值可以用这个默认的
 *
 * @param level 村民等级
 * @param isBuy 是买入还是卖出，即 true 表示消费绿宝石， false 表示得到绿宝石
 */
fun defaultXp(level: Int, isBuy: Boolean): Int {
    var xp = when (level) {
        1 -> 1
        2 -> 5
        3 -> 10
        4 -> 15
        else -> 30
    }
    if (isBuy && xp < 30) xp = xp shl 1
    return xp
}