package io.github.itskilerluc.fantastic_farmland.common.blocks;

import io.github.itskilerluc.fantastic_farmland.common.blocks.fantasticfarmlandblock.FarmlandMaterial;
import net.minecraft.world.level.block.Block;

public class FantasticDirtBlock extends Block {
    public final FarmlandMaterial material;

    public FantasticDirtBlock(Properties properties, FarmlandMaterial material) {
        super(properties);
        this.material = material;
    }
}
