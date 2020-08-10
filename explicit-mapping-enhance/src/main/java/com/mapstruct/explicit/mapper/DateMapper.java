package com.mapstruct.explicit.mapper;

import com.mapstruct.explicit.config.ApplicationProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
public class DateMapper {
    private final ApplicationProperties applicationProperties;

    public LocalDateTime stringToLocalDateTime(String dateTime) {
        return LocalDateTime.parse(dateTime, DateTimeFormatter
                .ofPattern(this.applicationProperties.getDataTimeFormat()));
    }

    public String localDateTimeToString(LocalDateTime dateTime) {
        return dateTime.format(DateTimeFormatter
                .ofPattern(this.applicationProperties.getDataTimeFormat()));
    }
}
