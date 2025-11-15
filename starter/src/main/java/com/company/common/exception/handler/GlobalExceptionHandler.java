package com.company.common.exception.handler;

import java.time.Instant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.company.common.exception.exception.BadRequestException;
import com.company.common.exception.exception.ConflictException;
import com.company.common.exception.exception.ForbiddenAccessException;
import com.company.common.exception.exception.InvalidRequestException;
import com.company.common.exception.exception.ResourceNotFoundException;
import com.company.common.exception.exception.ServiceUnavailableException;
import com.company.common.exception.exception.TooManyRequestsException;
import com.company.common.exception.model.ErrorResponse;
import com.company.common.exception.util.TraceIdFilter;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadReq(BadRequestException ex, HttpServletRequest req) {
        logWarn(ex, req);
        return build(HttpStatus.BAD_REQUEST, ex, req);
    }

    @ExceptionHandler(ForbiddenAccessException.class)
    public ResponseEntity<ErrorResponse> handleForbidden(ForbiddenAccessException ex, HttpServletRequest req) {
        logWarn(ex, req);
        return build(HttpStatus.FORBIDDEN, ex, req);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ErrorResponse> handleConflict(ConflictException ex, HttpServletRequest req) {
        logWarn(ex, req);
        return build(HttpStatus.CONFLICT, ex, req);
    }

    @ExceptionHandler(TooManyRequestsException.class)
    public ResponseEntity<ErrorResponse> handleTooMany(TooManyRequestsException ex, HttpServletRequest req) {
        logWarn(ex, req);
        return build(HttpStatus.TOO_MANY_REQUESTS, ex, req);
    }

    @ExceptionHandler(ServiceUnavailableException.class)
    public ResponseEntity<ErrorResponse> handleServiceUnavailable(ServiceUnavailableException ex, HttpServletRequest req) {
        logError(ex, req);
        return build(HttpStatus.SERVICE_UNAVAILABLE, ex, req);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> notFound(ResourceNotFoundException ex, HttpServletRequest req){
        logWarn(ex, req);
        return build(HttpStatus.NOT_FOUND, ex, req);
    }

    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<ErrorResponse> bad(InvalidRequestException ex, HttpServletRequest req){
        logWarn(ex, req);
        return build(HttpStatus.BAD_REQUEST, ex, req);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> all(Exception ex, HttpServletRequest req){
        logError(ex, req);
        return build(HttpStatus.INTERNAL_SERVER_ERROR, ex, req);
    }

    private ResponseEntity<ErrorResponse> build(HttpStatus status, Exception ex, HttpServletRequest req){
        String tid = MDC.get(TraceIdFilter.MDC_TRACE_ID);
        ErrorResponse response = new ErrorResponse(
                Instant.now(),
                status.value(),
                status.getReasonPhrase(),
                ex.getMessage(),
                req.getRequestURI(),
                tid
        );

        // Log of error response in a clean, readable format
        if (status.is5xxServerError()) {
            log.error("Error occurred: {}", response.toLogString());
        } else {
            log.warn("Error occurred: {}", response.toLogString());
        }

        return ResponseEntity.status(status).body(
            new ErrorResponse(
                Instant.now(),
                status.value(),
                status.getReasonPhrase(),
                ex.getMessage(),
                req.getRequestURI(),
                tid
            )
        );
    }

    private void logWarn(Exception ex, HttpServletRequest req) {
        log.warn("Handled Exception | type={} | path={} | message={} | traceId={}",
                ex.getClass().getSimpleName(),
                req.getRequestURI(),
                ex.getMessage(),
                MDC.get(TraceIdFilter.MDC_TRACE_ID));
    }

    private void logError(Exception ex, HttpServletRequest req) {
        log.error("Handled Exception | type={} | path={} | message={} | traceId={}",
                ex.getClass().getSimpleName(),
                req.getRequestURI(),
                ex.getMessage(),
                MDC.get(TraceIdFilter.MDC_TRACE_ID));
    }
}
