package com.mini.money.dto.mypage;

import io.swagger.annotations.ApiModel;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import static com.mini.money.dto.ValidatorMessage.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "회원 정보 수정")
public class UpdateCustomerDataRequest {
    @NotBlank(message = EMPTY_MESSAGE)
    @Pattern(regexp = MEMBER_PW_FORMAT, message = MEMBER_PW_MESSAGE)
    private String password;

    @NotBlank(message = EMPTY_MESSAGE)
    private String phone;
}
