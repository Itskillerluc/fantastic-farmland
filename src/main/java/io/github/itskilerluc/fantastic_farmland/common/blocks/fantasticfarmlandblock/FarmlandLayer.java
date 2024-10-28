package io.github.itskilerluc.fantastic_farmland.common.blocks.fantasticfarmlandblock;

public class FarmlandLayer {
    private final FarmlandMaterial material;

    public final int size;
    private int features;

    public FarmlandLayer(FarmlandMaterial material, int size) {
        this.material = material;
        this.features = 0b0;
        this.size = size;
    }

    public FarmlandLayer(FarmlandMaterial material, int features, int size) {
        this.material = material;
        this.features = features;
        this.size = size;
    }

    public void setFeature(int feature, boolean val) {
        if (val) {
            features |= feature;
        } else {
            features ^= feature;
        }
    }
}
