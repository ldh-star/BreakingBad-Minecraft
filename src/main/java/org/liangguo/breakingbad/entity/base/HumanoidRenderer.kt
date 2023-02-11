package org.liangguo.breakingbad.entity.base

import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.model.PlayerModel
import net.minecraft.client.model.geom.ModelLayers
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.entity.EntityRendererProvider
import net.minecraft.client.renderer.entity.HumanoidMobRenderer
import net.minecraft.resources.ResourceLocation
import org.liangguo.breakingbad.R
import org.liangguo.breakingbad.entity.renderer.HumanoidCapeLayer


/**
 * @author ldh
 * 时间: 2023/2/6 21:58
 * 邮箱: ldh.liangguo@outlook.com
 *
 * 类玩家型的渲染器，默认玩家类型的模型
 */
class HumanoidRenderer<T : HumanoidEntity> : HumanoidMobRenderer<T, PlayerModel<T>> {

    private val textureId: String

    private val scaleX: Float

    private val scaleY: Float

    private val scaleZ: Float


    constructor(
        renderManager: EntityRendererProvider.Context,
        textureId: String,
        modelProvider: PlayerModel<T> = PlayerModel<T>(renderManager.bakeLayer(ModelLayers.PLAYER), false),
        shadowRadius: Float = 0.5f,
        headScaleX: Float = 1f,
        headScaleY: Float = 1f,
        headScaleZ: Float = 1f,
        scaleX: Float = 1f,
        scaleY: Float = 1f,
        scaleZ: Float = 1f,
    ) : super(
        renderManager,
        modelProvider,
        shadowRadius,
        headScaleX,
        headScaleY,
        headScaleZ,
    ) {
        this.textureId = textureId
        this.scaleX = scaleX
        this.scaleY = scaleY
        this.scaleZ = scaleZ
    }

    constructor(
        renderManager: EntityRendererProvider.Context,
        textureId: String,
        modelProvider: PlayerModel<T> = PlayerModel(renderManager.bakeLayer(ModelLayers.PLAYER), false),
        shadowRadius: Float = 0.5f,
        headScale: Float = 1f,
        scale: Float = 1f,
    ) : super(
        renderManager,
        modelProvider,
        shadowRadius,
        headScale,
        headScale,
        headScale
    ) {
        this.textureId = textureId
        this.scaleX = scale
        this.scaleY = scale
        this.scaleZ = scale
    }

    init {
        addLayer(HumanoidCapeLayer(this))
    }


    override fun render(
        mob: T,
        p_115456_: Float,
        p_115457_: Float,
        p_115458_: PoseStack,
        p_115459_: MultiBufferSource,
        p_115460_: Int
    ) {
        p_115458_.scale(scaleX, scaleY, scaleZ)
        super.render(mob, p_115456_, p_115457_, p_115458_, p_115459_, p_115460_)
    }

    override fun getTextureLocation(entity: T): ResourceLocation {
        return ResourceLocation(
            R.MOD_ID,
            "textures/entity/${textureId}.png"
        )
    }




}