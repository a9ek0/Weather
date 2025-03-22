package com.example.weather.component;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CacheTest {

  private Cache<String, String> weatherCache;

  @BeforeEach
  void setUp() {
    weatherCache = new Cache<>();
  }

  @Test
  void testPutAndGet_Success() {
    String key = "key";
    String value = "value";

    weatherCache.put(key, value);
    String retrievedValue = weatherCache.get(key);

    assertEquals(value, retrievedValue);
  }

  @Test
  void testGet_NonExistingKey_ReturnsNull() {
    // Act & Assert
    assertNull(weatherCache.get("nonExistingKey"));
  }

  @Test
  void testContainsKey_True() {
    String key = "key";
    String value = "value";
    weatherCache.put(key, value);

    assertTrue(weatherCache.containsKey(key));
  }

  @Test
  void testContainsKey_False() {
    assertFalse(weatherCache.containsKey("nonExistingKey"));
  }

  @Test
  void testEvict_Success() {
    String key = "key";
    String value = "value";
    weatherCache.put(key, value);

    weatherCache.evict(key);

    assertNull(weatherCache.get(key));
  }

  @Test
  void testClear_Success() {
    weatherCache.put("key1", "value1");
    weatherCache.put("key2", "value2");

    weatherCache.clear();

    assertNull(weatherCache.get("key1"));
    assertNull(weatherCache.get("key2"));
  }
}
