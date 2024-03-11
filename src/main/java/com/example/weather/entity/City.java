package com.example.weather.entity;




import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a city entity with associated weather information.
 */
@Entity
public class City {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  Long id;

  private String name;

  @OneToMany(mappedBy = "city", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST,
                                                                   CascadeType.REMOVE,
                                                                   CascadeType.MERGE})
  private List<Weather> weatherList = new ArrayList<>();

  public City() {
    // No initialization logic needed for this constructor
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<Weather> getWeatherList() {
    return weatherList;
  }

  public void setWeatherList(List<Weather> weatherList) {
    this.weatherList = weatherList;
  }
}
