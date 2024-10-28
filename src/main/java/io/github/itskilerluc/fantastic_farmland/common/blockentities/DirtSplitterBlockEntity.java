package io.github.itskilerluc.fantastic_farmland.common.blockentities;

import io.github.itskilerluc.fantastic_farmland.common.init.BlockEntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class DirtSplitterBlockEntity extends BlockEntity {
    private FarmlandSettings blockBuilder = FarmlandSettings.EMPTY;

    public DirtSplitterBlockEntity(BlockPos pos, BlockState blockState) {
        super(BlockEntityRegistry.DIRT_SPLITTER.get(), pos, blockState);
    }

}