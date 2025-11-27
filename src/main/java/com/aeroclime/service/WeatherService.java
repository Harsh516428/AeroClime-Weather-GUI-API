package com.aeroclime.service;

import com.aeroclime.dao.WeatherRecordDAOJdbcImpl;
import com.aeroclime.exceptions.DataIngestionException;
import com.aeroclime.ingestion.WeatherProvider;
import com.aeroclime.model.WeatherRecord;
import com.aeroclime.util.TimeSeriesUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class WeatherService {

    private final WeatherProvider provider; // injected provider (polymorphism)
    private final WeatherRecordDAOJdbcImpl dao;

    @Autowired
    public WeatherService(WeatherProvider provider, WeatherRecordDAOJdbcImpl dao) {
        this.provider = provider;
        this.dao = dao;
    }

    // fetch current weather, save, and return record
    public WeatherRecord fetchAndSave(String city) throws DataIngestionException {
        try {
            WeatherRecord rec = provider.fetch(city);
            // persist using JDBC DAO
            dao.save(rec);
            return rec;
        } catch (DataIngestionException ex) {
            throw ex;
        } catch (SQLException ex) {
            throw new DataIngestionException("Database error: " + ex.getMessage(), ex);
        }
    }

    // get recent N readings and compute simple stats (demonstrates Collections & generics)
    public Map<String, Double> computeSimpleStats(String city, int lastN) throws SQLException {
        List<WeatherRecord> recs = dao.findLatestByCity(city, lastN);
        List<Double> temps = new ArrayList<>();
        for (WeatherRecord r : recs) temps.add(r.getTemp());
        Map<String, Double> stats = new HashMap<>();
        stats.put("avg", TimeSeriesUtils.average(temps));
        stats.put("min", TimeSeriesUtils.min(temps));
        stats.put("max", TimeSeriesUtils.max(temps));
        return stats;
    }
}
