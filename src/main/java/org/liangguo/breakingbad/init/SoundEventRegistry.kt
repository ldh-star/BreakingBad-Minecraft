package org.liangguo.breakingbad.init

import net.minecraft.resources.ResourceLocation
import net.minecraft.sounds.SoundEvent
import net.minecraftforge.eventbus.api.IEventBus
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries
import org.liangguo.breakingbad.R


/**
 * @author ldh
 * 时间: 2023/2/9 20:06
 * 邮箱: ldh.liangguo@outlook.com
 */
object SoundEventRegistry {

    val SOUNDS: DeferredRegister<SoundEvent> = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, R.MOD_ID)


    var TUCO = register("tuco")

    var BREAKING_BAD_THEME = register("breaking_bad_theme")

    /**
     * 只传一个id进来注册音频，一定要保证文件名是对应的
     */
    private fun register(id: String, modId: String = R.MOD_ID) = SOUNDS.register(id) {
        SoundEvent(ResourceLocation(modId, id))
    }


    fun IEventBus.registerSounds() {
        SOUNDS.register(this)
    }
}