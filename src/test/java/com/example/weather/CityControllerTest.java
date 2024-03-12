package com.example.weather;

import com.example.weather.controller.CityController;
import com.example.weather.entity.City;
import com.example.weather.service.CityService;
import com.example.weather.service.WeatherService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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
class CityControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Mock
    private CityService cityService;

    @Mock
    private WeatherService weatherService;

    @InjectMocks
    private CityController cityController;

    @Test
    void testCityResponse() throws Exception {
        City city = new City();

        city.setName("Katowice");

        mockMvc.perform(post("/weather/city")
                        .content(asJsonString(city))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("City was saved successfully!"));
    }

    @Test
    void testGetCity() throws Exception {
        Long id = 552L;

        mockMvc.perform(get("/weather/city/id/{id}", id))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteCity() throws Exception {
        Long id = 1802L;

        mockMvc.perform(delete("/weather/city/delete/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().string("Deleted successfully!"));
    }

    @Test
    void testUpdateCity() throws Exception {
        Long id = 702L;
        City updatedCity = new City();

        updatedCity.setName("Grodno");

        mockMvc.perform(put("/weather/city/update/{id}", id)
                        .content(asJsonString(updatedCity))
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
