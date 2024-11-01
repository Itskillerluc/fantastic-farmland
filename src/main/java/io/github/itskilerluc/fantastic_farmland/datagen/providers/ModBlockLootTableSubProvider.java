package io.github.itskilerluc.fantastic_farmland.datagen.providers;

import io.github.itskilerluc.fantastic_farmland.common.init.BlockRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class ModBlockLootTableSubProvider extends BlockLootSubProvider {
    public ModBlockLootTableSubProvider(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.DEFAULT_FLAGS, registries);
    }

    @Override
    protected @NotNull Iterable<Block> getKnownBlocks() {
        return BlockRegistry.BLOCKS.getEntries()
                .stream().map(e -> ((Block) e.value())).toList();
    }

    @Override
    protected void generate() {
        dropSelf(BlockRegistry.DIRT_SPLITTER.get());
        dropSelf(BlockRegistry.DEFAULT_DIRT.get());
        dropSelf(BlockRegistry.FAST_DIRT.get());
        dropSelf(BlockRegistry.FORTUNE_DIRT.get());
        dropOther(BlockRegistry.SOAKING_CAULDRON.get(), Blocks.CAULDRON);
    }
}
