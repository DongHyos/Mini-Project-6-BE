package com.mini.money.exceptrion.favor;

import com.mini.money.exceptrion.InputFieldException;
import org.springframework.http.HttpStatus;

public class DuplicateFavorException extends InputFieldException {
    private final static String MESSAGE = "이미 찜 목록에 추가된 대출 상품입니다.";

    public DuplicateFavorException() {
        super(MESSAGE, HttpStatus.BAD_REQUEST, LOAN);
    }
}
