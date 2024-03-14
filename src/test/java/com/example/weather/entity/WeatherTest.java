package com.example.weather.entity;

import com.example.weather.service.WeatherService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WeatherTest {

  @Mock
  private JsonNode jsonNode;
  @InjectMocks
  private WeatherService weatherService;
  @Mock
  ObjectMapper objectMapper;
 /* @Test
  void testWeatherCreationFromJson() {
    String jsonString = "{\n" +
            "    \"cityName\": \"Moscow\",\n" +
            "    \"dateTime\": \"2024-02-29 16:07\",\n" +
            "    \"description\": \"Cloudy\",\n" +
            "    \"temp\": 4.7,\n" +
            "    \"rh\": 59.2,\n" +
            "    \"countryCode\" : \"RU\"\n" +
            "}";
    try {
      jsonNode = objectMapper.readTree(jsonString);
    } catch (JsonProcessingException e) {
      System.err.println("Error parsing JSON: " + e.getMessage());
      throw new RuntimeException(e);
    }

    //JsonNode dataNode = jsonNode.get("data").get(0);
    Weather weather = new Weather(jsonNode);

    assertEquals("Cloudy", weather.getDescription());
    assertEquals("RU", weather.getCountryCode());
    assertEquals("Moscow", weather.getCityName());
    assertEquals(LocalDateTime.parse("2024-02-29 16:07", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), weather.getDateTime());
    assertEquals(4.7, weather.getTemp());
    assertEquals(59.2, weather.getRh());
  }*/

}
