package com.mini.money.exceptrion.cart;

import com.mini.money.exceptrion.InputFieldException;
import org.springframework.http.HttpStatus;

public class NoSuchLoanByCart extends InputFieldException {
    private final static String MESSAGE = "해당 대출 상품은 장바구니에 존재 하지 않습니다.";

    public NoSuchLoanByCart() {
        super(MESSAGE, HttpStatus.BAD_REQUEST, LOAN);
    }
}
