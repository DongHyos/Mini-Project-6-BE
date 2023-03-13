package com.mini.money.exceptrion;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class InputFieldException extends MoneyException {
    protected static final String EMAIL = "email";
    protected static final String PASSWORD = "password";
    protected static final String NAME = "name";
    protected static final String PHONE = "phone";
    protected static final String LOAN = "loan";

    private final String field;


    public InputFieldException(final String message, final HttpStatus status, final String field) {
        super(message, status);
        this.field = field;
    }
}