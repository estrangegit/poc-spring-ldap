package com.example.logging;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomizedRequestLoggingFilterConfig {

    @Bean
    public CustomizedRequestLoggingFilter logFilter() {
        CustomizedRequestLoggingFilter filter = new CustomizedRequestLoggingFilter();
        filter.setIncludeQueryString(true);
        filter.setIncludeClientInfo(false);
        filter.setIncludePayload(false);
        filter.setIncludeHeaders(false);
        return filter;
    }
}
