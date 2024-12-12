package com.example.app.poc.service.implementation;

import com.example.app.poc.service.IDateUtilitarianService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class DateUtilitarianService implements IDateUtilitarianService {

    public static final String STANDARD_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    
    private static final DateTimeFormatter STANDARD_DATE_FORMATTER = DateTimeFormatter.ofPattern(STANDARD_DATE_TIME_FORMAT);

    public String formatStandard(LocalDateTime date) {
        return STANDARD_DATE_FORMATTER.format(date);
    }
}
