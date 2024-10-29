package io.github.itskilerluc.fantastic_farmland.common.recipes.cauldronsoakrecipe;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;

public class CauldronSoakRecipeBuilder implements RecipeBuilder {
    private final ResourceKey<CauldronSoakFluid> fluid;
    private final int soakTime;
    private final int layers;
    private final Ingredient input;
    private final ItemStack output;
    private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();

    public CauldronSoakRecipeBuilder(ResourceKey<CauldronSoakFluid> fluid, int soakTime, int layers,
                                     Ingredient input, ItemStack output) {
        this.fluid = fluid;
        this.soakTime = soakTime;
        this.layers = layers;
        this.input = input;
        this.output = output;
    }

    @Override
    public RecipeBuilder unlockedBy(String name, Criterion<?> criterion) {
        this.criteria.put(name, criterion);
        return this;
    }

    @Override
    public RecipeBuilder group(@Nullable String groupName) {
        return this;
    }

    @Override
    public Item getResult() {
        return output.getItem();
    }

    @Override
    public void save(RecipeOutput recipeOutput, ResourceLocation id) {
        Advancement.Builder advancement = recipeOutput.advancement()
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id))
                .rewards(AdvancementRewards.Builder.recipe(id))
                .requirements(AdvancementRequirements.Strategy.OR);
        this.criteria.forEach(advancement::addCriterion);
        CauldronSoakRecipe recipe = new CauldronSoakRecipe(fluid, soakTime, layers, input, output);
        recipeOutput.accept(id, recipe, advancement.build(id.withPrefix("recipes/")));
    }
}
