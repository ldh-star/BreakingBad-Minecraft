package org.liangguo.breakingbad.entity.ai.goal

import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal
import net.minecraft.world.entity.player.Player
import org.liangguo.breakingbad.entity.base.HumanoidTrader


/**
 * @author ldh
 * 时间: 2023/2/11 13:43
 * 邮箱: ldh.liangguo@outlook.com
 */
class LookAtTradingPlayerGoal(private val trader: HumanoidTrader) : LookAtPlayerGoal(trader, Player::class.java, 8.0f) {

    override fun canUse(): Boolean {
        trader.tradingPlayer?.let {
            lookAt = it
            return true
        }
        return false
    }

}