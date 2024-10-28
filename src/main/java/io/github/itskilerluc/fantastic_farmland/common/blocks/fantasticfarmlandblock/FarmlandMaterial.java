package io.github.itskilerluc.fantastic_farmland.common.blocks.fantasticfarmlandblock;

public enum FarmlandMaterial {
    DEFAULT(0, 0),
    FORTUNE(0, 1),
    SPEED(1, 0);

    public final int growthSpeed;
    public final int fortune;

    FarmlandMaterial(int growthSpeed, int fortune) {
        this.growthSpeed = growthSpeed;
        this.fortune = fortune;
    }
}
