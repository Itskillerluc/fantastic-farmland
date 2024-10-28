package io.github.itskilerluc.fantastic_farmland.common.blocks;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DirtSplitterBlock extends HorizontalDirectionalBlock {
    private static final MapCodec<DirtSplitterBlock> CODEC = simpleCodec(DirtSplitterBlock::new);

    private static final VoxelShape EMPTY = Shapes.join(Shapes.block(), Shapes.or(
            Shapes.box(3, 3, 0, 13, 13, 16),
            Shapes.box(3, 0, 3, 13, 16, 13),
            Shapes.box(0, 3, 3, 16, 13, 13)
    ), BooleanOp.ONLY_FIRST);

    private static final VoxelShape TEST = Shapes.or(Shapes.box(3, 3, 0, 13, 13, 16),
            Shapes.box(3, 0, 3, 13, 16, 13),
            Shapes.box(0, 3, 3, 16, 13, 13));

    public DirtSplitterBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    protected @NotNull MapCodec<? extends HorizontalDirectionalBlock> codec() {
        return CODEC;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return defaultBlockState().setValue(FACING, context.getHorizontalDirection());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    protected boolean propagatesSkylightDown(BlockState state, BlockGetter level, BlockPos pos) {
        return super.propagatesSkylightDown(state, level, pos);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return TEST;
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return super.getCollisionShape(state, level, pos, context);
    }
}
