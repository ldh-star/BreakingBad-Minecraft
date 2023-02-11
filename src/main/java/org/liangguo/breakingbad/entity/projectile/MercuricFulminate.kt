package org.liangguo.breakingbad.entity.projectile

import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.projectile.ThrowableItemProjectile
import net.minecraft.world.level.Explosion.BlockInteraction
import net.minecraft.world.level.Level
import net.minecraft.world.phys.HitResult
import org.liangguo.breakingbad.init.EntityRegistry
import org.liangguo.breakingbad.init.ItemRegistry


/**
 * @author ldh
 * 时间: 2023/2/2 20:50
 * 邮箱: ldh.liangguo@outlook.com
 */
class MercuricFulminate : ThrowableItemProjectile {

    constructor(
        type: EntityType<out MercuricFulminate>,
        level: Level,
        x: Double = 0.0,
        y: Double = 0.0,
        z: Double = 0.0
    ) : super(type, x, y, z, level)

    constructor(
        level: Level,
        entity: LivingEntity,
        type: EntityType<out MercuricFulminate> = EntityRegistry.MERCURIC_FULMINATE.get(),
    ) : super(type, entity, level)

    override fun getDefaultItem() = ItemRegistry.MERCURIC_FULMINATE_ITEM.get()

    override fun onHit(hitResult: HitResult) {
        super.onHit(hitResult)
        level.explode(this, x, y, z, EXPLOSION_RADIUS, BlockInteraction.BREAK)
        discard()
    }

    companion object {
        val ENTITY_ID = "mercuric_fulminate"
        private const val EXPLOSION_RADIUS = 5.0f

    }
}