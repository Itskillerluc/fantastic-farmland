package io.github.itskilerluc.fantastic_farmland.common.recipes.cauldronsoakrecipe;

import io.github.itskilerluc.fantastic_farmland.common.init.DatapackRegistryRegistry;
import io.github.itskilerluc.fantastic_farmland.common.init.RecipeSerializerRegistry;
import io.github.itskilerluc.fantastic_farmland.common.init.RecipeTypeRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

public class CauldronSoakRecipe implements Recipe<CauldronSoakInput> {
    private final ResourceKey<CauldronSoakFluid> fluid;
    private final int soakTime;
    private final int layers;
    private final Ingredient input;
    private final ItemStack output;

    public CauldronSoakRecipe(ResourceKey<CauldronSoakFluid> fluid, int soakTime, int layers, Ingredient input, ItemStack output) {
        this.fluid = fluid;
        this.soakTime = soakTime;
        this.layers = layers;
        this.input = input;
        this.output = output;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> list = NonNullList.create();
        list.add(this.input);
        return list;
    }

    @Override
    public boolean matches(CauldronSoakInput input, Level level) {
        var fluid = level.registryAccess().lookupOrThrow(DatapackRegistryRegistry.CAULDRON_SOAK_FLUID_REGISTRY_KEY)
                .getOrThrow(this.fluid);
        return this.input.test(input.input()) && input.fluid().equals(fluid.value()) && input.layers() == this.layers;
    }

    @Override
    public ItemStack assemble(CauldronSoakInput input, HolderLookup.Provider registries) {
        return output.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width * height >= 1;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider registries) {
        return output;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RecipeSerializerRegistry.CAULDRON_SOAK_RECIPE.get();
    }

    @Override
    public RecipeType<?> getType() {
        return RecipeTypeRegistry.CAULDRON_SOAK_RECIPE.get();
    }

    public int getSoakTime() {
        return soakTime;
    }

    public int getLayers() {
        return layers;
    }

    public ResourceKey<CauldronSoakFluid> getFluidKey() {
        return fluid;
    }

    public Ingredient getInput() {
        return input;
    }

    public ItemStack getOutput() {
        return output;
    }
}
