package org.liangguo.breakingbad.loot

import net.minecraftforge.eventbus.api.IEventBus
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries
import org.liangguo.breakingbad.R


/**
 * @author ldh
 * 时间: 2023/2/1 22:02
 * 邮箱: ldh.liangguo@outlook.com
 */
object ModLootModifiers {

    val LOOT_MODIFIER_SERIALIZERS =
        DeferredRegister.create(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, R.MOD_ID)

    val ADD_ITEM = LOOT_MODIFIER_SERIALIZERS.register("add_item", AddItemModifier.CODEC)



    fun IEventBus.registerLootModifiers() {
        LOOT_MODIFIER_SERIALIZERS.register(this)
    }

}