package com.mini.money.controller;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.mini.money.dto.ErrorResponse;
import com.mini.money.dto.InputFieldErrorResponse;
import com.mini.money.exceptrion.InputFieldException;
import com.mini.money.exceptrion.MoneyException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

@Slf4j
@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(InputFieldException.class)
    public ResponseEntity<InputFieldErrorResponse> inputFieldExceptionHandler(final InputFieldException exception) {
        return ResponseEntity
                .status(exception.getStatus())
                .body(InputFieldErrorResponse.from(exception));
    }

    @ExceptionHandler(MoneyException.class)
    public ResponseEntity<ErrorResponse> moneyExceptionHandler(final MoneyException exception) {
        return ResponseEntity
                .status(exception.getStatus())
                .body(ErrorResponse.from(exception));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<InputFieldErrorResponse> invalidArgumentHandler(final MethodArgumentNotValidException exception) {
        return ResponseEntity
                .badRequest()
                .body(InputFieldErrorResponse.from(exception));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> invalidParamHandler(final ConstraintViolationException exception) {
        return ResponseEntity
                .badRequest()
                .body(ErrorResponse.from(exception));
    }

    @ExceptionHandler({InvalidFormatException.class, HttpMessageNotReadableException.class})
    public ResponseEntity<ErrorResponse> invalidFormatHandler() {
        return ResponseEntity
                .badRequest()
                .body(ErrorResponse.invalidFormat());
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<Void> invalidDataAccessHandler(final DataAccessException exception) {
        return ResponseEntity
                .internalServerError()
                .build();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Void> unhandledExceptionHandler(final Exception exception) {
        return ResponseEntity
                .internalServerError()
                .build();
    }
}
