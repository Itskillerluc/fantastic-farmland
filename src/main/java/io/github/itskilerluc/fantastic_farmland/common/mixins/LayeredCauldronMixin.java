package io.github.itskilerluc.fantastic_farmland.common.mixins;

import io.github.itskilerluc.fantastic_farmland.common.blocks.SoakingCauldronBlock;
import io.github.itskilerluc.fantastic_farmland.common.init.BlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractCauldronBlock;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LayeredCauldronBlock.class)
public abstract class LayeredCauldronMixin extends AbstractCauldronBlock {

    public LayeredCauldronMixin(Properties properties, CauldronInteraction.InteractionMap interactions) {
        super(properties, interactions);
    }

    @Inject(method = "entityInside", at = @At("HEAD"))
    protected void entityInside(BlockState state, Level level, BlockPos pos, Entity entity, CallbackInfo ci) {
        if (entity instanceof ItemEntity item) {
            //todo show some cool particles
            //todo play a sound
            if (item.getItem().is(Items.BONE_MEAL)) {
                level.setBlockAndUpdate(pos, BlockRegistry.SOAKING_CAULDRON.get()
                        .defaultBlockState().setValue(SoakingCauldronBlock.LEVEL,
                                state.getValue(SoakingCauldronBlock.LEVEL)));
                item.remove(Entity.RemovalReason.KILLED);
                entity.playSound(SoundEvents.LAVA_EXTINGUISH);
            }
        }
    }
}
