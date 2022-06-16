package com.knowledgesharing.ms.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    private String code;
    private String description;
    private String timestamp = Instant.now().toString();

    public ErrorResponse(String code, String description) {
        this.code = code;
        this.description = description;
    }
}
