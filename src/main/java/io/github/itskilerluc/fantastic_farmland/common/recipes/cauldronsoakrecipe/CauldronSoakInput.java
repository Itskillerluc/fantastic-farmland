package io.github.itskilerluc.fantastic_farmland.common.recipes.cauldronsoakrecipe;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

public record CauldronSoakInput(
        CauldronSoakFluid fluid,
        int layers,
        ItemStack input) implements RecipeInput {

    @Override
    public ItemStack getItem(int index) {
        return input;
    }

    @Override
    public int size() {
        return 2;
    }
}
