package io.github.itskilerluc.fantastic_farmland.common.init;

import io.github.itskilerluc.fantastic_farmland.FantasticFarmland;
import io.github.itskilerluc.fantastic_farmland.common.recipes.cauldronsoakrecipe.CauldronSoakRecipe;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class RecipeTypeRegistry {
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister
            .create(Registries.RECIPE_TYPE, FantasticFarmland.MOD_ID);

    public static final Supplier<RecipeType<CauldronSoakRecipe>> CAULDRON_SOAK_RECIPE =
            RECIPE_TYPES.register("cauldron_soak_recipe",
                    () -> RecipeType.simple(FantasticFarmland.rl("cauldron_soak_recipe")));
}
