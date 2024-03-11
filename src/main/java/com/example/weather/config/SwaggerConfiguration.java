package com.example.weather.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * Configuration class for Swagger documentation.
 */
@Configuration
public class SwaggerConfiguration {

  /**
   * Configures and returns a Docket bean for Swagger documentation.
   *
   * @return Docket bean for Swagger documentation.
   */
  @Bean
  public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2)
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.example.weather.controller"))
            .paths(PathSelectors.any())
            .build();
  }
}
