package io.github.itskilerluc.fantastic_farmland.datagen.providers;

import io.github.itskilerluc.fantastic_farmland.FantasticFarmland;
import io.github.itskilerluc.fantastic_farmland.common.init.BlockRegistry;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, FantasticFarmland.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        simpleBlockItem(BlockRegistry.DIRT_SPLITTER.get());
        simpleBlockItem(BlockRegistry.DEFAULT_DIRT.get());
        simpleBlockItem(BlockRegistry.FAST_DIRT.get());
        simpleBlockItem(BlockRegistry.FORTUNE_DIRT.get());
    }
}
