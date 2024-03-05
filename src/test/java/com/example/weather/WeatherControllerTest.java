package com.example.weather;

import com.example.weather.entity.Weather;
import com.example.weather.service.DateTimeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class WeatherControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void testPostWeather() throws Exception {
        Weather weather = new Weather.Builder()
                .rh(60.0)
                .temp(10.0)
                .userList(null)
                .countryCode("RU")
                .cityName("Moscow")
                .description("*******")
                .dateTime(DateTimeService.toDateTime("2024-03-03 23:59"))
                .build();

        mockMvc.perform(post("/weather")
                        .content(asJsonString(weather))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Weather was saved successfully!"));
    }

    @Test
    void testGetWeatherDB() throws Exception {
        String city = "Moscow";

        mockMvc.perform(get("/weather/db/{city}", city))
                .andExpect(status().isOk());
    }

    @Test
    void testGetWeather() throws Exception {
        String city = "Moscow";

        mockMvc.perform(get("/weather/city")
                        .param("cityName", city))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteWeather() throws Exception {
        Long id = 2252L;

        mockMvc.perform(delete("/weather/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().string("Deleted successfully!"));
    }

    @Test
    void testUpdateWeather() throws Exception {
        Long id = 2202L;
        Weather updatedWeather = new Weather.Builder()
                .rh(60.0)
                .temp(10.0)
                .countryCode("RU")
                .cityName("Moscow")
                .description("*******")
                .dateTime(DateTimeService.toDateTime("2024-03-03 23:59"))
                .build();

        mockMvc.perform(put("/weather/update/id/")
                        .param("id", String.valueOf(id))
                        .content(asJsonString(updatedWeather))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Updated successfully!"));
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}