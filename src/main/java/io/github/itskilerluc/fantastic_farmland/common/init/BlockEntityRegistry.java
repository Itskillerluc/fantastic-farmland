package io.github.itskilerluc.fantastic_farmland.common.init;

import io.github.itskilerluc.fantastic_farmland.FantasticFarmland;
import io.github.itskilerluc.fantastic_farmland.common.blockentities.DirtSplitterBlockEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class BlockEntityRegistry {
    public static final DeferredRegister<BlockEntityType<?>> REGISTRY =
            DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, FantasticFarmland.MOD_ID);

    public static final Supplier<BlockEntityType<DirtSplitterBlockEntity>> DIRT_SPLITTER =
            REGISTRY.register("dirt_splitter", () ->
                    BlockEntityType.Builder.of(DirtSplitterBlockEntity::new, BlockRegistry.DIRT_SPLITTER.get())
                            .build(null));
}
