package org.liangguo.breakingbad.entity.renderer

import net.minecraft.client.model.ZombieModel
import net.minecraft.client.model.geom.ModelLayers
import net.minecraft.client.renderer.entity.EntityRendererProvider
import net.minecraft.client.renderer.entity.HumanoidMobRenderer
import net.minecraft.resources.ResourceLocation
import org.liangguo.breakingbad.R
import org.liangguo.breakingbad.entity.mobs.UndeadZombie


/**
 * @author ldh
 * 时间: 2023/2/6 20:52
 * 邮箱: ldh.liangguo@outlook.com
 */
class UndeadZombieRenderer(
    renderManager: EntityRendererProvider.Context,
    modelProvider: ZombieModel<UndeadZombie> = ZombieModel(renderManager.bakeLayer(ModelLayers.ZOMBIE)),
    shadowRadius: Float = 0.5f,
    scaleX: Float = 1f,
    scaleY: Float = 1f,
    scaleZ: Float = 1f,
) : HumanoidMobRenderer<UndeadZombie, ZombieModel<UndeadZombie>>(
    renderManager,
    modelProvider,
    shadowRadius,
    scaleX,
    scaleY,
    scaleZ
) {

    override fun getTextureLocation(zombie: UndeadZombie): ResourceLocation {
        return ResourceLocation(
            R.MOD_ID,
            "textures/entity/gus_fring_zombie.png"
        )
    }



}