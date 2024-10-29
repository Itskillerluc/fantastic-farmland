package io.github.itskilerluc.fantastic_farmland.common.recipes.cauldronsoakrecipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.itskilerluc.fantastic_farmland.common.init.DatapackRegistryRegistry;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class CauldronSoakRecipeSerializer implements RecipeSerializer<CauldronSoakRecipe> {
    public static final MapCodec<CauldronSoakRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            ResourceKey.codec(DatapackRegistryRegistry.CAULDRON_SOAK_FLUID_REGISTRY_KEY)
                    .fieldOf("fluid").forGetter(CauldronSoakRecipe::getFluidKey),
            Codec.INT.fieldOf("soak_time").forGetter(CauldronSoakRecipe::getSoakTime),
            Codec.INT.fieldOf("layers").forGetter(CauldronSoakRecipe::getLayers),
            Ingredient.CODEC.fieldOf("input").forGetter(CauldronSoakRecipe::getInput),
            ItemStack.CODEC.fieldOf("output").forGetter(CauldronSoakRecipe::getOutput)
    ).apply(instance, CauldronSoakRecipe::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, CauldronSoakRecipe> STREAM_CODEC =
            StreamCodec.composite(
                    ResourceKey.streamCodec(DatapackRegistryRegistry.CAULDRON_SOAK_FLUID_REGISTRY_KEY),
                    CauldronSoakRecipe::getFluidKey,
                    ByteBufCodecs.INT, CauldronSoakRecipe::getSoakTime,
                    ByteBufCodecs.INT, CauldronSoakRecipe::getLayers,
                    Ingredient.CONTENTS_STREAM_CODEC, CauldronSoakRecipe::getInput,
                    ItemStack.STREAM_CODEC, CauldronSoakRecipe::getOutput,
                    CauldronSoakRecipe::new
            );

    @Override
    public MapCodec<CauldronSoakRecipe> codec() {
        return CODEC;
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, CauldronSoakRecipe> streamCodec() {
        return STREAM_CODEC;
    }
}
