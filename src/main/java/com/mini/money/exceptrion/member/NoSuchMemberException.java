package com.mini.money.exceptrion.member;

import com.mini.money.exceptrion.InputFieldException;
import org.springframework.http.HttpStatus;

public class NoSuchMemberException extends InputFieldException {

    private static final String MESSAGE = "존재하지 않는 회원입니다.";

    public NoSuchMemberException() {
        super(MESSAGE, HttpStatus.BAD_REQUEST, EMAIL);
    }
}
