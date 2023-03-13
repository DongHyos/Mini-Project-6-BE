package com.mini.money.exceptrion.loan;

import com.mini.money.exceptrion.InputFieldException;
import org.springframework.http.HttpStatus;

public class NoSuchLoanException extends InputFieldException {
    private static final String MESSAGE = "존재 하지 않는 대출상품입니다.";

    public NoSuchLoanException() {
        super(MESSAGE, HttpStatus.BAD_REQUEST, LOAN);
    }
}
