package com.example.weather.service;

import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.stereotype.Service;

/**
 * The RequestCounterService class is responsible for counting the number of requests.
 * It provides methods to increment the request count and retrieve the current count.
 */
@Service
public class RequestCounterService {
  private AtomicInteger count = new AtomicInteger(0);

  public int increment() {
    return count.incrementAndGet();
  }

  public int getCount() {
    return count.get();
  }
}
