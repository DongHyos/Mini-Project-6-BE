package com.mini.money.exceptrion.favor;

import com.mini.money.exceptrion.InputFieldException;
import org.springframework.http.HttpStatus;

public class NoSuchLoanByFavor extends InputFieldException {
    private final static String MESSAGE = "해당 대출 상품은 찜 목록에 존재 하지 않습니다.";

    public NoSuchLoanByFavor() {
        super(MESSAGE, HttpStatus.BAD_REQUEST, LOAN);
    }
}
