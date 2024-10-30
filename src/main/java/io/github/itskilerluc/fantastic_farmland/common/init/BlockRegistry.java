package io.github.itskilerluc.fantastic_farmland.common.init;

import io.github.itskilerluc.fantastic_farmland.FantasticFarmland;
import io.github.itskilerluc.fantastic_farmland.common.blocks.DirtSplitterBlock;
import io.github.itskilerluc.fantastic_farmland.common.blocks.FantasticDirtBlock;
import io.github.itskilerluc.fantastic_farmland.common.blocks.SoakingCauldronBlock;
import io.github.itskilerluc.fantastic_farmland.common.blocks.fantasticfarmlandblock.FarmlandMaterial;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public class BlockRegistry {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(FantasticFarmland.MOD_ID);

    public static final DeferredBlock<DirtSplitterBlock> DIRT_SPLITTER = BLOCKS.registerBlock("dirt_splitter",
            DirtSplitterBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_WOOD)
                    .noOcclusion().isRedstoneConductor(((state, level, pos) -> false)));

    public static final DeferredBlock<Block> DEFAULT_DIRT = BLOCKS.registerBlock("default_dirt",
            props -> new FantasticDirtBlock(props, FarmlandMaterial.DEFAULT),
            BlockBehaviour.Properties.ofFullCopy(Blocks.DIRT));

    public static final DeferredBlock<Block> FAST_DIRT = BLOCKS.registerBlock("fast_dirt",
            props -> new FantasticDirtBlock(props, FarmlandMaterial.SPEED),
            BlockBehaviour.Properties.ofFullCopy(Blocks.DIRT).mapColor(MapColor.TERRACOTTA_RED));

    public static final DeferredBlock<Block> FORTUNE_DIRT = BLOCKS.registerBlock("fortune_dirt",
            props -> new FantasticDirtBlock(props, FarmlandMaterial.FORTUNE),
            BlockBehaviour.Properties.ofFullCopy(Blocks.DIRT).mapColor(MapColor.TERRACOTTA_BLUE));

    public static final DeferredBlock<Block> SOAKING_CAULDRON = BLOCKS.registerBlock("soaking_cauldron",
            SoakingCauldronBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.CAULDRON));
}
