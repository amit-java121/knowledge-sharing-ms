package com.knowledgesharing.ms.configuration.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

public class RequestLoggingFilter implements Filter {
    private static final List<String> MUTED_HEADER_NAMES = Arrays.asList("authorization");
    private static Logger log = LoggerFactory.getLogger(RequestLoggingFilter.class);

    public RequestLoggingFilter() {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            this.logStartRequest((HttpServletRequest) request);
            chain.doFilter(request, response);
        } finally {
            this.logRequestAndResponse((HttpServletRequest) request, (HttpServletResponse) response);
        }

    }

    public static void setLogger(Logger passedLogger) {
        log = passedLogger;
    }

    public void init(FilterConfig filterConfig) throws ServletException {
    }

    public void destroy() {
    }

    private void logStartRequest(HttpServletRequest request) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("Before ");
        this.addRequestDescription(buffer, request);
        log.info(buffer.toString());
    }

    private void logRequestAndResponse(HttpServletRequest request, HttpServletResponse response) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(response.getStatus());
        buffer.append("    ");
        this.addRequestDescription(buffer, request);
        buffer.append(" [");
        this.addHeaders(buffer, request);
        buffer.append("]");
        log.info(buffer.toString());
    }

    private void addRequestDescription(StringBuffer buffer, HttpServletRequest request) {
        buffer.append(request.getMethod());
        buffer.append(" ");
        buffer.append(request.getRequestURI());
        if (null != request.getQueryString()) {
            buffer.append("?");
            buffer.append(request.getQueryString());
        }

    }

    private void addHeaders(StringBuffer headers, HttpServletRequest request) {
        int count = 0;

        for (Enumeration<String> headerNames = request.getHeaderNames(); headerNames.hasMoreElements(); ++count) {
            if (count > 0) {
                headers.append("; ");
            }

            String name = (String) headerNames.nextElement();
            headers.append(name);
            headers.append(": ");
            if (this.shouldBeMuted(name)) {
                this.addHeaderValues(headers, Collections.enumeration(Collections.singletonList("...")));
            } else {
                this.addHeaderValues(headers, request.getHeaders(name));
            }
        }

    }

    private boolean shouldBeMuted(String name) {
        return MUTED_HEADER_NAMES.contains(name.toLowerCase(Locale.ENGLISH));
    }

    private void addHeaderValues(StringBuffer buffer, Enumeration<String> headers) {
        for (int count = 0; headers.hasMoreElements(); ++count) {
            if (count > 0) {
                buffer.append(", ");
            }

            buffer.append((String) headers.nextElement());
        }

    }
}
