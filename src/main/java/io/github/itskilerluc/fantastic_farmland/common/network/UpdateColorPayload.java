package io.github.itskilerluc.fantastic_farmland.common.network;

import io.github.itskilerluc.fantastic_farmland.FantasticFarmland;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record UpdateColorPayload(BlockPos pos) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<UpdateColorPayload> TYPE =
            new CustomPacketPayload.Type<>(FantasticFarmland.rl("update_color"));

    public static final StreamCodec<FriendlyByteBuf, UpdateColorPayload> STREAM_CODEC = StreamCodec.composite(
            BlockPos.STREAM_CODEC,
            UpdateColorPayload::pos,
            UpdateColorPayload::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handleData(final UpdateColorPayload data, final IPayloadContext context) {
        context.enqueueWork(() -> {
            var level = Minecraft.getInstance().level;
            if (level != null) {
                level.sendBlockUpdated(data.pos, Blocks.AIR.defaultBlockState(), Blocks.AIR.defaultBlockState(), 8);
            }
        });
    }
}
