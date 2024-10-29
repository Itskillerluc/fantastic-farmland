package io.github.itskilerluc.fantastic_farmland.datagen.providers;

import io.github.itskilerluc.fantastic_farmland.FantasticFarmland;
import io.github.itskilerluc.fantastic_farmland.common.init.BlockRegistry;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
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
    }
}
