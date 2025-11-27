package com.aeroclime.scheduler;

import com.aeroclime.service.WeatherService;
import com.aeroclime.model.WeatherRecord;
import com.aeroclime.exceptions.DataIngestionException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.*;

@Component
public class IngestionScheduler {

    private final WeatherService weatherService;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);

    @Value("${ingestion.interval.seconds:600}")
    private int intervalSeconds;

    public IngestionScheduler(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @PostConstruct
    public void start() {
        // example: schedule ingestion for sample cities every intervalSeconds
        Runnable task = () -> {
            List<String> cities = List.of("London", "Delhi", "New York");
            for (String city : cities) {
                try {
                    WeatherRecord r = weatherService.fetchAndSave(city);
                    System.out.println(LocalDateTime.now() + " - Ingested: " + city + " " + r.getTemp() + "°C");
                } catch (DataIngestionException ex) {
                    System.err.println("Ingestion failed for " + city + ": " + ex.getMessage());
                }
            }
        };
        scheduler.scheduleAtFixedRate(task, 5, intervalSeconds, TimeUnit.SECONDS);
    }
}
