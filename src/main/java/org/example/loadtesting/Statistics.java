package org.example.loadtesting;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class Statistics {
    private final Long totalTime;
    private final Long medianTime;
    private final Long percentile95;
    private final Long percentile99;
    public Statistics(List<Long> times) {
        this.totalTime = countTotalTime(times);
        this.medianTime = countPercentile(times, 50f);
        this.percentile95 = countPercentile(times, 95f);
        this.percentile99 = countPercentile(times, 99f);
    }

    private long countTotalTime(List<Long> times) {
        return times.stream().mapToLong(f -> f).sum();
    }

    private Long countPercentile(List<Long> times, float percentile) {
        if (times.isEmpty()) {
            return null;
        }
        int index = (int) Math.ceil(percentile/100 * times.size()) - 1;
        return times.get(index);
    }

    @Override
    public String toString() {
        return "totalTime: %d s, medianTime: %d mc, percentile95: %d mc, percentile99: %d mc"
                .formatted(
                        TimeUnit.NANOSECONDS.toSeconds(this.totalTime),
                        TimeUnit.NANOSECONDS.toMicros(this.medianTime),
                        TimeUnit.NANOSECONDS.toMicros(this.percentile95),
                        TimeUnit.NANOSECONDS.toMicros(this.percentile99));
    }
}
