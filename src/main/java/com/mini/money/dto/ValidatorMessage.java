package com.mini.money.dto;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ValidatorMessage {

    public static final String EMPTY_MESSAGE = "비어 있는 항목을 입력 해주세요.";

    public static final String EMAIL_MESSAGE = "올바른 이메일 형식이 아닙니다.";

    public static final String MEMBER_PW_MESSAGE = "비밀번호는 영어와 숫자를 포함해서 8자 이상 16자 이내로 입력해주세요.";
    public static final String MEMBER_PW_FORMAT = "^(?=.*[a-zA-Z])(?=.*[0-9]).{8,16}$";

    public static final String SERVER_ERROR_MESSAGE = "예상치 못한 문제가 발생했습니다. 개발자에게 문의하세요.";
    public static final String FORMAT_MESSAGE = "날짜 및 시간 데이터 형식이 올바르지 않습니다.";
}
