package com.knowledgesharing.ms.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class ApplicationControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException ex) {
        if (log.isErrorEnabled()) {
            log.error("Constraint Validation Exception: " + ex.getMessage(), ex);
        }
        String message = ex.getConstraintViolations().stream().filter(Objects::nonNull).map(ConstraintViolation::getMessage).collect(Collectors.joining(", "));
        return new ResponseEntity<>(new ErrorResponse("400", message), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<ErrorResponse> fourHundredException(NotFoundException ex) {
        if (log.isErrorEnabled()) {
            log.error("Not found Exception: " + ex.getMessage(), ex);
        }
        ErrorResponse errorResponse = new ErrorResponse("404", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.EXPECTATION_FAILED);
    }

    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        if (log.isErrorEnabled()) {
            log.error("Method Argument Not Valid Exception: " + ex.getMessage(), ex);
        }
        String errorMessage = this.formatError(ex.getBindingResult().getAllErrors());
        return new ResponseEntity(new ErrorResponse("400", errorMessage), HttpStatus.BAD_REQUEST);
    }

    private String formatError(List<ObjectError> allErrors) {
        return allErrors.stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(","));
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    class ErrorResponse {
        private String code;
        private String description;
    }

}
