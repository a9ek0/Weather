package com.example.weather.service;

import com.example.weather.entity.Weather;
import com.example.weather.exception.CityNotFoundException;
import com.example.weather.exception.JsonReadingException;
import com.example.weather.exception.WeatherNotFoundException;
import com.example.weather.dto.WeatherDTO;
import com.example.weather.repository.WeatherRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class WeatherService {

    private final ObjectMapper objectMapper;

    private final WeatherRepo weatherRepo;

    @Autowired
    public WeatherService(ObjectMapper objectMapper, WeatherRepo weatherRepo) {
        this.objectMapper = objectMapper;
        this.weatherRepo = weatherRepo;
    }


    public Weather weatherResponse(Weather weather) {
        return weatherRepo.save(weather);
    }

    public WeatherDTO getWeather(Long id) throws WeatherNotFoundException {
        Weather weather = weatherRepo.findById(id).get();
        if (weather == null) {
            throw new WeatherNotFoundException("Weather not found!");
        }
        return WeatherDTO.toModel(weather);
    }

    public List<WeatherDTO> getWeather(String city) throws CityNotFoundException {
        List<Weather> weathers = weatherRepo.findByCityName(city);

        if (weathers.isEmpty()) {
            throw new CityNotFoundException("City not found!");
        }

        return weathers.stream().map(WeatherDTO::toModel).toList();
    }

    public List<Weather> findWeather(String city) {
        return weatherRepo.findByCityName(city);
    }


    public Long delete(Long id) {
        weatherRepo.deleteById(id);
        return id;
    }

    public Weather processJson(String jsonResponse) {
        try {
            return objectMapper.readValue(jsonResponse, Weather.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Weather processApiUrl(String apiUrl) throws JsonReadingException {
        RestTemplate restTemplate = new RestTemplate();

        String jsonString = restTemplate.getForObject(apiUrl, String.class);

        JsonNode jsonNode = null;
        try {
            jsonNode = objectMapper.readTree(jsonString);
        } catch (JsonProcessingException e) {
            throw new JsonReadingException("Json reading error!");
        }
        JsonNode dataNode = jsonNode.get("data").get(0);

        return new Weather(dataNode);
    }

    public List<Weather> getWeatherByCountryCode(String countryCode) {
        return weatherRepo.findByCountryCode(countryCode);
    }

    public WeatherDTO complete(Long id, Weather updatedWeather) {
        Weather weather = weatherRepo.findById(id).get();

        weather.setCountryCode(updatedWeather.getCountryCode());
        weather.setTemp(updatedWeather.getTemp());
        weather.setRh(updatedWeather.getRh());
        weather.setDateTime(updatedWeather.getDateTime());
        weather.setDescription(updatedWeather.getDescription());
        weather.setCityName(updatedWeather.getCityName());
        weather.setUserList(updatedWeather.getUserList());

        return WeatherDTO.toModel(weatherRepo.save(weather));
    }
}
