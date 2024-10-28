package io.github.itskilerluc.fantastic_farmland.common.util;

import com.google.common.collect.HashBiMap;
import org.apache.commons.lang3.ArrayUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class DividingList<T> implements List<T> {
    private final int[] values;
    private final HashBiMap<Integer, DividingEntry<T>> entries = HashBiMap.create();

    private final int slots;

    public DividingList(int slots) {
        this.values = new int[slots];
        this.slots = slots;
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
        return entries.values().stream().anyMatch(entry -> o.equals(entry.value()));
    }

    @NotNull
    @Override
    public Iterator<T> iterator() {
        return Arrays.stream(values).mapToObj(integer ->
                Util.ifNotNull(entries.get(integer), DividingEntry::value)).iterator();
    }

    @NotNull
    @Override
    public Object @NotNull [] toArray() {
        return Arrays.stream(values).mapToObj(integer ->
                Util.ifNotNull(entries.get(integer), DividingEntry::value)).toList().toArray();
    }

    @NotNull
    @Override
    public <T1> T1 @NotNull [] toArray(@NotNull T1 @NotNull [] a) {
        return Arrays.stream(values).mapToObj(integer ->
                Util.ifNotNull(entries.get(integer), DividingEntry::value)).toList().toArray(a);
    }

    @Override
    public boolean add(T t) {
        return add(t, getFreeSlot(1), 1);
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
            if(!contains(o)) return false;
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
        return false;
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
        return Util.ifNotNull(entries.get(values[index]), DividingEntry::value);
    }

    @Override
    public T set(int index, T element) {
        var element = Util.ifNotNull(values[index]

        return null;
    }

    @Override
    public void add(int index, T element) {

    }

    @Override
    public T remove(int index) {
        return null;
    }

    @Override
    public int indexOf(Object o) {
        return 0;
    }

    @Override
    public int lastIndexOf(Object o) {
        return 0;
    }

    @NotNull
    @Override
    public ListIterator<T> listIterator() {
        return null;
    }

    @NotNull
    @Override
    public ListIterator<T> listIterator(int index) {
        return null;
    }

    @NotNull
    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        return List.of();
    }


    record DividingEntry<T>(@Nullable T value, int size) {
        public boolean isEmpty() {
            return value == null;
        }

        @Override
        public int hashCode() {
            return Objects.hash(value, size);
        }
    }
}
