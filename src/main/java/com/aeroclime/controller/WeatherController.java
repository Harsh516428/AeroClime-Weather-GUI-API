package com.aeroclime.controller;

import com.aeroclime.exceptions.DataIngestionException;
import com.aeroclime.model.WeatherRecord;
import com.aeroclime.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    // trigger a fetch + save manually
    @PostMapping("/weather/{city}/fetch")
    public WeatherRecord fetchNow(@PathVariable String city) throws DataIngestionException {
        return weatherService.fetchAndSave(city);
    }

    // stats for last N records
    @GetMapping("/weather/{city}/stats")
    public Map<String, Double> stats(@PathVariable String city, @RequestParam(defaultValue = "10") int lastN) throws SQLException {
        return weatherService.computeSimpleStats(city, lastN);
    }

    // health check
    @GetMapping("/health")
    public String health() {
        return "OK - " + LocalDateTime.now();
    }
}
