package com.mini.money.controller;

import com.mini.money.dto.InputFieldErrorResponse;
import com.mini.money.exceptrion.InputFieldException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ControllerAdvice {
    private static final String MESSAGE_FORMAT = "{} (traceId: {})";
    private static final String TRACE_ID_KEY = "traceId";

    @ExceptionHandler(InputFieldException.class)
    public ResponseEntity<InputFieldErrorResponse> inputFieldExceptionHandler(final InputFieldException exception) {
        return ResponseEntity
                .status(exception.getStatus())
                .body(InputFieldErrorResponse.from(exception));
    }
}
