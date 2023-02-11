package org.liangguo.breakingbad.utils

import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraftforge.registries.RegistryObject


/**
 * @author ldh
 * 时间: 2023/1/28 13:48
 * 邮箱: ldh.liangguo@outlook.com
 */

/**
 * 将模组中自定义的Item通过该扩展函数变成ItemStack
 *
 * @param count 这个物品的数量
 */
fun RegistryObject<out Item>.toStack(count: Int) = ItemStack(get(), count)

/**
 * 将Item通过该扩展函数变成ItemStack
 */
fun Item.toStack(count: Int = 1) = ItemStack(this, count)