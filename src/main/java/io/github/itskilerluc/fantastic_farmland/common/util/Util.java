package io.github.itskilerluc.fantastic_farmland.common.util;

import java.util.function.Function;

public class Util {
    public static <F, T> T ifNotNull(F value, Function<F, T> func) {
        if (value == null) return null;
        return func.apply(value);
    }
}
