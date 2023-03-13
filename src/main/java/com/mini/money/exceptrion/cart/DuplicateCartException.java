package com.mini.money.exceptrion.cart;

import com.mini.money.exceptrion.InputFieldException;
import org.springframework.http.HttpStatus;

public class DuplicateCartException extends InputFieldException {
    private final static String MESSAGE = "이미 장바구니에 추가 된 상품입니다.";

    public DuplicateCartException() {
        super(MESSAGE, HttpStatus.BAD_REQUEST, LOAN);
    }
}
