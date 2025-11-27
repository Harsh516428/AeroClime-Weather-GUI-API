package com.aeroclime.ingestion;

import com.aeroclime.exceptions.DataIngestionException;
import com.aeroclime.model.WeatherRecord;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Component
public class OpenWeatherProvider implements WeatherProvider {

    @Value("${openweathermap.api.key:}")
    private String apiKey;

    private final String baseUrl = "https://api.openweathermap.org/data/2.5/weather";

    @Override
    public WeatherRecord fetch(String location) throws DataIngestionException {
        try {
            if (apiKey == null || apiKey.isBlank()) {
                throw new DataIngestionException("OpenWeather API key not configured");
            }
            String urlStr = baseUrl + "?q=" + location + "&appid=" + apiKey + "&units=metric";
            URL url = new URL(urlStr);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            int code = con.getResponseCode();
            if (code != 200) {
                BufferedReader err = new BufferedReader(new InputStreamReader(con.getErrorStream()));
                String errMsg = err.lines().collect(Collectors.joining());
                throw new DataIngestionException("Failed to fetch: " + code + " " + errMsg);
            }
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String response = in.lines().collect(Collectors.joining());
            in.close();

            JSONObject json = new JSONObject(response);
            double temp = json.getJSONObject("main").getDouble("temp");
            double humidity = json.getJSONObject("main").getDouble("humidity");
            double wind = json.getJSONObject("wind").has("speed") ? json.getJSONObject("wind").getDouble("speed") : 0.0;

            WeatherRecord rec = new WeatherRecord(location, temp, humidity, wind, providerName(), LocalDateTime.now());
            return rec;
        } catch (DataIngestionException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new DataIngestionException("Error calling OpenWeather: " + ex.getMessage(), ex);
        }
    }

    @Override
    public String providerName() {
        return "OpenWeather";
    }
}
