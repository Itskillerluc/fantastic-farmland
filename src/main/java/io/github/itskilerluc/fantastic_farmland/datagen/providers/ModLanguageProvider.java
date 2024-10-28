package io.github.itskilerluc.fantastic_farmland.datagen.providers;

import io.github.itskilerluc.fantastic_farmland.FantasticFarmland;
import io.github.itskilerluc.fantastic_farmland.common.init.BlockRegistry;
import io.github.itskilerluc.fantastic_farmland.common.init.ItemRegistry;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.LanguageProvider;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.Arrays;
import java.util.stream.Collectors;

public class ModLanguageProvider extends LanguageProvider {
    public ModLanguageProvider(PackOutput output) {
        super(output, FantasticFarmland.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        for (DeferredHolder<Item, ? extends Item> entry : ItemRegistry.ITEMS.getEntries()) {
            if (entry.get() instanceof BlockItem && !(entry.get() instanceof ItemNameBlockItem)) continue;
            addItem(entry, toEnglishTranslation(entry.getId()));
        }

        for (DeferredHolder<Block, ? extends Block> entry : BlockRegistry.BLOCKS.getEntries()) {
            addBlock(entry, toEnglishTranslation(entry.getId()));
        }
    }

    private String toEnglishTranslation(ResourceLocation rl) {
        return Arrays.stream(rl.getPath().split("_"))
                .map(word -> Character.toTitleCase(word.charAt(0)) + word.substring(1))
                .collect(Collectors.joining(" "));
    }
}
