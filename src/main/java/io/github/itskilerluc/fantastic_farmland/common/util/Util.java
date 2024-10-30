package io.github.itskilerluc.fantastic_farmland.common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class Util {
    public static <F, T> T ifNotNull(F value, Function<F, T> func) {
        if (value == null) return null;
        return func.apply(value);
    }

    public static <T> boolean startsWith(List<T> list, List<T> start) {
        if (list.size() < start.size()) return false;
        for (int i = 0; i < start.size(); i++) {
            if (!list.get(i).equals(start.get(i))) return false;
        }
        return true;
    }

    public static <T> List<T> composite(List<T> list, T item) {
        List<T> newList = new ArrayList<>(list);
        newList.add(item);
        return newList;
    }
}
