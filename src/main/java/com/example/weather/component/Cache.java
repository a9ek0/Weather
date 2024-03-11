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
  private static final int MAX_ENTRIES = 10;

  private final Map<K, V> weatherCache = new LinkedHashMap<>(MAX_ENTRIES, 0.75f, true) {
    @Override
    protected boolean removeEldestEntry(Map.Entry eldest) {
      return size() > MAX_ENTRIES;
    }
  };

  public V get(K key) {
    System.out.println("get");
    return weatherCache.get(key);
  }

  public void put(K key, V value) {
    System.out.println("put");
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
/*public class Cache {
    private final Map<String, Object> weatherCache = new HashMap<>();

    public Object get(String key) {
        return weatherCache.get(key);
    }

    public void put(String key, Object value) {
        weatherCache.put(key, value);
    }

    public boolean containsKey(String key) {
        return weatherCache.containsKey(key);
    }

    public void evict(String key) {
        weatherCache.remove(key);
    }

    public void clear() {
        weatherCache.clear();
    }
}*/
