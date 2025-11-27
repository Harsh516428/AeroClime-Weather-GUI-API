CREATE TABLE IF NOT EXISTS weather_record (
  id SERIAL PRIMARY KEY,
  city VARCHAR(128) NOT NULL,
  temp DOUBLE PRECISION,
  humidity DOUBLE PRECISION,
  wind_speed DOUBLE PRECISION,
  provider VARCHAR(64),
  timestamp TIMESTAMP NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_weather_ts ON weather_record(timestamp);
