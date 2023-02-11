package org.liangguo.breakingbad.utils

import net.minecraft.ChatFormatting
import net.minecraft.client.Minecraft
import net.minecraft.world.InteractionHand
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.Mob
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import net.minecraftforge.event.ForgeEventFactory
import kotlin.random.Random


/**
 * @author ldh
 * 时间: 2023/1/31 13:38
 * 邮箱: ldh.liangguo@outlook.com
 */
/**
 * 是否是主手
 */
val InteractionHand.isMainHand: Boolean
    get() = this == InteractionHand.MAIN_HAND

/**
 * 是否是副手
 */
val InteractionHand.isOffHand: Boolean
    get() = this == InteractionHand.OFF_HAND

/**
 * 服务器端
 */
val Level.isServerSide: Boolean
    get() = !isClientSide

/**
 * 使用当前玩家发送聊天框消息
 */
fun trySendSystemMessage(message: String, color: ChatFormatting? = null) {
    Minecraft.getInstance().player?.sendMessage(message, color)
}

/**
 * 发送消息到聊天框
 */
fun Player.sendMessage(message: String, color: ChatFormatting? = null) {
    sendSystemMessage(message.literal().apply {
        color?.let { withStyle(color) }
    })
}

/**
 * [Mob]的左手
 */
val Mob.leftHand: InteractionHand
    get() = if (isLeftHanded) InteractionHand.MAIN_HAND else InteractionHand.OFF_HAND

/**
 * [Mob]的右手
 */
val Mob.rightHand: InteractionHand
    get() = if (!isLeftHanded) InteractionHand.MAIN_HAND else InteractionHand.OFF_HAND

/**
 * 一个通用函数能够将一种生物转换成另外一种生物，转换后原来的生物会自动消除
 *
 * @param customConfig 新生成的生物可以在这个函数里配置你自己想要的属性
 * @param saveItemInHand 转换后生成的会不会保留原生物手上拿的
 */
fun <T : Mob> Mob.convertTo(
    type: EntityType<T>,
    level: Level,
    saveItemInHand: Boolean = false,
    customConfig: ((T) -> Unit)? = null,
): T? {
    val newMob = type.create(level)?.let {
        it.moveTo(x, y, z, yRot, xRot)
        it.isNoAi = isNoAi
        it.isBaby = isBaby
        if (saveItemInHand) {
            if (it.mainHandItem.isEmpty) {
                it.setItemInHand(InteractionHand.MAIN_HAND, mainHandItem)
            }
            if (it.offhandItem.isEmpty) {
                it.setItemInHand(InteractionHand.OFF_HAND, offhandItem)
            }
        }
        if (hasCustomName()) {
            it.customName = customName
            it.isCustomNameVisible = isCustomNameVisible
        }
        customConfig?.invoke(it)
        ForgeEventFactory.onLivingConvert(this, it)
        level.addFreshEntity(it)
        discard()
        it
    }
    return newMob
}

/**
 * 让一个实体爆出items，每一个itemStack都是随机方向
 */
fun Entity.spawnItems(vararg itemStacks: ItemStack) {
    //在本实体坐标的y的上面一格的位置炸出来
    itemStacks.forEach { itemStack ->
        spawnAtLocation(itemStack.item, 1)?.let {
            it.item = itemStack
            deltaMovement.add(
                ((Random.nextFloat() - Random.nextFloat()) * 0.1f).toDouble(),
                (Random.nextFloat() * 0.05f).toDouble(),
                ((Random.nextFloat() - Random.nextFloat()) * 0.1f).toDouble()
            )
        }
    }
}
