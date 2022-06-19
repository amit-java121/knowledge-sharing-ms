package com.knowledgesharing.ms.exception;

import org.hibernate.validator.internal.engine.ConstraintViolationImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.validation.ConstraintViolationException;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
public class ApplicationExceptionAdviceTest {
    @InjectMocks
    ApplicationControllerAdvice applicationControllerAdvice;

    @Test
    void shouldHandleNotFoundException() {
        NotFoundException exception = new NotFoundException("not found");
        ResponseEntity<ErrorResponse> errorResponse = applicationControllerAdvice.fourHundredException(exception);
        ErrorResponse responseBody = errorResponse.getBody();
        assertNotNull(responseBody);
        assertThat(responseBody.getCode()).isEqualTo("404");
        assertThat(responseBody.getDescription()).isEqualTo("not found");
    }

    @Test
    void shouldAccessDeniedException() {
        AccessDeniedException exception = new AccessDeniedException("access denied");
        ResponseEntity<ErrorResponse> errorResponse = applicationControllerAdvice.accessDeniedException(exception);
        ErrorResponse responseBody = errorResponse.getBody();
        assertNotNull(responseBody);
        assertThat(responseBody.getCode()).isEqualTo("403");
        assertThat(responseBody.getDescription()).isEqualTo("access denied");
    }

    @Test
    void shouldHandleConstraintViolationException() {
        ConstraintViolationException exception = new ConstraintViolationException(
                "",
                Set.of(ConstraintViolationImpl.forParameterValidation(
                        null,
                        null,
                        null,
                        "Message",
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null)
                )
        );

        ResponseEntity<ErrorResponse> errorResponse = applicationControllerAdvice.handleConstraintViolationException(exception);
        assertEquals(HttpStatus.BAD_REQUEST, errorResponse.getStatusCode());
        assertNotNull(errorResponse.getBody());
        assertEquals("400", errorResponse.getBody().getCode());
        assertEquals("Message", errorResponse.getBody().getDescription());
    }
}
