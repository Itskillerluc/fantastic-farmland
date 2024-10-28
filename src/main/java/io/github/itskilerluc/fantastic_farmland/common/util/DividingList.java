package io.github.itskilerluc.fantastic_farmland.common.util;

import com.google.common.collect.HashBiMap;
import it.unimi.dsi.fastutil.Pair;
import it.unimi.dsi.fastutil.ints.IntIntPair;
import org.apache.commons.lang3.ArrayUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;

public class DividingList<T> implements List<T> {
    private final int[] values;
    private final HashBiMap<Integer, DividingEntry<T>> entries = HashBiMap.create();

    private final int slots;

    public DividingList(int slots) {
        this.values = new int[slots];
        this.slots = slots;
        clear();
    }

    public int getSlots() {
        return slots;
    }

    public int getFreeSlots() {
        int freeSlots = 0;
        for (int i : values) {
            var entry = entries.get(i);
            if (entry == null || entry.isEmpty()) {
                freeSlots++;
            }
        }
        return freeSlots;
    }

    @Override
    public int size() {
        return entries.size();
    }

    @Override
    public boolean isEmpty() {
        return getFreeSlots() == getSlots();
    }

    private boolean isEmpty(int[] list) {
        for (int i : list) {
            var entry = entries.get(i);
            if (entry != null && !entry.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean contains(Object o) {
        for (DividingEntry<T> entry : entries.values()) {
            if (o.equals(entry.value())) {
                return true;
            }
        }
        return false;
    }

    @NotNull
    @Override
    public Iterator<T> iterator() {
        return Arrays.stream(values)
                .mapToObj(this::getValue)
                .iterator();
    }

    @NotNull
    @Override
    public Object @NotNull [] toArray() {
        List<T> list = new ArrayList<>();
        for (int value : values) {
            T t = getValue(value);
            list.add(t);
        }
        return list.toArray();
    }

    @NotNull
    @Override
    public <T1> T1 @NotNull [] toArray(@NotNull T1 @NotNull [] a) {
        List<T> list = new ArrayList<>();
        for (int value : values) {
            T t = getValue(value);
            list.add(t);
        }
        return list.toArray(a);
    }

    @Override
    public boolean add(T t) {
        return add(t, getFreeSlot(1), 1);
    }

    public boolean add(T t, int size) {
        return add(t, getFreeSlot(size), size);
    }

    public int getFreeSlot(int size) {
        int freeSlots = 0;
        for (int i = 0; i < values.length; i++) {
            DividingEntry<T> dividingEntry = entries.get(values[i]);
            if (dividingEntry == null || dividingEntry.isEmpty()) {
                freeSlots++;
            }
            if (freeSlots >= size) {
                return i - freeSlots + 1;
            }
        }
        return -1;
    }

    public boolean add(T t, int index, int size) {
        if (values.length < index + size || !isEmpty(ArrayUtils.subarray(values, index, index + size))) {
            return false;
        }
        var entry = new DividingEntry<>(t, size);
        entries.put(entry.hashCode(), entry);
        for (int i = index; i < index + size; i++) {
            values[i] = entry.hashCode();
        }
        return true;
    }

    @Override
    public boolean remove(Object o) {
        for (Map.Entry<DividingEntry<T>, Integer> dividingEntryIntegerEntry : entries.inverse().entrySet()) {
            if (o.equals(dividingEntryIntegerEntry.getValue())) {
                entries.inverse().replace(new DividingEntry<>(
                        null, dividingEntryIntegerEntry.getKey().size), dividingEntryIntegerEntry.getValue());
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsAll(@NotNull Collection<?> c) {
        for (Object o : c) {
            if (!contains(o)) return false;
        }
        return true;
    }

    @Override
    public boolean addAll(@NotNull Collection<? extends T> c) {
        for (T t : c) {
            add(t);
        }
        return true;
    }

    @Override
    public boolean addAll(int index, @NotNull Collection<? extends T> c) {
        int i = 0;
        for (T t : c) {
            add(index + i, t);
            i++;
        }
        return true;
    }

    @Override
    public boolean removeAll(@NotNull Collection<?> c) {
        boolean toReturn = true;
        for (Object o : c) {
            if (!remove(o)) {
                toReturn = false;
            }
        }
        return toReturn;
    }

    @Override
    public boolean retainAll(@NotNull Collection<?> c) {
        for (int value : values) {
            var result = entries.get(value);
            if (result != null) {
                if (c.contains(result.value)) {
                    continue;
                }
            }
            remove(result);
        }
        return true;
    }

    @Override
    public void clear() {
        entries.clear();
        var empty = new DividingEntry<T>(null, 5);
        entries.put(empty.hashCode(), empty);
        Arrays.setAll(values, value -> empty.hashCode());
    }

    @Override
    public T get(int index) {
        return getValue(values[index]);
    }

    public T getValue(int key) {
        return Util.ifNotNull(entries.get(key), DividingEntry::value);
    }

    @Override
    public T set(int index, T element) {
        var old = get(index);
        var entry = new DividingEntry<>(element, 1);
        entries.put(entry.hashCode(), entry);
        values[index] = entry.hashCode();
        return old;
    }

    @Override
    public void add(int index, T element) {
        var old = get(index);
        if (old == null) {
            set(index, element);
        }
    }

    @Override
    public T remove(int index) {
        var old = get(index);
        entries.remove(old.hashCode());
        var empty = new DividingEntry<T>(null, 1);
        entries.put(empty.hashCode(), empty);
        values[index] = empty.hashCode();
        return old;
    }

    @Override
    public int indexOf(Object o) {
        for (int i = 0; i < values.length; i++) {
            if (o.equals(getValue(i))) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        for (int i = values.length - 1; i >= 0; i--) {
            if (o.equals(getValue(i))) {
                return i;
            }
        }
        return -1;
    }

    @NotNull
    @Override
    public ListIterator<T> listIterator() {
        List<T> list = new ArrayList<>();
        for (int value : values) {
            T t = getValue(value);
            list.add(t);
        }
        return list.listIterator();
    }

    @NotNull
    @Override
    public ListIterator<T> listIterator(int index) {
        List<T> list = new ArrayList<>();
        for (int value : values) {
            T t = getValue(value);
            list.add(t);
        }
        return list.listIterator(index);
    }

    @NotNull
    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        List<T> list = new ArrayList<>();
        long limit = toIndex - fromIndex;
        long toSkip = fromIndex;
        for (int value : values) {
            T t = getValue(value);
            if (toSkip > 0) {
                toSkip--;
                continue;
            }
            if (limit-- == 0) break;
            list.add(t);
        }
        return list;
    }

    public void split(Function<T, Pair<T, T>> splitter, int... splits) {
        for (int split : splits) {
            if (get(split).equals(get(split + 1))) {
                var entry = getEntry(split);
                int size = entry.size;

                var entryStartIndex = indexOf(entry.value());
                int index = split - entryStartIndex;

                IntIntPair dividedSizes = IntIntPair.of(index + 1, size - index - 1);
                Pair<T, T> dividedValue = splitter.apply(entry.value());

                var first = new DividingEntry<T>(dividedValue.first(), dividedSizes.firstInt());
                var second = new DividingEntry<T>(dividedValue.second(), dividedSizes.secondInt());

                entries.put(first.hashCode(), first);
                entries.put(second.hashCode(), second);
                entries.remove(entry.hashCode());

                values[split] = first.hashCode();
                values[split + 1] = second.hashCode();
            }
        }
    }

    public void join(BiFunction<T, T, T> joiner, int... junctions) {
        for (int junction : junctions) {
            if (get(junction).equals(get(junction + 1))) {
                var entry = getEntry(junction);
                int firstSize = entry.size;

                var secondEntry = getEntry(junction + 1);
                int secondSize = secondEntry.size;

                entries.remove(entry.hashCode());
                entries.remove(secondEntry.hashCode());

                var newEntry = new DividingEntry<T>(joiner.apply(entry.value(), secondEntry.value()),
                        firstSize + secondSize);

                entries.put(newEntry.hashCode(), newEntry);
                values[junction] = newEntry.hashCode();
                values[junction + 1] = newEntry.hashCode();
            }
        }
    }

    public void fill(T element) {
        entries.clear();
        var entry = new DividingEntry<>(element, slots);
        entries.put(entry.hashCode(), entry);
        Arrays.fill(values, entry.hashCode());
    }

    public DividingEntry<T> getEntry(int index) {
        return entries.get(values[index]);
    }

    public record DividingEntry<T>(@Nullable T value, int size) {
        public boolean isEmpty() {
            return value == null;
        }

        @Override
        public int hashCode() {
            return Objects.hash(value, size);
        }
    }
}
