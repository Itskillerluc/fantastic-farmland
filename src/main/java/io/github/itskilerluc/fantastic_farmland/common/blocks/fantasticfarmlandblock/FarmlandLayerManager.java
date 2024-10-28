package io.github.itskilerluc.fantastic_farmland.common.blocks.fantasticfarmlandblock;

import java.util.Arrays;

public class FarmlandLayerManager {
    private final FarmlandLayer[] layers = new FarmlandLayer[5];

    public int getTotalSize() {
        return Arrays.stream(layers).mapToInt(layer -> layer.size).sum();
    }
}
