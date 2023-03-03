package com.mini.money.exceptrion;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class MoneyException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "일시적으로 접속이 원활하지 않습니다.";
    private final HttpStatus status;

    public MoneyException() {
        super(DEFAULT_MESSAGE);
        this.status = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public MoneyException(final String message, final HttpStatus status) {
        super(message);
        this.status = status;
    }

    public MoneyException(final String message, final Throwable cause, final HttpStatus status) {
        super(message, cause);
        this.status = status;
    }
}
