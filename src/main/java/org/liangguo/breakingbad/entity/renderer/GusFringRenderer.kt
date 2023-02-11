package org.liangguo.breakingbad.entity.renderer

import net.minecraft.client.model.HumanoidModel
import net.minecraft.client.model.PlayerModel
import net.minecraft.client.model.geom.ModelLayers
import net.minecraft.client.renderer.entity.EntityRendererProvider
import net.minecraft.client.renderer.entity.HumanoidMobRenderer
import net.minecraft.resources.ResourceLocation
import org.liangguo.breakingbad.R
import org.liangguo.breakingbad.entity.mobs.GusFring


/**
 * @author ldh
 * 时间: 2023/2/6 21:43
 * 邮箱: ldh.liangguo@outlook.com
 */

class GusFringRenderer(
    renderManager: EntityRendererProvider.Context,
    modelProvider: HumanoidModel<GusFring> = PlayerModel(renderManager.bakeLayer(ModelLayers.PLAYER), false),
    shadowRadius: Float = 0.5f,
    scaleX: Float = 1f,
    scaleY: Float = 1f,
    scaleZ: Float = 1f,
) : HumanoidMobRenderer<GusFring, HumanoidModel<GusFring>>(
    renderManager,
    modelProvider,
    shadowRadius,
    scaleX,
    scaleY,
    scaleZ
) {

    override fun getTextureLocation(entity: GusFring): ResourceLocation {
        return ResourceLocation(
            R.MOD_ID,
            "textures/entity/${GusFring.ENTITY_ID}.png"
        )
    }
}