package org.liangguo.breakingbad.init

import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemNameBlockItem
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraftforge.eventbus.api.IEventBus
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries
import net.minecraftforge.registries.RegistryObject
import org.liangguo.breakingbad.block.crops.CannabisCrop
import org.liangguo.breakingbad.R
import org.liangguo.breakingbad.item.ModCreativeModeTab
import org.liangguo.breakingbad.init.ItemRegistry.registerItem

/**
 * @author ldh
 * 时间: 2023/1/27 17:22
 * 邮箱: ldh.liangguo@outlook.com
 */
object BlockRegistry {

    private val BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, R.MOD_ID)


    val CANNABIS_CROP: RegistryObject<CannabisCrop<ItemNameBlockItem>> = registerBlock(
        id = "cannabis_crop",
        properties = BlockBehaviour.Properties.copy(Blocks.WHEAT),
        registerItem = false,
    ) { CannabisCrop(it, ItemRegistry.CANNABIS_SEEDS) }


    /**
     * 注册新的方块
     *
     * @param properties Item的属性，这是Item的构造函数的必要参数
     * @param tab 创造模式标签栏，默认在本模组的标签栏下
     * @param block 构造[Block]的函数，默认由[properties]构造普通的Item，如果你需要注册非[Block]类的（[Block]下的子类），请一定要覆盖此参数
     * @return 注册好的block
     */
    private inline fun <reified T : Block> registerBlock(
        id: String,
        properties: BlockBehaviour.Properties,
        tab: CreativeModeTab = ModCreativeModeTab.DRUGS_TAB,
        registerItem: Boolean = true,
        crossinline block: (properties: BlockBehaviour.Properties) -> T = { Block(it) as T }
    ): RegistryObject<T> = BLOCKS.register(id) {
        block(properties)
    }.apply {
        if (registerItem) registerBlockItem(id, this, tab)
    }


    /**
     * 为方块注册item
     */
    private fun <T : Block> registerBlockItem(
        id: String,
        block: RegistryObject<T>,
        tab: CreativeModeTab
    ): RegistryObject<Item> {
        return registerItem(id = id, tab = tab) {
            BlockItem(block.get(), it)
        }
    }

    fun IEventBus.registerBlocks() {
        BLOCKS.register(this)
    }
}