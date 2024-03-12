package com.example.weather.service;

import com.example.weather.dto.WeatherDto;
import com.example.weather.entity.City;
import com.example.weather.entity.Weather;
import com.example.weather.exception.CityNotFoundException;
import com.example.weather.exception.IdNotFoundException;
import com.example.weather.exception.JsonReadingException;
import com.example.weather.exception.WeatherNotFoundException;
import com.example.weather.repository.CityRepo;
import com.example.weather.repository.WeatherRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Service class for handling Weather-related operations.
 */
@Service
public class WeatherService {

  private final ObjectMapper objectMapper;

  final CityRepo cityRepo;
  private final WeatherRepository weatherRepository;

  /**
   * Constructor for the WeatherService class.
   *
   * @param objectMapper ObjectMapper for handling JSON.
   * @param weatherRepository  Weather repository for data access.
   */
  @Autowired
  public WeatherService(ObjectMapper objectMapper, WeatherRepository weatherRepository, CityRepo cityRepo) {
    this.objectMapper = objectMapper;
    this.weatherRepository = weatherRepository;
    this.cityRepo = cityRepo;
  }

  /**
   * Saves a Weather object to the repository.
   *
   * @param weather Weather object to be saved.
   * @return Saved Weather object.
   */
  public Weather weatherResponse(Weather weather) {
    return weatherRepository.save(weather);
  }

  /**
   * Retrieves a WeatherDto object by the specified ID.
   *
   * @param id ID of the Weather object.
   * @return WeatherDto object.
   * @throws WeatherNotFoundException if the Weather object is not found.
   */
  public WeatherDto getWeather(Long id) throws WeatherNotFoundException {
    Weather weather = weatherRepository.findById(id).get();
    if (weather == null) {
      throw new WeatherNotFoundException("Weather not found!");
    }
    return WeatherDto.toModel(weather);
  }

  /**
   * Retrieves a list of WeatherDto objects by the specified city name.
   *
   * @param city Name of the city.
   * @return List of WeatherDto objects.
   * @throws CityNotFoundException if the city is not found.
   */
  public List<WeatherDto> getWeather(String city) throws CityNotFoundException {
    List<Weather> weathers = weatherRepository.findByCityName(city);

    if (weathers.isEmpty()) {
      throw new CityNotFoundException("city not found");
    }

    return weathers.stream().map(WeatherDto::toModel).toList();
  }

  /**
   * Retrieves a list of WeatherDto objects by the specified city name using a custom query.
   *
   * @param city Name of the city.
   * @return List of WeatherDto objects.
   * @throws CityNotFoundException if the city is not found.
   */
  public List<WeatherDto> getWeatherDb(String city) throws CityNotFoundException {
    List<Weather> weathers = weatherRepository.findWeatherByCityName(city);

    if (weathers.isEmpty()) {
      throw new CityNotFoundException("city not found");
    }

    return weathers.stream().map(WeatherDto::toModel).toList();
  }

  public List<Weather> findWeather(String city) {
    return weatherRepository.findByCityName(city);
  }


  /**
   * Deletes a Weather object by the specified ID.
   *
   * @param id ID of the Weather object to be deleted.
   * @return Deleted Weather object ID.
   * @throws IdNotFoundException if the Weather object with the specified ID is not found.
   */
  public Long delete(Long id) throws IdNotFoundException {
    if (weatherRepository.findById(id).isEmpty()) {
      throw new IdNotFoundException("weather with such id not found");
    }
    weatherRepository.deleteById(id);
    return id;
  }

  /**
   * Processes the JSON response from the weather API and returns a Weather object.
   *
   * @param jsonResponse JSON response from the weather API.
   * @return Processed Weather object.
   */
  public Weather processJson(String jsonResponse) {
    try {
      return objectMapper.readValue(jsonResponse, Weather.class);
    } catch (Exception e) {
      Logger logger = Logger.getLogger(getClass().getName());
      logger.info("error occurred");
      return null;
    }
  }

  /**
   * Processes the weather API URL, retrieves the JSON response, and returns a Weather object.
   *
   * @param apiUrl URL of the weather API.
   * @return Processed Weather object.
   * @throws JsonReadingException if there is an error reading the JSON response.
   */
  public Weather processApiUrl(String apiUrl) throws JsonReadingException {
    RestTemplate restTemplate = new RestTemplate();

    String jsonString = restTemplate.getForObject(apiUrl, String.class);

    JsonNode jsonNode = null;
    try {
      jsonNode = objectMapper.readTree(jsonString);
    } catch (JsonProcessingException e) {
      throw new JsonReadingException("json reading error");
    }
    JsonNode dataNode = jsonNode.get("data").get(0);

    return new Weather(dataNode);
  }

  /**
   * Retrieves a list of Weather objects by the specified country code.
   *
   * @param countryCode Country code.
   * @return List of Weather objects.
   */
  public List<Weather> getWeatherByCountryCode(String countryCode) {
    return weatherRepository.findByCountryCode(countryCode);
  }

  /**
   * Updates a Weather object with the specified ID.
   *
   * @param id            ID of the Weather object to be updated.
   * @param updatedWeather Updated Weather object.
   * @return Updated WeatherDto object.
   */
  public WeatherDto complete(Long id, Weather updatedWeather) {
    Weather weather = weatherRepository.findById(id).get();

    weather.setCountryCode(updatedWeather.getCountryCode());
    weather.setTemp(updatedWeather.getTemp());
    weather.setRh(updatedWeather.getRh());
    weather.setDateTime(updatedWeather.getDateTime());
    weather.setDescription(updatedWeather.getDescription());
    weather.setCityName(updatedWeather.getCityName());
    weather.setUserList(updatedWeather.getUserList());

    return WeatherDto.toModel(weatherRepository.save(weather));
  }

  public Weather createWeather(Weather weather, String cityName) throws WeatherNotFoundException, CityNotFoundException {
    if (weather == null) {
      throw new WeatherNotFoundException("weather not found");
    }
    City city = cityRepo.findByName(cityName);
    if(city != null) {
      weather.setCity(city);
      return weatherRepository.save(weather);
    } else {
      throw new CityNotFoundException("city not found");
    }
  }

  public void createWeatherBulk(List<Weather> weatherList, String cityName) throws Exception {
    if (weatherList == null || weatherList.isEmpty()) {
      throw new WeatherNotFoundException("weather not found");
    }

    List<String> errors = weatherList.stream()
            .map(weather -> {
              try {
                createWeather(weather, cityName);
                return null;
              } catch (Exception e) {
                return "Error creating weather: " + e.getMessage();
              }
            })
            .filter(error -> error != null)
            .toList();

    if (!errors.isEmpty()) {
      throw new Exception("Errors occurred during bulk creation:\n" + String.join("\n", errors));
    }
  }

}
