package io.github.itskilerluc.fantastic_farmland.common.init;

import io.github.itskilerluc.fantastic_farmland.FantasticFarmland;
import io.github.itskilerluc.fantastic_farmland.common.blocks.DirtSplitterBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public class BlockRegistry {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(FantasticFarmland.MOD_ID);

    public static final DeferredBlock<DirtSplitterBlock> DIRT_SPLITTER = BLOCKS.registerBlock("dirt_splitter",
            DirtSplitterBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_WOOD)
                    .noOcclusion().isRedstoneConductor(((state, level, pos) -> false)));
}
