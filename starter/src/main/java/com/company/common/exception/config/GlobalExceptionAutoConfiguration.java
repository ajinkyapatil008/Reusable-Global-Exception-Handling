package com.company.common.exception.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.company.common.exception.handler.GlobalExceptionHandler;
import com.company.common.exception.util.TraceIdFilter;

import jakarta.annotation.PostConstruct;

@Configuration
public class GlobalExceptionAutoConfiguration {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionAutoConfiguration.class);

    @PostConstruct
    public void init() {
        log.info("✅ Global Exception Handling Starter: Auto-configuration initialized successfully.");
    }
  
    @Bean
    public GlobalExceptionHandler globalExceptionHandler() {

        log.info("🔧 Registering GlobalExceptionHandler bean");

        return new GlobalExceptionHandler();
    }

    @Bean
    public TraceIdFilter traceIdFilter() {

        log.info("🔧 Registering TraceIdFilter bean (traceId will be added to MDC)");
        
        return new TraceIdFilter();
    }
}
