package com.example.weather.service;

import com.example.weather.dto.CityDTO;
import com.example.weather.entity.City;
import com.example.weather.entity.Weather;
import com.example.weather.exception.UserNotFoundException;
import com.example.weather.repository.CityRepo;
import com.example.weather.repository.WeatherRepo;
import org.springframework.stereotype.Service;

@Service
public class CityService {

    final
    CityRepo cityRepo;

    final
    WeatherRepo weatherRepo;

    public CityService(CityRepo cityRepo, WeatherRepo weatherRepo) {
        this.cityRepo = cityRepo;
        this.weatherRepo = weatherRepo;
    }

    public City cityResponse(City city) {
        return cityRepo.save(city);
    }

    public CityDTO getCity(Long id) throws UserNotFoundException {
        City city = cityRepo.findById(id).get();
        if (city == null) {
            throw new UserNotFoundException("User not found!");
        }
        return CityDTO.toModel(city);
    }

    public CityDTO createUser(City city, String countryCode) {
        Weather weather = weatherRepo.findByCityName(countryCode);
        city.getWeatherList().add(weather);
        return CityDTO.toModel(cityRepo.save(city));
    }

    public City findUserById(Long userId) {
        if (userId != null)
            return cityRepo.findById(userId).get();
        return null;
    }

    public Long delete(Long id) {
        cityRepo.deleteById(id);
        return id;
    }

    public CityDTO complete(Long id, City updatedCity) {
        City city = cityRepo.findById(id).get();

        city.setName(updatedCity.getName());
        if(!updatedCity.getWeatherList().isEmpty())
            city.setWeatherList(updatedCity.getWeatherList());

        return CityDTO.toModel(cityRepo.save(city));
    }
}
