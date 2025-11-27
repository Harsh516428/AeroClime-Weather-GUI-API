package com.aeroclime.ingestion;

import com.aeroclime.exceptions.DataIngestionException;
import com.aeroclime.model.WeatherRecord;

public interface WeatherProvider {
    WeatherRecord fetch(String location) throws DataIngestionException;
    String providerName();
}
