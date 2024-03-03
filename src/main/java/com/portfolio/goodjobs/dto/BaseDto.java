package com.portfolio.goodjobs.dto;

import java.time.Instant;
import java.util.List;

public interface BaseDto {
    void setDeadline(Instant deadline);

    void setRegDate(Instant regDate);

    void setSigunguNames(List<String> locationList);
}
