package org.liangguo.breakingbad.entity.ai.goal

import net.minecraft.world.entity.ai.goal.Goal
import org.liangguo.breakingbad.entity.base.HumanoidTrader
import java.util.*


/**
 * @author ldh
 * 时间: 2023/2/11 13:55
 * 邮箱: ldh.liangguo@outlook.com
 */
class TradeWithPlayerGoal(private val trader: HumanoidTrader) : Goal() {

    init {
        flags = EnumSet.of(Flag.JUMP, Flag.MOVE)
    }

    override fun canUse(): Boolean {
        return with(trader) {
            if (!isAlive || isInWater || !isOnGround || hurtMarked || tradingPlayer == null) {
                false
            } else {
                trader.distanceToSqr(tradingPlayer!!) < 16.0 && tradingPlayer?.containerMenu != null
            }
        }
    }

    override fun start() {
        trader.navigation.stop()
    }

    override fun stop() {
        trader.tradingPlayer = null
    }


}