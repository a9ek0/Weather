package com.example.weather.service;

import com.example.weather.dto.WeatherHistoryDTO;
import com.example.weather.entity.Weather;
import com.example.weather.entity.WeatherHistory;
import com.example.weather.exception.CityNotFoundException;
import com.example.weather.repository.WeatherHistoryRepo;
import com.example.weather.repository.WeatherRepo;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class WeatherHistoryService {
    private final WeatherHistoryRepo weatherHistoryRepo;

    private final WeatherRepo weatherRepo;

    public WeatherHistoryService(WeatherHistoryRepo weatherHistoryRepo, WeatherRepo weatherRepo) {
        this.weatherHistoryRepo = weatherHistoryRepo;
        this.weatherRepo = weatherRepo;
    }

    public WeatherHistoryDTO createWeatherHistory(WeatherHistory weatherHistory, String city) {
        Weather weather = weatherRepo.findByCityName(city);
        weatherHistory.setWeather(weather);
        return WeatherHistoryDTO.toModel(weatherHistoryRepo.save(weatherHistory));
    }

    public WeatherHistoryDTO createWeatherHistory(WeatherHistory weatherHistory, Weather weather, String city) {
        Weather weatherByCityName = weatherRepo.findByCityName(city);

        weatherHistory.setWeather(weather);
        weatherHistory.setDescription(weather.getDescription());
        weatherHistory.setDateTime(weather.getDateTime());
        weatherHistory.setTemp(weather.getTemp());
        weatherHistory.setRh(weather.getRh());
        weatherHistory.setCountryCode(weather.getCountryCode());

        weatherHistory.setWeather(weatherByCityName);
        return WeatherHistoryDTO.toModel(weatherHistoryRepo.save(weatherHistory));
    }

    public WeatherHistoryDTO complete(Long id, String countryCode, double temp, double rh, LocalDateTime dataTime, String description) {
        WeatherHistory weatherHistory = weatherHistoryRepo.findById(id).get();

        weatherHistory.setCountryCode(countryCode);
        weatherHistory.setTemp(temp);
        weatherHistory.setRh(rh);
        weatherHistory.setDateTime(dataTime);
        weatherHistory.setDescription(description);

        return WeatherHistoryDTO.toModel(weatherHistoryRepo.save(weatherHistory));
    }

    public Long delete(Long id) {
        weatherHistoryRepo.deleteById(id);
        return id;
    }

    public List<WeatherHistoryDTO> getWeather(String city) throws CityNotFoundException {
        Weather weather = weatherRepo.findByCityName(city);

        if (weather == null) {
            throw new CityNotFoundException("City not found!");
        }

        return weather.getWeatherHistoryList().stream().map(WeatherHistoryDTO::toModel).toList();
    }
}
