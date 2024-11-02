package io.github.itskilerluc.fantastic_farmland.common.events;

import io.github.itskilerluc.fantastic_farmland.FantasticFarmland;
import io.github.itskilerluc.fantastic_farmland.common.blockentities.SoakingCauldronBlockEntity;
import io.github.itskilerluc.fantastic_farmland.common.init.BlockEntityRegistry;
import io.github.itskilerluc.fantastic_farmland.common.network.UpdateColorPayload;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber(modid = FantasticFarmland.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class ModEvents {
    @SubscribeEvent
    public static void registerPayloads(final RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar("1");
        registrar.playToClient(
                UpdateColorPayload.TYPE,
                UpdateColorPayload.STREAM_CODEC,
                UpdateColorPayload::handleData);
    }

    @SubscribeEvent
    public static void registerCapabilities(final RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(
                Capabilities.ItemHandler.BLOCK,
                BlockEntityRegistry.SOAKING_CAULDRON.get(),
                SoakingCauldronBlockEntity::getItemStackHandler
        );
    }
}
