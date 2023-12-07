package com.kpi.invertedindex.models.concurrenthashmap;

import java.util.function.Function;

public class CustomConcurrentHashMap<K, V> {

    private static final class Segment {}

    private final Segment[] segments;
    private final Entry<K, V>[] table;

    public CustomConcurrentHashMap(int capacity) {

        table = (Entry<K, V>[]) new Entry[capacity];

        segments = new Segment[32];

        for (int i = 0; i < segments.length; i++) {
            segments[i] = new Segment();
        }
    }

    private static int hash(Object key) {
        int h = key.hashCode();
        return (h << 7) - h + (h >>> 9) + (h >>> 17);
    }

    private int getBucketIndex(int hash) {
        return hash & (table.length - 1);
    }

    private Segment synchronizedSegment(int hash) {
        return segments[hash & 0x1F];
    }

    private void validateKeyAndValue(K key, V value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException("Key or value cannot be null");
        }
    }

    private void validateKey(Object key) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }
    }

    private boolean isKeyEquals(Object key, int hash, Entry<K, V> entry) {
        return entry.hash == hash &&
                entry.key == key ||
                (entry.key != null && entry.key.equals(key));
    }

    public void put(K key, V value) {

        validateKeyAndValue(key, value);

        int hash = hash(key);

        synchronized (synchronizedSegment(hash)) {
            int index = getBucketIndex(hash);
            updateOrAddEntry(key, value, hash, index);
        }
    }

    private void updateOrAddEntry(K key, V value, int hash, int index) {
        Entry<K, V> first = table[index];
        for (Entry<K, V> e = first; e != null; e = e.next) {
            if (isKeyEquals(key, hash, e)) {
                e.value = value;
                return;
            }
        }
        table[index] = new Entry<>(hash, key, value, first);
    }

    public V get(Object key) {
        validateKey(key);
        int hash = hash(key);
        int index = getBucketIndex(hash);
        return findValueByKey(key, hash, index);
    }

    private V findValueByKey(Object key, int hash, int index) {
        for (Entry<K, V> e = table[index]; e != null; e = e.next) {
            if (isKeyEquals(key, hash, e)) {
                return e.value;
            }
        }

        return null;
    }

    public V computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction) {
        validateKey(key);
        int hash = hash(key);

        synchronized (synchronizedSegment(hash)) {
            V existingValue = get(key);

            if (existingValue != null) {
                return existingValue;
            }

            V newValue = mappingFunction.apply(key);

            if (newValue != null) {
                put(key, newValue);
            }

            return newValue;
        }
    }

}
