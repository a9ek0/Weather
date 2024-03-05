package com.example.weather.service;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class DateTimeService {

    private DateTimeService() {
        // Private constructor to hide the implicit public one
    }
    
    public static LocalDateTime toDateTime(String dateString) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return LocalDateTime.parse(dateString, formatter);
    }

    public static String toString(LocalDateTime dateTime) {
        String date;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        date = dateTime.format(formatter);

        return date;
    }
}
