package com.example.weather.component;

import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

/**
 * A simple caching component based on a LinkedHashMap with a maximum number of entries.
 *
 * @param <K> the type of keys maintained by this cache
 * @param <V> the type of mapped values
 */
@Component
public class Cache<K, V> {
  private static final int MAX_ENTRIES = 100;

  private final Map<K, V> weatherCache = new LinkedHashMap<>(MAX_ENTRIES, 0.75f, true) {
    @Override
    protected boolean removeEldestEntry(Map.Entry eldest) {
      return size() > MAX_ENTRIES;
    }
  };

  public V get(K key) {
    return weatherCache.get(key);
  }

  public void put(K key, V value) {
    weatherCache.put(key, value);
  }

  public boolean containsKey(K key) {
    return weatherCache.containsKey(key);
  }

  public void evict(K key) {
    weatherCache.remove(key);
  }

  public void clear() {
    weatherCache.clear();
  }
}