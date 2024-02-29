package com.portfolio.goodjobs.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public interface TimeConverter {

    default LocalDateTime instantToLocalDateTime(Instant instant, ZoneId zoneId) {

        return LocalDateTime.ofInstant(instant, zoneId);
    }

    default Instant localDateTimeToInstant(LocalDateTime localDateTime, ZoneId zoneId) {

        return localDateTime.atZone(zoneId).toInstant();
    }
}
