package com.aeroclime.dao;

import com.aeroclime.model.WeatherRecord;
import com.aeroclime.util.JdbcUtil;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class WeatherRecordDAOJdbcImpl {

    // insert record
    public void save(WeatherRecord record) throws SQLException {
        String sql = "INSERT INTO weather_record(city, temp, humidity, wind_speed, provider, timestamp) VALUES (?,?,?,?,?,?)";
        try (Connection conn = JdbcUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, record.getCity());
            ps.setDouble(2, record.getTemp());
            ps.setDouble(3, record.getHumidity());
            ps.setDouble(4, record.getWindSpeed());
            ps.setString(5, record.getProvider());
            ps.setTimestamp(6, Timestamp.valueOf(record.getTimestamp()));
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    record.setId(rs.getLong(1));
                }
            }
        }
    }

    // simple query by city and date range
    public List<WeatherRecord> findByCityAndRange(String city, LocalDateTime from, LocalDateTime to) throws SQLException {
        String sql = "SELECT id, city, temp, humidity, wind_speed, provider, timestamp FROM weather_record WHERE city = ? AND timestamp BETWEEN ? AND ? ORDER BY timestamp";
        List<WeatherRecord> out = new ArrayList<>();
        try (Connection conn = JdbcUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, city);
            ps.setTimestamp(2, Timestamp.valueOf(from));
            ps.setTimestamp(3, Timestamp.valueOf(to));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    WeatherRecord r = new WeatherRecord();
                    r.setId(rs.getLong("id"));
                    r.setCity(rs.getString("city"));
                    r.setTemp(rs.getDouble("temp"));
                    r.setHumidity(rs.getDouble("humidity"));
                    r.setWindSpeed(rs.getDouble("wind_speed"));
                    r.setProvider(rs.getString("provider"));
                    r.setTimestamp(rs.getTimestamp("timestamp").toLocalDateTime());
                    out.add(r);
                }
            }
        }
        return out;
    }

    // get last N records for a city
    public List<WeatherRecord> findLatestByCity(String city, int limit) throws SQLException {
        String sql = "SELECT id, city, temp, humidity, wind_speed, provider, timestamp FROM weather_record WHERE city = ? ORDER BY timestamp DESC LIMIT ?";
        List<WeatherRecord> out = new ArrayList<>();
        try (Connection conn = JdbcUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, city);
            ps.setInt(2, limit);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    WeatherRecord r = new WeatherRecord();
                    r.setId(rs.getLong("id"));
                    r.setCity(rs.getString("city"));
                    r.setTemp(rs.getDouble("temp"));
                    r.setHumidity(rs.getDouble("humidity"));
                    r.setWindSpeed(rs.getDouble("wind_speed"));
                    r.setProvider(rs.getString("provider"));
                    r.setTimestamp(rs.getTimestamp("timestamp").toLocalDateTime());
                    out.add(r);
                }
            }
        }
        return out;
    }
}
