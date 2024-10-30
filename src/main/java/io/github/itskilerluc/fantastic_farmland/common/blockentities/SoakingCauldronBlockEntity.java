package io.github.itskilerluc.fantastic_farmland.common.blockentities;

import io.github.itskilerluc.fantastic_farmland.common.init.BlockEntityRegistry;
import io.github.itskilerluc.fantastic_farmland.common.init.CauldronSoakFluidRegistry;
import io.github.itskilerluc.fantastic_farmland.common.init.DatapackRegistryRegistry;
import io.github.itskilerluc.fantastic_farmland.common.recipes.cauldronsoakrecipe.CauldronSoakFluid;
import io.github.itskilerluc.fantastic_farmland.common.util.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SoakingCauldronBlockEntity extends BlockEntity {
    private ResourceKey<CauldronSoakFluid> fluidKey;
    public List<ItemStack> extraItems = new ArrayList<>();

    public SoakingCauldronBlockEntity(BlockPos pos, BlockState blockState) {
        super(BlockEntityRegistry.SOAKING_CAULDRON.get(), pos, blockState);
        fluidKey = CauldronSoakFluidRegistry.DEFAULT_FLUID;
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public void handleUpdateTag(CompoundTag tag, HolderLookup.Provider lookupProvider) {
        super.handleUpdateTag(tag, lookupProvider);
        if (level != null) {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 8);
        }
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        var tag = super.getUpdateTag(registries);
        saveAdditional(tag, registries);
        return tag;
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt, HolderLookup.Provider lookupProvider) {
        super.onDataPacket(net, pkt, lookupProvider);
        if (level != null) {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 8);
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);

        tag.put("fluidKey", ResourceKey.codec(DatapackRegistryRegistry.CAULDRON_SOAK_FLUID_REGISTRY_KEY)
                .encodeStart(NbtOps.INSTANCE, fluidKey).result().orElse(new CompoundTag()));

        ListTag extraItemsTag = new ListTag();
        for (ItemStack stack : extraItems) {
            extraItemsTag.add(stack.save(registries));
        }
        tag.put("extraItems", extraItemsTag);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        fluidKey = ResourceKey.codec(DatapackRegistryRegistry.CAULDRON_SOAK_FLUID_REGISTRY_KEY)
                .parse(NbtOps.INSTANCE, tag.get("fluidKey")).result().orElse(null);

        extraItems = new ArrayList<>(tag.getList("extraItems", 10).stream()
                .map(itemTag -> ItemStack.parse(registries, itemTag).orElse(ItemStack.EMPTY))
                .toList());
    }

    public CauldronSoakFluid getFluid() {
        return Util.ifNotNull(level, lev -> lev.registryAccess()
                .lookupOrThrow(DatapackRegistryRegistry.CAULDRON_SOAK_FLUID_REGISTRY_KEY).getOrThrow(fluidKey).value());
    }

    public ResourceKey<CauldronSoakFluid> getFluidKey() {
        return fluidKey;
    }

    public void setFluid(ResourceKey<CauldronSoakFluid> fluidKey) {
        this.fluidKey = fluidKey;
        if (level != null) {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 8);
        }
    }
}
