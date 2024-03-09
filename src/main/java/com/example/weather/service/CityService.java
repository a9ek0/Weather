package com.example.weather.service;

import com.example.weather.dto.CityDTO;
import com.example.weather.entity.City;
import com.example.weather.exception.IdNotFoundException;
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

    public City findCityById(Long userId) {
        if (userId != null)
            return cityRepo.findById(userId).get();
        return null;
    }

    public City findCityByCityName(String name) {
        if (name != null)
            return cityRepo.findByName(name);
        return null;
    }

    public Long delete(Long id) throws IdNotFoundException {
        if(cityRepo.findById(id).isEmpty())
            throw new IdNotFoundException("City with such id not found!");

        cityRepo.deleteById(id);
        return id;
    }

    public CityDTO complete(Long id, City updatedCity) {
        City city = cityRepo.findById(id).get();

        city.setName(updatedCity.getName());
        if (!updatedCity.getWeatherList().isEmpty())
            city.setWeatherList(updatedCity.getWeatherList());

        return CityDTO.toModel(cityRepo.save(city));
    }
}
