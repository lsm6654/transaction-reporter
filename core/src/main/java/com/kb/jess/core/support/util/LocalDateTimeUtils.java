package com.kb.jess.core.support.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadLocalRandom;

public class LocalDateTimeUtils {
    private static final DateTimeFormatter defaultDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss");
    private static final DateTimeFormatter defaultDateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static LocalDate getRandomLocalDate(final long minEpoch, final long maxEpoch) {
        long randomDay = ThreadLocalRandom.current().nextLong(minEpoch, maxEpoch);
        return LocalDate.ofEpochDay(randomDay);
    }

    public static String getRandomDateString(final long minEpoch, final long maxEpoch, final DateTimeFormatter formatter) {
        return getRandomLocalDate(minEpoch, maxEpoch).format(formatter);
    }

    public static String getRandomDateString(final long minEpoch, final long maxEpoch) {
        return getRandomDateString(minEpoch, maxEpoch, defaultDateFormatter);
    }

    public static String nowString() {
        return nowString(defaultDateTimeFormatter);
    }

    public static String nowString(final DateTimeFormatter formatter) {
        return LocalDateTime.now().format(formatter);
    }
}
