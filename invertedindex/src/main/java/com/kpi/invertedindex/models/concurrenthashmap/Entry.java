package com.kpi.invertedindex.models.concurrenthashmap;

import java.util.Map;

class Entry<K, V> implements Map.Entry<K, V> {

    final K key;
    volatile V value;
    final int hash;
    Entry<K, V> next;

    Entry(int hash, K key, V value, Entry<K, V> next) {
        this.hash = hash;
        this.key = key;
        this.value = value;
        this.next = next;
    }

    @Override
    public K getKey() {
        return key;
    }

    @Override
    public V getValue() {
        return value;
    }

    @Override
    public V setValue(V newValue) {
        V oldValue = this.value;
        this.value = newValue;
        return oldValue;
    }
}
