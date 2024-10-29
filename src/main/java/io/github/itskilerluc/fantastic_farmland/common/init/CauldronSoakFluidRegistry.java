package io.github.itskilerluc.fantastic_farmland.common.init;

import io.github.itskilerluc.fantastic_farmland.FantasticFarmland;
import io.github.itskilerluc.fantastic_farmland.common.recipes.cauldronsoakrecipe.CauldronSoakFluid;
import net.minecraft.core.NonNullList;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

public class CauldronSoakFluidRegistry {
    public static final ResourceKey<CauldronSoakFluid> SPEED_FLUID =
            ResourceKey.create(DatapackRegistryRegistry.CAULDRON_SOAK_FLUID_REGISTRY_KEY,
                    FantasticFarmland.rl("speed"));

    public static final ResourceKey<CauldronSoakFluid> FORTUNE_FLUID =
            ResourceKey.create(DatapackRegistryRegistry.CAULDRON_SOAK_FLUID_REGISTRY_KEY,
                    FantasticFarmland.rl("fortune"));

    public static final ResourceKey<CauldronSoakFluid> DEFAULT_FLUID =
            ResourceKey.create(DatapackRegistryRegistry.CAULDRON_SOAK_FLUID_REGISTRY_KEY,
                    FantasticFarmland.rl("default"));

    public static void bootstrap(BootstrapContext<CauldronSoakFluid> context) {
        context.register(SPEED_FLUID, new CauldronSoakFluid(NonNullList.of(
                Ingredient.EMPTY,
                Ingredient.of(Items.REDSTONE_BLOCK)),
                0xFF0000));
        context.register(FORTUNE_FLUID, new CauldronSoakFluid(NonNullList.of(
                Ingredient.EMPTY,
                Ingredient.of(Items.LAPIS_BLOCK)),
                0x0000FF));
        context.register(DEFAULT_FLUID, new CauldronSoakFluid(NonNullList.of(
                Ingredient.EMPTY,
                Ingredient.of()),
                0xFFFFFF));
    }
}
