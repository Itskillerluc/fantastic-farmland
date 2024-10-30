package io.github.itskilerluc.fantastic_farmland.common.blocks;

import com.mojang.datafixers.util.Pair;
import io.github.itskilerluc.fantastic_farmland.common.blockentities.SoakingCauldronBlockEntity;
import io.github.itskilerluc.fantastic_farmland.common.init.DatapackRegistryRegistry;
import io.github.itskilerluc.fantastic_farmland.common.recipes.cauldronsoakrecipe.CauldronSoakFluid;
import io.github.itskilerluc.fantastic_farmland.common.util.Util;
import io.github.itskilerluc.fantastic_farmland.datagen.providers.ModLanguageProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class SoakingCauldronBlock extends LayeredCauldronBlock implements EntityBlock {
    private static final CauldronInteraction.InteractionMap INTERACTIONS = new CauldronInteraction.InteractionMap(
            "soaking",
            Map.of(
                    Items.GLASS_BOTTLE, (state, level, pos, player, hand, stack) -> {
                        if (state.getValue(LEVEL) < 1) return ItemInteractionResult.FAIL;
                        if (!level.isClientSide) {
                            if (level.getBlockEntity(pos) instanceof SoakingCauldronBlockEntity cauldron) {
                                var itemStack = new ItemStack(Items.POTION);
                                //todo make this translatable
                                itemStack.set(DataComponents.ITEM_NAME,
                                        Component.literal(ModLanguageProvider.toEnglishTranslation(cauldron.getFluidKey().location()) + " Sludge"));
                                itemStack.set(DataComponents.POTION_CONTENTS,
                                        new PotionContents(Optional.empty(),
                                                Optional.of(cauldron.getFluid().color()), cauldron.getFluid().effects()));
                                player.setItemInHand(hand, ItemUtils.createFilledResult(stack, player, itemStack));

                                if (state.getValue(LEVEL) == 1) {
                                    level.setBlockAndUpdate(pos, Blocks.CAULDRON.defaultBlockState());
                                } else {
                                    level.setBlockAndUpdate(pos, state.trySetValue(LEVEL, state.getValue(LEVEL) - 1));
                                }
                                return ItemInteractionResult.sidedSuccess(false);
                            } else {
                                return ItemInteractionResult.FAIL;
                            }
                        } else {
                            level.playSound(player, pos, SoundEvents.BOTTLE_FILL, SoundSource.BLOCKS, 1.0F, 1.0F);
                            return ItemInteractionResult.SUCCESS;
                        }
                    }
            )
    );

    public SoakingCauldronBlock(Properties properties) {
        super(Biome.Precipitation.NONE, INTERACTIONS, properties);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new SoakingCauldronBlockEntity(pos, state);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        CauldronInteraction cauldroninteraction = this.interactions.map().get(stack.getItem());
        if (cauldroninteraction == null) return ItemInteractionResult.FAIL;
        return cauldroninteraction.interact(state, level, pos, player, hand, stack);
    }

    @Override
    protected void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (isEntityInsideContent(state, pos, entity)) {
            if (level.getBlockEntity(pos) instanceof SoakingCauldronBlockEntity blockEntity) {
                if (entity instanceof ItemEntity item) {
                    Stream<Holder.Reference<CauldronSoakFluid>> fluids = level.registryAccess()
                            .lookupOrThrow(DatapackRegistryRegistry.CAULDRON_SOAK_FLUID_REGISTRY_KEY)
                            .listElements()
                            .filter(fluid ->
                                    Util.startsWith(Util.composite(blockEntity.getFluid().ingredients(),
                                                            Ingredient.of(item.getItem())).stream()
                                                    .filter(ingredient -> !ingredient.isEmpty()).toList()
                                            ,fluid.value().ingredients().stream()
                                                    .filter(ingredient -> !ingredient.isEmpty()).toList()));

                    blockEntity.extraItems.add(item.getItem().copy());
                    item.remove(Entity.RemovalReason.KILLED);

                    List<ItemStack> ingredients = Stream.concat(
                            blockEntity.getFluid().ingredients().stream().map(ingr -> Arrays.stream(ingr.getItems()).findFirst().orElse(ItemStack.EMPTY))
                                    .filter(stack -> !stack.isEmpty()),
                            blockEntity.extraItems.stream()).toList();
                    var fluid = fluids.filter(f -> f.value().ingredients().size() == ingredients.size())
                            .filter(i -> IntStream.range(0, ingredients.size()).mapToObj(
                                    j -> Pair.of(ingredients.get(j), i.value().ingredients().get(j))
                            ).allMatch(pair -> pair.getSecond().test(pair.getFirst()))).findFirst();
                    if (fluid.isPresent()) {
                        blockEntity.extraItems.clear();
                        blockEntity.setFluid(fluid.get().key());
                    }
                    entity.playSound(SoundEvents.BREWING_STAND_BREW);
                }
            }
        }
        super.entityInside(state, level, pos, entity);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        return EntityBlock.super.getTicker(level, state, blockEntityType);
    }
}
