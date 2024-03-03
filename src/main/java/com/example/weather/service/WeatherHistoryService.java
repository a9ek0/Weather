package com.example.weather.service;

import com.example.weather.dto.WeatherHistoryDTO;
import com.example.weather.entity.Weather;
import com.example.weather.entity.WeatherHistory;
import com.example.weather.repository.WeatherHistoryRepo;
import com.example.weather.repository.WeatherRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        weatherHistory.setDatetime(weather.getDateTime());
        weatherHistory.setTemp(weather.getTemp());
        weatherHistory.setRh(weather.getRh());
        weatherHistory.setCountryCode(weather.getCountryCode());

        weatherHistory.setWeather(weatherByCityName);
        return WeatherHistoryDTO.toModel(weatherHistoryRepo.save(weatherHistory));
    }

    public WeatherHistoryDTO complete(Long id, String countryCode, double temp, double rh, String dataTime, String description) {
        WeatherHistory weatherHistory = weatherHistoryRepo.findById(id).get();

        weatherHistory.setCountryCode(countryCode);
        weatherHistory.setTemp(temp);
        weatherHistory.setRh(rh);
        weatherHistory.setDatetime(dataTime);
        weatherHistory.setDescription(description);

        return WeatherHistoryDTO.toModel(weatherHistoryRepo.save(weatherHistory));
    }
}
