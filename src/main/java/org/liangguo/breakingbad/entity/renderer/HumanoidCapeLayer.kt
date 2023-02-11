package org.liangguo.breakingbad.entity.renderer

import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.math.Vector3f
import net.minecraft.client.model.PlayerModel
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.entity.RenderLayerParent
import net.minecraft.client.renderer.entity.layers.RenderLayer
import net.minecraft.client.renderer.texture.OverlayTexture
import net.minecraft.util.Mth
import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.item.Items
import org.liangguo.breakingbad.entity.base.HumanoidEntity


/**
 * @author ldh
 * 时间: 2023/2/7 14:09
 * 邮箱: ldh.liangguo@outlook.com
 */
class HumanoidCapeLayer<T : HumanoidEntity, M : PlayerModel<T>>(parent: RenderLayerParent<T, M>) :
    RenderLayer<T, M>(parent) {
    override fun render(
        p_116615_: PoseStack,
        p_116616_: MultiBufferSource,
        p_116617_: Int,
        entity: T,
        p_116619_: Float,
        p_116620_: Float,
        p_116621_: Float,
        p_116622_: Float,
        p_116623_: Float,
        p_116624_: Float
    ) {
        if (!entity.isInvisible && entity.cloakTexture != null) {
            val itemStack = entity.getItemBySlot(EquipmentSlot.CHEST)
            if (!itemStack.`is`(Items.ELYTRA)) {
                p_116615_.pushPose()
                p_116615_.translate(0.0, 0.0, 0.125)
                val d0 = Mth.lerp(p_116621_.toDouble(), entity.xCloakO, entity.xCloak) - Mth.lerp(
                    p_116621_.toDouble(),
                    entity.xo,
                    entity.x
                )
                val d1 = Mth.lerp(p_116621_.toDouble(), entity.yCloakO, entity.yCloak) - Mth.lerp(
                    p_116621_.toDouble(),
                    entity.yo,
                    entity.y
                )
                val d2 = Mth.lerp(p_116621_.toDouble(), entity.zCloakO, entity.zCloak) - Mth.lerp(
                    p_116621_.toDouble(),
                    entity.zo,
                    entity.z
                )
                val f = entity.yBodyRotO + (entity.yBodyRot - entity.yBodyRotO)
                val d3 = Mth.sin(f * (Math.PI.toFloat() / 180f)).toDouble()
                val d4 = (-Mth.cos(f * (Math.PI.toFloat() / 180f))).toDouble()
                var f1 = d1.toFloat() * 10.0f
                f1 = Mth.clamp(f1, -6.0f, 32.0f)
                var f2 = (d0 * d3 + d2 * d4).toFloat() * 100.0f
                f2 = Mth.clamp(f2, 0.0f, 150.0f)
                var f3 = (d0 * d4 - d2 * d3).toFloat() * 100.0f
                f3 = Mth.clamp(f3, -20.0f, 20.0f)
                if (f2 < 0.0f) {
                    f2 = 0.0f
                }
                val f4 = Mth.lerp(p_116621_, entity.oBob, entity.bob)
                f1 += Mth.sin(Mth.lerp(p_116621_, entity.walkDistO, entity.walkDist) * 6.0f) * 32.0f * f4
                if (entity.isCrouching) {
                    f1 += 25.0f
                }
                p_116615_.mulPose(Vector3f.XP.rotationDegrees(6.0f + f2 / 2.0f + f1))
                p_116615_.mulPose(Vector3f.ZP.rotationDegrees(f3 / 2.0f))
                p_116615_.mulPose(Vector3f.YP.rotationDegrees(180.0f - f3 / 2.0f))
                val vertexconsumer = p_116616_.getBuffer(RenderType.entitySolid(entity.cloakTexture!!))
                this.parentModel.renderCloak(p_116615_, vertexconsumer, p_116617_, OverlayTexture.NO_OVERLAY)
                p_116615_.popPose()
            }
        }
    }


}