package com.aeroclime.util;

import java.util.List;

public class TimeSeriesUtils {

    public static <T extends Number> double average(List<T> values) {
        if (values == null || values.isEmpty()) return 0.0;
        double sum = 0.0;
        for (T v : values) sum += v.doubleValue();
        return sum / values.size();
    }

    public static <T extends Number> double min(List<T> values) {
        if (values == null || values.isEmpty()) return 0.0;
        double min = Double.MAX_VALUE;
        for (T v : values) min = Math.min(min, v.doubleValue());
        return min;
    }

    public static <T extends Number> double max(List<T> values) {
        if (values == null || values.isEmpty()) return 0.0;
        double max = -Double.MAX_VALUE;
        for (T v : values) max = Math.max(max, v.doubleValue());
        return max;
    }
}
