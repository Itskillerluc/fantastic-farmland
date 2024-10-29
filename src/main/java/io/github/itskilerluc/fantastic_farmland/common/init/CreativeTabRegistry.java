package io.github.itskilerluc.fantastic_farmland.common.init;

import io.github.itskilerluc.fantastic_farmland.FantasticFarmland;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class CreativeTabRegistry {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, FantasticFarmland.MOD_ID);

    public static final Supplier<CreativeModeTab> FANTASTIC_FARMLAND = CREATIVE_MODE_TABS.register("fantastic_farmland",
            () -> CreativeModeTab.builder()
                    .title(Component.translatableWithFallback("ítemGroup." + FantasticFarmland.MOD_ID + ".fantastic_farmland", "Fantastic Farmland"))
                    .icon(() -> new ItemStack(ItemRegistry.DIRT_SPLITTER.get()))
                    .displayItems((params, output) -> {
                        for (DeferredHolder<Item, ? extends Item> entry : ItemRegistry.ITEMS.getEntries()) {
                            output.accept(entry.get());
                        }
                    }).build());
}
