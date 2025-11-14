package com.company.common.exception.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.company.common.exception.handler.GlobalExceptionHandler;
import com.company.common.exception.util.TraceIdFilter;

@Configuration
public class GlobalExceptionAutoConfiguration {

    @Bean
    public GlobalExceptionHandler globalExceptionHandler() {
        return new GlobalExceptionHandler();
    }

    @Bean
    public TraceIdFilter traceIdFilter() {
        return new TraceIdFilter();
    }
}
