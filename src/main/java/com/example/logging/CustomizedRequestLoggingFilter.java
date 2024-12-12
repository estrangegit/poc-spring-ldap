package com.example.logging;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.filter.AbstractRequestLoggingFilter;

public class CustomizedRequestLoggingFilter extends AbstractRequestLoggingFilter {

    @Override
    protected void beforeRequest(HttpServletRequest httpServletRequest, String message) {
        if(httpServletRequest.getRequestURI().startsWith("/api")) {
            this.logger.info(message);            
        }
    }
    @Override
    protected void afterRequest(HttpServletRequest httpServletRequest, String message) {}
}
