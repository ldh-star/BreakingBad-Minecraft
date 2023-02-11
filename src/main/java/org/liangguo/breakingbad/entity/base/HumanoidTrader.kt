package org.liangguo.breakingbad.entity.base

import com.google.common.collect.Sets
import net.minecraft.server.level.ServerLevel
import net.minecraft.sounds.SoundEvent
import net.minecraft.sounds.SoundEvents
import net.minecraft.world.damagesource.DamageSource
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.npc.VillagerProfession
import net.minecraft.world.entity.npc.VillagerTrades
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.trading.Merchant
import net.minecraft.world.item.trading.MerchantOffer
import net.minecraft.world.item.trading.MerchantOffers
import net.minecraft.world.level.Level
import net.minecraftforge.common.util.ITeleporter
import org.liangguo.breakingbad.entity.ai.goal.LookAtTradingPlayerGoal
import org.liangguo.breakingbad.entity.ai.goal.TradeWithPlayerGoal
import java.lang.ref.WeakReference
import kotlin.random.Random


/**
 * @author ldh
 * 时间: 2023/2/11 12:55
 * 邮箱: ldh.liangguo@outlook.com
 */
abstract class HumanoidTrader(
    type: EntityType<out HumanoidTrader>,
    level: Level,
    val profession: VillagerProfession,
    val tradeItemCount: Int = 2,
) : HumanoidEntity(type, level), Merchant {

    /**
     * mc原版代码里是强引用player对象，会不会造成内存泄漏啊
     */
    private var _tradingPlayer = WeakReference<Player>(null)

    private var _offers: MerchantOffers? = null

    val isTrading: Boolean
        get() = tradingPlayer != null

    //todo 暂时不考虑交易升级，以后再说
    val tradeLevel = 1

    override fun registerGoals() {
        super.registerGoals()
        goalSelector.addGoal(1, LookAtTradingPlayerGoal(this))
        goalSelector.addGoal(1, TradeWithPlayerGoal(this))
    }

    override fun setTradingPlayer(player: Player?) {
        val flag = tradingPlayer != null && player == null
        _tradingPlayer = WeakReference(player)
        if (flag) {
            stopTrading()
        }
    }

    override fun getTradingPlayer() = _tradingPlayer.get()

    override fun getOffers(): MerchantOffers {
        if (_offers == null) {
            _offers = MerchantOffers()
            updateTrades()
        }
        return _offers!!
    }

    private fun updateTrades() {
        val map = VillagerTrades.TRADES[profession]
        if (!map.isNullOrEmpty()) {
            map[tradeLevel]?.let { array ->
                addOffersFromItemListings(offers, array, tradeItemCount)
            }
        }
    }

    /**
     * 交易槽有几个
     */
    private fun addOffersFromItemListings(
        merchantOffers: MerchantOffers,
        listings: Array<VillagerTrades.ItemListing>,
        tradeItemCount: Int
    ) {
        val set: MutableSet<Int> = Sets.newHashSet()
        if (listings.size > tradeItemCount) {
            while (set.size < tradeItemCount) {
                set.add(Random.nextInt(listings.size))
            }
        } else {
            for (i in listings.indices) {
                set.add(i)
            }
        }
        for (i in set) {
            val listing = listings.getOrNull(i)
            listing?.getOffer(this, random)?.let { merchantOffers.add(it) }
        }
    }

    override fun overrideOffers(merchantOffers: MerchantOffers) {}

    override fun notifyTrade(p_45305_: MerchantOffer) {
        //todo notifyTrade
    }

    override fun notifyTradeUpdated(itemStack: ItemStack) {
        if (!level.isClientSide && ambientSoundTime > -ambientSoundInterval + 20) {
            ambientSoundTime = -ambientSoundInterval
            playSound(getTradeUpdatedSound(!itemStack.isEmpty), soundVolume, voicePitch)
        }
    }

    private fun getTradeUpdatedSound(success: Boolean) =
        if (success) SoundEvents.VILLAGER_YES else SoundEvents.VILLAGER_NO


    override fun getVillagerXp(): Int {
        return 0
    }

    override fun overrideXp(p_45309_: Int) {}

    override fun showProgressBar(): Boolean {
        return false
    }

    override fun getNotifyTradeSound(): SoundEvent = SoundEvents.VILLAGER_YES

    override fun isClientSide() = level.isClientSide

    protected open fun startTrading(player: Player) {
//   todo 特价销售     updateSpecialPrices(player)
        setTradingPlayer(player)
        openTradingScreen(player, displayName, tradeLevel)
    }

    override fun die(source: DamageSource) {
        super.die(source)
        stopTrading()
    }

    override fun changeDimension(serverLevel: ServerLevel, teleporter: ITeleporter): Entity? {
        stopTrading()
        return super.changeDimension(serverLevel, teleporter)
    }

    protected open fun stopTrading() {
        tradingPlayer = null
        // todo 重置特价 resetSpecialPrices()
    }

}