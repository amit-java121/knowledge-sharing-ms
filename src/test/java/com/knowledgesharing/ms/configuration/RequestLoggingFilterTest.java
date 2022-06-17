package com.knowledgesharing.ms.configuration;

import com.knowledgesharing.ms.configuration.filter.RequestLoggingFilter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import java.io.IOException;

import static org.mockito.Mockito.*;

public class RequestLoggingFilterTest {

    MockHttpServletRequest request = new MockHttpServletRequest("GET", "/foobar");
    MockHttpServletResponse response = new MockHttpServletResponse();
    FilterChain chain = mock(FilterChain.class);
    Logger logger = mock(Logger.class);

    private RequestLoggingFilter requestLoggingFilter = new RequestLoggingFilter();

    @BeforeEach
    public void setUp() {
        requestLoggingFilter.setLogger(logger);
    }

    @Test
    public void passesTheBucket() throws Exception {
        requestLoggingFilter.doFilter(request, response, chain);

        verify(chain, only()).doFilter(request, response);
    }

    @Test
    public void withMultipleValuedHeaders() throws Exception {
        request.addHeader("foo", "bar");
        request.addHeader("foo", "zork");
        response.setStatus(201);

        requestLoggingFilter.doFilter(request, response, chain);

        verify(logger).info("Before GET /foobar");
        verify(logger).info("201    GET /foobar [foo: bar, zork]");
        verifyNoMoreInteractions(logger);
    }

    @Test
    public void withQueryString() throws Exception {
        request.setQueryString("foo=bar&zot=zork");
        response.setStatus(200);

        requestLoggingFilter.doFilter(request, response, chain);

        verify(logger).info("Before GET /foobar?foo=bar&zot=zork");
        verify(logger).info("200    GET /foobar?foo=bar&zot=zork []");
        verifyNoMoreInteractions(logger);
    }

    @Test
    public void withAuthorizationHeader() throws IOException, ServletException {
        request.addHeader("authorization", "Bearer token_value");
        response.setStatus(201);

        requestLoggingFilter.doFilter(request, response, chain);

        verify(logger).info("Before GET /foobar");
        verify(logger).info("201    GET /foobar [authorization: ...]");
        verifyNoMoreInteractions(logger);
    }
}
