package io.github.itskilerluc.fantastic_farmland.client.events;

import io.github.itskilerluc.fantastic_farmland.FantasticFarmland;
import io.github.itskilerluc.fantastic_farmland.common.blockentities.SoakingCauldronBlockEntity;
import io.github.itskilerluc.fantastic_farmland.common.init.BlockRegistry;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;

@EventBusSubscriber(modid = FantasticFarmland.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModEvents {
    @SubscribeEvent
    public static void registerBlockColors(RegisterColorHandlersEvent.Block event) {
        event.register((state, level, pos, tintIndex) -> {
            if (tintIndex == 0 && level != null && pos != null) {
                if (level.getBlockEntity(pos) instanceof SoakingCauldronBlockEntity cauldron) {
                    return cauldron.getFluid().color();
                } else {
                    return -1;
                }
            } else {
                return -1;
            }
        }, BlockRegistry.SOAKING_CAULDRON.get());
    }
}
