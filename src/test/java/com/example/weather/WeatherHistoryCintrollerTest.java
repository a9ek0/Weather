package com.example.weather;

import com.example.weather.controller.WeatherController;
import com.example.weather.entity.Weather;
import com.example.weather.entity.WeatherHistory;
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
class WeatherHistoryCintrollerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void testCreateWeatherHistory() throws Exception {
        WeatherHistory weatherHistory = new WeatherHistory();

        weatherHistory.setDatetime("2024-03-03 23:59");
        weatherHistory.setDescription("nu tipa norm");
        weatherHistory.setTemp(16.0);
        weatherHistory.setRh(67.0);
        weatherHistory.setCountryCode("RU");

        String city = "Moscow";

        mockMvc.perform(post("/weather/history/city")
                        .param("city", city)
                        .content(asJsonString(weatherHistory))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdateWeatherHistory() throws Exception {
        String countryCode = "RU";
        String description = "Sunny";
        String dataTime = "2024-03-03T12:00:00";
        double temp = 25.0;
        double rh = 50.0;
        Long id = 2852L;

        mockMvc.perform(put("/weather/history/update")
                        .param("countryCode", countryCode)
                        .param("description", description)
                        .param("dataTime", dataTime)
                        .param("temp", String.valueOf(temp))
                        .param("rh", String.valueOf(rh))
                        .param("id", String.valueOf(id)))
                .andExpect(status().isOk());
        // Добавьте дополнительные проверки, если необходимо
    }

    @Test
    void testDeleteWeatherHistory() throws Exception {
        Long id = 2802L;

        mockMvc.perform(delete("/weather/history/delete/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().string("Deleted successfully!"));
    }

    @Test
    void testGetWeatherHistory() throws Exception {
        String city = "Moscow";

        mockMvc.perform(get("/weather/history/city/")
                        .param("city", city))
                .andExpect(status().isOk());
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
