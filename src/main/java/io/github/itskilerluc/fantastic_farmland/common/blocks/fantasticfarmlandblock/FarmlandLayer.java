package io.github.itskilerluc.fantastic_farmland.common.blocks.fantasticfarmlandblock;

public class FarmlandLayer {
    private final FarmlandMaterial material;

    private int features;

    public FarmlandLayer(FarmlandMaterial material) {
        this.material = material;
        this.features = 0b0;
    }

    public FarmlandLayer(FarmlandMaterial material, int features) {
        this.material = material;
        this.features = features;
    }

    public void setFeature(int feature, boolean val) {
        if (val) {
            features |= feature;
        } else {
            features ^= feature;
        }
    }

    public static FarmlandLayer copy(FarmlandLayer layer) {
        return new FarmlandLayer(layer.material, layer.features);
    }
}
