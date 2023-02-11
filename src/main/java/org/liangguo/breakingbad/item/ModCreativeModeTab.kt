package org.liangguo.breakingbad.item

import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.item.ItemStack
import org.liangguo.breakingbad.init.ItemRegistry

/**
 * @author ldh
 * 时间: 2023/1/26 18:29
 * 邮箱: ldh.liangguo@outlook.com
 *
 * 我的世界创造模式物品栏专项
 */
object ModCreativeModeTab {

    val DRUGS_TAB: CreativeModeTab = object : CreativeModeTab("breaking_bad_mod_tab") {
        override fun makeIcon(): ItemStack {
            return ItemStack(ItemRegistry.LOGO.get())
        }
    }
}