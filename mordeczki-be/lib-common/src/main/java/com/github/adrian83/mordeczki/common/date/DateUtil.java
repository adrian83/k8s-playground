package com.github.adrian83.mordeczki.common.date;

import java.time.ZoneId;
import java.time.ZonedDateTime;


public final class DateUtil {

    private final static ZoneId UTC_ZONE = ZoneId.of("UTC");

    private DateUtil() {}

    public static ZonedDateTime utcNow() {
        return ZonedDateTime.now(UTC_ZONE);
    }

    public static ZonedDateTime utcNowPlusHours(long hours) {
        return utcNow().plusHours(hours);
    } 

    public static boolean isBefore(ZonedDateTime first, ZonedDateTime second) {
        return first.isBefore(second);
    }

}