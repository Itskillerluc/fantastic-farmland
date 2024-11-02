package io.github.itskilerluc.fantastic_farmland.datagen.providers;

import io.github.itskilerluc.fantastic_farmland.FantasticFarmland;
import io.github.itskilerluc.fantastic_farmland.common.blocks.SoakingCauldronBlock;
import io.github.itskilerluc.fantastic_farmland.common.init.BlockRegistry;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, FantasticFarmland.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        horizontalBlock(BlockRegistry.DIRT_SPLITTER.get(),
                models().getExistingFile(FantasticFarmland.rl("block/dirt_splitter")));
        simpleBlock(BlockRegistry.DEFAULT_DIRT.get());
        simpleBlock(BlockRegistry.FAST_DIRT.get());
        simpleBlock(BlockRegistry.FORTUNE_DIRT.get());
        getVariantBuilder(BlockRegistry.SOAKING_CAULDRON.get())
                .forAllStates(blockState ->
                        switch (blockState.getValue(SoakingCauldronBlock.LEVEL)) {
                            case 1 -> ConfiguredModel.builder().modelFile(
                                    models().withExistingParent("soaking_cauldron_level1",
                                            ResourceLocation.withDefaultNamespace("block/water_cauldron_level1"))
                                            .texture("content", "block/cauldron")).build();
                            case 2 -> ConfiguredModel.builder().modelFile(
                                    models().withExistingParent("soaking_cauldron_level2",
                                            ResourceLocation.withDefaultNamespace("block/water_cauldron_level2"))
                                            .texture("content", "block/cauldron")).build();
                            case 3 -> ConfiguredModel.builder().modelFile(
                                    models().withExistingParent("soaking_cauldron_full",
                                            ResourceLocation.withDefaultNamespace("block/water_cauldron_full"))
                                            .texture("content", "block/cauldron")).build();
                            default ->
                                    throw new IllegalStateException("Unexpected value: " + blockState.getValue(SoakingCauldronBlock.LEVEL));
                        });
    }
}
