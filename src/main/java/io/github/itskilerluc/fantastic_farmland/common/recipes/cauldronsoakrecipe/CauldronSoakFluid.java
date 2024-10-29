package io.github.itskilerluc.fantastic_farmland.common.recipes.cauldronsoakrecipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.List;

public record CauldronSoakFluid(List<Ingredient> ingredients, int color) {
    public static final Codec<CauldronSoakFluid> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Ingredient.CODEC.listOf().fieldOf("ingredients").forGetter(CauldronSoakFluid::ingredients),
            Codec.INT.fieldOf("color").forGetter(CauldronSoakFluid::color)
    ).apply(instance, CauldronSoakFluid::new));
}
