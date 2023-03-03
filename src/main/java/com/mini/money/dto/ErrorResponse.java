package com.mini.money.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.ConstraintViolationException;

import static com.mini.money.dto.ValidatorMessage.FORMAT_MESSAGE;

@Getter
@NoArgsConstructor
public class ErrorResponse {
    private String message;

    protected ErrorResponse(final String message) {
        this.message = message;
    }

    public static ErrorResponse from(final RuntimeException exception) {
        return new ErrorResponse(exception.getMessage());
    }

    public static ErrorResponse from(final ConstraintViolationException exception) {
        String message = exception.getConstraintViolations().iterator().next().getMessage();
        return new ErrorResponse(message);
    }

    public static ErrorResponse invalidFormat() {
        return new ErrorResponse(FORMAT_MESSAGE);
    }
}
