package io.github.itskilerluc.fantastic_farmland.common.init;

import io.github.itskilerluc.fantastic_farmland.FantasticFarmland;
import net.minecraft.world.item.BlockItem;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ItemRegistry {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(FantasticFarmland.MOD_ID);

    public static final DeferredItem<BlockItem> DIRT_SPLITTER =
            ITEMS.registerSimpleBlockItem(BlockRegistry.DIRT_SPLITTER);

    public static final DeferredItem<BlockItem> DEFAULT_DIRT =
            ITEMS.registerSimpleBlockItem(BlockRegistry.DEFAULT_DIRT);

    public static final DeferredItem<BlockItem> FAST_DIRT =
            ITEMS.registerSimpleBlockItem(BlockRegistry.FAST_DIRT);

    public static final DeferredItem<BlockItem> FORTUNE_DIRT =
            ITEMS.registerSimpleBlockItem(BlockRegistry.FORTUNE_DIRT);
}
