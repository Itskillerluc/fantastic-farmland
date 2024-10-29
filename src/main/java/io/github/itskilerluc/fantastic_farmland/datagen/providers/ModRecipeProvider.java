package io.github.itskilerluc.fantastic_farmland.datagen.providers;

import io.github.itskilerluc.fantastic_farmland.common.init.CauldronSoakFluidRegistry;
import io.github.itskilerluc.fantastic_farmland.common.init.DatapackRegistryRegistry;
import io.github.itskilerluc.fantastic_farmland.common.init.ItemRegistry;
import io.github.itskilerluc.fantastic_farmland.common.recipes.cauldronsoakrecipe.CauldronSoakFluid;
import io.github.itskilerluc.fantastic_farmland.common.recipes.cauldronsoakrecipe.CauldronSoakRecipeBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider {
    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }


    @Override
    protected void buildRecipes(RecipeOutput p_recipeOutput, HolderLookup.Provider holderLookup) {
        super.buildRecipes(p_recipeOutput, holderLookup);
        new CauldronSoakRecipeBuilder(
                CauldronSoakFluidRegistry.DEFAULT_FLUID,
                100,
                4,
                Ingredient.of(Items.DIRT),
                new ItemStack(ItemRegistry.DEFAULT_DIRT.get())
        ).unlockedBy("has_dirt", has(Items.DIRT))
                .save(p_recipeOutput);
        new CauldronSoakRecipeBuilder(
                CauldronSoakFluidRegistry.FORTUNE_FLUID,
                100,
                4,
                Ingredient.of(Items.DIRT),
                new ItemStack(ItemRegistry.FORTUNE_DIRT.get())
        ).unlockedBy("has_dirt", has(Items.DIRT))
                .save(p_recipeOutput);
        new CauldronSoakRecipeBuilder(
                CauldronSoakFluidRegistry.SPEED_FLUID,
                100,
                4,
                Ingredient.of(Items.DIRT),
                new ItemStack(ItemRegistry.FAST_DIRT.get())
        ).unlockedBy("has_dirt", has(Items.DIRT))
                .save(p_recipeOutput);
    }
}
