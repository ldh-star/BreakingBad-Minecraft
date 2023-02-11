package org.liangguo.breakingbad.init

import net.minecraft.world.effect.MobEffectCategory
import net.minecraftforge.eventbus.api.IEventBus
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries
import org.liangguo.breakingbad.R
import org.liangguo.breakingbad.effect.AddictionEffect
import org.liangguo.breakingbad.effect.AddictionTimerEffect


/**
 * @author ldh
 * 时间: 2023/2/10 18:34
 * 邮箱: ldh.liangguo@outlook.com
 */
object EffectRegistry {


    val EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, R.MOD_ID)

    val ADDICTION = EFFECTS.register("addiction") {
        AddictionEffect(MobEffectCategory.NEUTRAL, 3344556)
    }

    val ADDICITION_TIMER = EFFECTS.register("addicition_timer") {
        AddictionTimerEffect(MobEffectCategory.NEUTRAL, 0)
    }

    fun IEventBus.registerEffects() {
        EFFECTS.register(this)
    }

}