package com.example.weather;

import com.example.weather.entity.User;
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
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void testPostUser() throws Exception {
        User user = new User();

        user.setCountryCode("RU");
        user.setName("Iluha Zadvorkin");
        user.setEmail("qwe@mail.ru");

        mockMvc.perform(post("/weather/user")
                        .content(asJsonString(user))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("User was saved successfully!"));
    }

    @Test
    void testCreateUserForWeathers() throws Exception {
        User user = new User();

        user.setCountryCode("RU");
        user.setName("Iluha Zadverkin");
        user.setEmail("qwe@mail.ru");

        mockMvc.perform(post("/weather/user/ForWeathers")
                        .content(asJsonString(user))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("User was saved successfully!"));
    }

    @Test
    void testGetUser() throws Exception {
        Long id = 2402L;

        mockMvc.perform(get("/weather/user/id/{id}", id))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteUser() throws Exception {
        Long id = 2452L;

        mockMvc.perform(delete("/weather/user/delete/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().string("Deleted successfully!"));
    }

    @Test
    void testUpdateUser() throws Exception {
        Long id = 2402L;
        User updatedUser = new User();

        updatedUser.setCountryCode("RU");
        updatedUser.setName("Iluha NeZamolkin");
        updatedUser.setEmail("qwe@mail.ru");

        mockMvc.perform(put("/weather/user/update/{id}", id)
                        .content(asJsonString(updatedUser))
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
