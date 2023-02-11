package org.liangguo.breakingbad.entity.base

import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.monster.Monster
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level


/**
 * @author ldh
 * 时间: 2023/2/7 14:13
 * 邮箱: ldh.liangguo@outlook.com
 *
 *  这是一个通用的实体模板，基于史蒂夫类型的模型
 */
open class HumanoidEntity(
    type: EntityType<out HumanoidEntity>,
    level: Level,
) : Monster(type, level) {

    /**
     * 斗篷的纹理材质，如果该生物没有斗篷，则这个值为空
     */
    open val cloakTexture: ResourceLocation? = null

    var oBob = 0f
    var bob = 0f
    var xCloakO = 0.0
    var yCloakO = 0.0
    var zCloakO = 0.0
    var xCloak = 0.0
    var yCloak = 0.0
    var zCloak = 0.0

    override fun tick() {
        super.tick()
        moveCloak()
    }

    override fun rideTick() {
        super.rideTick()
        oBob = bob
        bob = 0.0f
    }

    private fun moveCloak() {
        xCloakO = xCloak
        yCloakO = yCloak
        zCloakO = zCloak
        val d0 = this.x - xCloak
        val d1 = this.y - yCloak
        val d2 = this.z - zCloak
        val d3 = 10.0
        if (d0 > 10.0) {
            xCloak = this.x
            xCloakO = xCloak
        }
        if (d2 > 10.0) {
            zCloak = this.z
            zCloakO = zCloak
        }
        if (d1 > 10.0) {
            yCloak = this.y
            yCloakO = yCloak
        }
        if (d0 < -10.0) {
            xCloak = this.x
            xCloakO = xCloak
        }
        if (d2 < -10.0) {
            zCloak = this.z
            zCloakO = zCloak
        }
        if (d1 < -10.0) {
            yCloak = this.y
            yCloakO = yCloak
        }
        xCloak += d0 * 0.25
        zCloak += d2 * 0.25
        yCloak += d1 * 0.25
    }

    /**
     * 对于怪物来说，吃东西直接回血
     */
    override fun eat(level: Level, itemStack: ItemStack): ItemStack {
        if (itemStack.isEdible) {
            itemStack.getFoodProperties(this)?.nutrition?.let {
                heal(it.toFloat())
            }
        }
        return super.eat(level, itemStack)
    }

}