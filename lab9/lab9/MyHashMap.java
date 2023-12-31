package lab9;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * A hash table-backed Map implementation. Provides amortized constant time
 * access to elements via get(), remove(), and put() in the best case.
 *
 * @author Shiming Fu
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

    private static final int DEFAULT_SIZE = 16;
    private static final double MAX_LF = 0.75;

    private ArrayMap<K, V>[] buckets;
    private int size;

    private int loadFactor() {
        return size / buckets.length;
    }

    public MyHashMap() {
        buckets = new ArrayMap[DEFAULT_SIZE];
        this.clear();
    }

    /* Removes all of the mappings from this map. */
    @Override
    public void clear() {
        this.size = 0;
        for (int i = 0; i < this.buckets.length; i += 1) {
            this.buckets[i] = new ArrayMap<>();
        }
    }

    /**
     * Computes the hash function of the given key. Consists of
     * computing the hashcode, followed by modding by the number of buckets.
     * To handle negative numbers properly, uses floorMod instead of %.
     */
    private int hash(K key) {
        if (key == null) {
            return 0;
        }

        int numBuckets = buckets.length;
        return Math.floorMod(key.hashCode(), numBuckets);
    }

    /*
     * Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        int hash = hash(key);
        return buckets[hash].get(key);
    }

    /* Associates the specified value with the specified key in this map. */
    @Override
    public void put(K key, V value) {
        if (loadFactor() > MAX_LF) {
            resize();
        }
        int hash = hash(key);
        if (!buckets[hash].containsKey(key)) {
            size += 1;
        }
        buckets[hash].put(key, value);
    }

    private void resize() {
        ArrayMap<K, V>[] oldbuckets = buckets;
        buckets = new ArrayMap[buckets.length * 2];
        for (int i = 0; i < buckets.length; i++) {
            buckets[i] = new ArrayMap<K, V>();
        }
        for (ArrayMap<K, V> oldbucket : oldbuckets) {
            for (K key : oldbucket.keySet()) {
                V value = oldbucket.get(key);
                int hash = hash(key);
                buckets[hash].put(key, value);
            }
        }
    }

    /* Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return size;
    }

    //////////////// EVERYTHING BELOW THIS LINE IS OPTIONAL ////////////////

    /* Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
        Set<K> keySet = new HashSet<>();
        for (ArrayMap<K, V> bucket : buckets) {
            for (K key : bucket.keySet()) {
                keySet.add(key);
            }
        }
        return keySet;
    }

    /*
     * Removes the mapping for the specified key from this map if exists.
     * Not required for this lab. If you don't implement this, throw an
     * UnsupportedOperationException.
     */
    @Override
    public V remove(K key) {
        if (!containsKey(key)) {
            return null;
        }
        int hash = hash(key);
        size -= 1;
        return buckets[hash].remove(key);
    }

    /*
     * Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Not required for this lab. If you don't implement this,
     * throw an UnsupportedOperationException.
     */
    @Override
    public V remove(K key, V value) {
        if (!containsKey(key) || get(key) != value) {
            return null;
        }
        int hash = hash(key);
        size -= 1;
        return buckets[hash].remove(key);
    }

    @Override
    public Iterator<K> iterator() {
        return keySet().iterator();
    }
}
