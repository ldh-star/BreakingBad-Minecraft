package org.liangguo.breakingbad.entity.model

import net.minecraft.client.model.PlayerModel
import net.minecraft.client.model.geom.ModelPart
import net.minecraft.util.Mth
import org.liangguo.breakingbad.entity.mobs.UndeadGusFring


/**
 * @author ldh
 * 时间: 2023/2/7 13:31
 * 邮箱: ldh.liangguo@outlook.com
 */
class UndeadGusFringModel(part: ModelPart, private val slim: Boolean): PlayerModel<UndeadGusFring>(part, slim) {

    override fun setupAnim(
        mob: UndeadGusFring,
        p_103396_: Float,
        p_103397_: Float,
        p_103398_: Float,
        p_103399_: Float,
        p_103400_: Float
    ) {
        super.setupAnim(mob, p_103396_, p_103397_, p_103398_, p_103399_, p_103400_)
        if (mob.isSpellCasting) {
            rightArm.z = 0.0f
            rightArm.x = -5.0f
            leftArm.z = 0.0f
            leftArm.x = 5.0f
            rightArm.xRot = Mth.cos(p_103398_ * 0.6662f) * 0.25f
            leftArm.xRot = Mth.cos(p_103398_ * 0.6662f) * 0.25f
            rightArm.zRot = 2.3561945f
            leftArm.zRot = -2.3561945f
            rightArm.yRot = 0.0f
            leftArm.yRot = 0.0f
        }
    }

}