package org.liangguo.breakingbad.item.custom

import net.minecraft.ChatFormatting
import net.minecraft.sounds.SoundEvents
import net.minecraft.sounds.SoundSource
import net.minecraft.stats.Stats
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import org.liangguo.breakingbad.entity.projectile.MercuricFulminate
import org.liangguo.breakingbad.item.base.TooltipItem
import org.liangguo.breakingbad.utils.literals
import kotlin.random.Random


/**
 * @author ldh
 * 时间: 2023/2/2 20:48
 * 邮箱: ldh.liangguo@outlook.com
 */
class MercuricFulminateItem(properties: Properties) :
    TooltipItem(
        properties,
        literals("Hg(CNO)₂", "右键扔出爆炸", color = ChatFormatting.YELLOW),
        literals(
            "雷汞为极敏感而猛烈的爆药，受轻微碰撞、摩擦或与燃烧体、加热体互相接触即会发生爆炸，故用于起爆用药。",
            "剧情：第一季第6集。毒贩头目图克毒打了杰西一顿，抢走了他们的货。白老师看见深度昏迷的杰西，感到愤怒不已。他制作了一袋只要轻轻摩擦就会爆炸的雷酸汞前往图克的老窝，炸掉了整个地方。"
        )
    ) {

    override fun use(
        level: Level,
        player: Player,
        hand: InteractionHand
    ): InteractionResultHolder<ItemStack> {
        val itemstack = player.getItemInHand(hand)
        level.playSound(
            player,
            player.x,
            player.y,
            player.z,
            SoundEvents.SNOWBALL_THROW,
            SoundSource.NEUTRAL,
            0.5f,
            0.5f / (Random.nextFloat() * 0.4f + 0.8f)
        )

        if (!level.isClientSide) {
            val ball = MercuricFulminate(level, player)
            ball.item = itemstack
            ball.shootFromRotation(player, player.xRot, player.yRot, 0.0f, 1.5f, 1.0f)
            level.addFreshEntity(ball)
        }
        player.awardStat(Stats.ITEM_USED[this])
        if (!player.abilities.instabuild) {
            itemstack.shrink(1)
        }

        return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide())
    }

}