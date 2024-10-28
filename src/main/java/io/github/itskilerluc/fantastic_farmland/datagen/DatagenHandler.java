package io.github.itskilerluc.fantastic_farmland.datagen;

import io.github.itskilerluc.fantastic_farmland.FantasticFarmland;
import io.github.itskilerluc.fantastic_farmland.datagen.providers.*;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.List;
import java.util.Set;

@EventBusSubscriber(modid = FantasticFarmland.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class DatagenHandler {
    @SubscribeEvent
    public static void gatherData(final GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        generator.addProvider(event.includeClient(), new ModLanguageProvider(output));
        generator.addProvider(event.includeClient(), new ModItemModelProvider(output, existingFileHelper));
        generator.addProvider(event.includeClient(), new ModBlockStateProvider(output, existingFileHelper));
        generator.addProvider(event.includeServer(), new ModRecipeProvider(output, event.getLookupProvider()));
        generator.addProvider(event.includeServer(),
                new ModBlockTagProvider(output, event.getLookupProvider(), existingFileHelper));
        generator.addProvider(event.includeServer(),
                new LootTableProvider(output, Set.of(), List.of(new LootTableProvider.SubProviderEntry(
                        ModBlockLootTableSubProvider::new, LootContextParamSets.BLOCK
                )), event.getLookupProvider()));
    }
}
