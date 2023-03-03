package com.mini.money.exceptrion.member;

import com.mini.money.exceptrion.InputFieldException;
import org.springframework.http.HttpStatus;

public class DuplicateEmailException extends InputFieldException {

    private static final String MESSAGE = "이미 사용 중인 이메일입니다.";

    public DuplicateEmailException() {
        super(MESSAGE, HttpStatus.BAD_REQUEST, EMAIL);
    }
}
