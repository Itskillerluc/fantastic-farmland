package io.github.itskilerluc.fantastic_farmland;

import io.github.itskilerluc.fantastic_farmland.common.init.BlockRegistry;
import io.github.itskilerluc.fantastic_farmland.common.init.CreativeTabRegistry;
import io.github.itskilerluc.fantastic_farmland.common.init.ItemRegistry;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;

@Mod(FantasticFarmland.MOD_ID)
public class FantasticFarmland
{
    public static final String MOD_ID = "fantastic_farmland";

    public FantasticFarmland(IEventBus modEventBus)
    {
        BlockRegistry.BLOCKS.register(modEventBus);
        ItemRegistry.ITEMS.register(modEventBus);
        CreativeTabRegistry.CREATIVE_MODE_TABS.register(modEventBus);
    }

    public static ResourceLocation rl(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }

    public static String rlStr(String path) {
        return rl(path).toString();
    }
}
