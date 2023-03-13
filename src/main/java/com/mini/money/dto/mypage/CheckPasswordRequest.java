package com.mini.money.dto.mypage;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import static com.mini.money.dto.ValidatorMessage.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CheckPasswordRequest {

    @NotBlank(message = EMPTY_MESSAGE)
    @Pattern(regexp = MEMBER_PW_FORMAT, message = MEMBER_PW_MESSAGE)
    private String password;
}
