package com.company.common.exception.util;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.slf4j.MDC;
import java.io.IOException;
import java.util.UUID;

public class TraceIdFilter implements Filter {

    public static final String MDC_TRACE_ID = "traceId";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        String traceId = UUID.randomUUID().toString();
        MDC.put(MDC_TRACE_ID, traceId);

        try {
            chain.doFilter(request, response);
        } finally {
            MDC.remove(MDC_TRACE_ID);
        }
    }
}
