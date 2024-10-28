package io.github.itskilerluc.fantastic_farmland.common.blockentities;

import io.github.itskilerluc.fantastic_farmland.common.blocks.fantasticfarmlandblock.FarmlandLayer;
import io.github.itskilerluc.fantastic_farmland.common.blocks.fantasticfarmlandblock.FarmlandMaterial;
import io.github.itskilerluc.fantastic_farmland.common.util.DividingList;
import it.unimi.dsi.fastutil.Pair;

public class FarmlandSettings {
    public static final int HOPPER = 0b1;
    public static final int PLANTER = 0b10;
    public static final int HARVESTER = 0b100;
    public static final int GROWER = 0b1000;
    public static final int CHEST = 0b10000;

    private int features = 0b0;
    private final DividingList<FarmlandLayer> layers = new DividingList<>(5);
    private int shape;

    public FarmlandSettings() {
        layers.fill(new FarmlandLayer(FarmlandMaterial.DEFAULT));
        layers.split(entry -> Pair.of(FarmlandLayer.copy(entry), FarmlandLayer.copy(entry)), 0, 1, 3);
        layers.join((first, second) -> FarmlandLayer.copy(first), 0);
    }


    public void setFeature(int feature, boolean val) {
        if (val) {
            features |= feature;
        } else {
            features ^= feature;
        }
    }

}
