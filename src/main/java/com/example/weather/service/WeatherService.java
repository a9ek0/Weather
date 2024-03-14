package com.example.weather.service;

import com.example.weather.dto.WeatherDto;
import com.example.weather.entity.City;
import com.example.weather.entity.Weather;
import com.example.weather.exception.BulkCreationException;
import com.example.weather.exception.CityNotFoundException;
import com.example.weather.exception.IdNotFoundException;
import com.example.weather.exception.JsonReadingException;
import com.example.weather.exception.WeatherNotFoundException;
import com.example.weather.repository.CityRepo;
import com.example.weather.repository.WeatherRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * Service class for handling Weather-related operations.
 */
@Service
public class WeatherService {

  private final ObjectMapper objectMapper;
  private static final String CITY_NOT_FOUND = "City not found";
  final CityRepo cityRepo;
  private final WeatherRepository weatherRepository;

  /**
   * Constructor for the WeatherService class.
   *
   * @param objectMapper      ObjectMapper for handling JSON.
   * @param weatherRepository Weather repository for data access.
   */
  @Autowired
  public WeatherService(ObjectMapper objectMapper,
                        WeatherRepository weatherRepository,
                        CityRepo cityRepo) {
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
    Optional<Weather> optionalWeather = weatherRepository.findById(id);
    Weather weather = optionalWeather.orElseThrow(() -> new WeatherNotFoundException("Weather not found"));

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
      throw new CityNotFoundException(CITY_NOT_FOUND);
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
      throw new CityNotFoundException(CITY_NOT_FOUND);
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
   * @param id             ID of the Weather object to be updated.
   * @param updatedWeather Updated Weather object.
   * @return Updated WeatherDto object.
   */
  public WeatherDto complete(Long id, Weather updatedWeather) throws WeatherNotFoundException {
    Optional<Weather> optionalWeather = weatherRepository.findById(id);
    Weather weather = optionalWeather.orElseThrow(() -> new WeatherNotFoundException("Weather not found"));


    if (updatedWeather.getCountryCode() != null) {
      weather.setCountryCode(updatedWeather.getCountryCode());
    }
    weather.setTemp(updatedWeather.getTemp());
    weather.setRh(updatedWeather.getRh());
    if (updatedWeather.getDateTime() != null) {
      weather.setDateTime(updatedWeather.getDateTime());
    }
    if (updatedWeather.getDescription() != null) {
      weather.setDescription(updatedWeather.getDescription());
    }
    if (updatedWeather.getCityName() != null) {
      weather.setCityName(updatedWeather.getCityName());
    }
    if (updatedWeather.getUserList() != null) {
      weather.setUserList(updatedWeather.getUserList());
    }

    return WeatherDto.toModel(weatherRepository.save(weather));
  }

  /**
   * Creates a new weather record for the specified city.
   *
   * @param weather  The Weather object to be created.
   * @param cityName The name of the city for which the weather record is created.
   * @return The newly created Weather object.
   * @throws WeatherNotFoundException If the provided Weather object is null.
   * @throws CityNotFoundException    If the specified city does not exist in the database.
   */
  public Weather createWeather(Weather weather, String cityName) throws WeatherNotFoundException,
          CityNotFoundException {
    if (weather == null) {
      throw new WeatherNotFoundException("weather not found");
    }
    City city = cityRepo.findByName(cityName);
    if (city != null) {
      weather.setCity(city);
      return weatherRepository.save(weather);
    } else {
      throw new CityNotFoundException(CITY_NOT_FOUND);
    }
  }

  /**
   * Creates multiple weather records in bulk for the specified city.
   *
   * @param weatherList The list of Weather objects to be created in bulk.
   * @param cityName    The name of the city for which the weather records are created.
   * @throws BulkCreationException If any errors occur
   *                               during the bulk creation process.
   *                               This could include WeatherNotFoundException
   *                               or CityNotFoundException for individual weather records.
   */
  public void createWeatherBulk(List<Weather> weatherList, String cityName) throws BulkCreationException, WeatherNotFoundException {
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
            .filter(Objects::nonNull)
            .toList();

    if (!errors.isEmpty()) {
      throw new BulkCreationException("Errors occurred during bulk creation:\n" + String.join("\n", errors));
    }
  }

}
