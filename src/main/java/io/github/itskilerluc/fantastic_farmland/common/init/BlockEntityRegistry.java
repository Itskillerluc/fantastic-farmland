package io.github.itskilerluc.fantastic_farmland.common.init;

import io.github.itskilerluc.fantastic_farmland.FantasticFarmland;
import io.github.itskilerluc.fantastic_farmland.common.blockentities.DirtSplitterBlockEntity;
import io.github.itskilerluc.fantastic_farmland.common.blockentities.SoakingCauldronBlockEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class BlockEntityRegistry {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES =
            DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, FantasticFarmland.MOD_ID);

    public static final Supplier<BlockEntityType<DirtSplitterBlockEntity>> DIRT_SPLITTER =
            BLOCK_ENTITY_TYPES.register("dirt_splitter", () ->
                    BlockEntityType.Builder.of(DirtSplitterBlockEntity::new, BlockRegistry.DIRT_SPLITTER.get())
                            .build(null));

    public static final Supplier<BlockEntityType<SoakingCauldronBlockEntity>> SOAKING_CAULDRON =
            BLOCK_ENTITY_TYPES.register("soaking_cauldron", () ->
                    BlockEntityType.Builder.of(SoakingCauldronBlockEntity::new, BlockRegistry.SOAKING_CAULDRON.get())
                            .build(null));
}
