package io.github.itskilerluc.fantastic_farmland.common.init;

import io.github.itskilerluc.fantastic_farmland.FantasticFarmland;
import io.github.itskilerluc.fantastic_farmland.common.recipes.cauldronsoakrecipe.CauldronSoakFluid;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;

@EventBusSubscriber(modid = FantasticFarmland.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class DatapackRegistryRegistry {
    public static final ResourceKey<Registry<CauldronSoakFluid>> CAULDRON_SOAK_FLUID_REGISTRY_KEY =
            ResourceKey.createRegistryKey(FantasticFarmland.rl("cauldron_soak_fluid"));

    @SubscribeEvent
    public static void registerDatapackRegistries(DataPackRegistryEvent.NewRegistry event) {
        event.dataPackRegistry(
                CAULDRON_SOAK_FLUID_REGISTRY_KEY,
                CauldronSoakFluid.CODEC,
                CauldronSoakFluid.CODEC
        );
    }
}
