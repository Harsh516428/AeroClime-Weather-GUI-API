package com.aeroclime.model;

import java.time.LocalDateTime;

public class WeatherRecord {
    private Long id;
    private String city;
    private double temp;
    private double humidity;
    private double windSpeed;
    private String provider;
    private LocalDateTime timestamp;

    public WeatherRecord() {}

    public WeatherRecord(String city, double temp, double humidity, double windSpeed, String provider, LocalDateTime timestamp) {
        this.city = city;
        this.temp = temp;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
        this.provider = provider;
        this.timestamp = timestamp;
    }

    // getters & setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public double getTemp() { return temp; }
    public void setTemp(double temp) { this.temp = temp; }

    public double getHumidity() { return humidity; }
    public void setHumidity(double humidity) { this.humidity = humidity; }

    public double getWindSpeed() { return windSpeed; }
    public void setWindSpeed(double windSpeed) { this.windSpeed = windSpeed; }

    public String getProvider() { return provider; }
    public void setProvider(String provider) { this.provider = provider; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}
