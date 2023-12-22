package com.kpi.invertedindex.models.concurrenthashmap;

import java.util.function.Function;

public class CustomConcurrentHashMap<K, V> {

    private static final class Segment {}

    private final Segment[] segments;
    private final Entry<K, V>[] table;
    private final int SEGMENTS_NUMBER = 32;

    public CustomConcurrentHashMap(int capacity) {
        table = (Entry<K, V>[]) new Entry[capacity];

        segments = new Segment[SEGMENTS_NUMBER];
        initSegments();
    }

    private void initSegments() {
        for (int i = 0; i < segments.length; i++) {
            segments[i] = new Segment();
        }
    }

    private static int hash(Object key) {
        return key.hashCode();
    }

    private int getBucketIndex(int hash) {
        return hash & (table.length - 1);
    }

    private Segment synchronizedSegment(int hash) {
        return segments[getSegmentIndex(hash)];
    }

    private int getSegmentIndex(int hash) {
        return hash & (segments.length - 1);
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
            updateOrAddEntry(key, value, hash);
        }
    }

    private void updateOrAddEntry(K key, V value, int hash) {
        int index = getBucketIndex(hash);
        Entry<K, V> first = table[index];

        for (Entry<K, V> e = first; e != null; e = e.next) {
            if (isKeyEquals(key, hash, e)) {
                e.value = value;
                return;
            }
        }

        table[index] = new Entry<>(hash, key, value, first);
    }

    public V getOrDefault(K key, V defaultValue) {
        V value = get(key);
        return value != null ? value : defaultValue;
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
                addEntry(key, newValue, hash);
            }

            return newValue;
        }
    }

    private void addEntry(K key, V value, int hash) {
        int index = getBucketIndex(hash);
        Entry<K, V> first = table[index];
        table[index] = new Entry<>(hash, key, value, first);
    }

}
