package com.company.common.exception.annotation;

import java.lang.annotation.*;
import org.springframework.context.annotation.Import;
import com.company.common.exception.config.GlobalExceptionAutoConfiguration;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(GlobalExceptionAutoConfiguration.class)
public @interface EnableGlobalExceptionHandler {}
