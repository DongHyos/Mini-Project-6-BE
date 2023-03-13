package com.mini.money.dto.member;

import com.mini.money.entity.Customer;
import io.swagger.annotations.ApiModel;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import static com.mini.money.dto.ValidatorMessage.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@ApiModel(value = "회원 필수 정보 입력")
public class CustomerRequest {

    @NotBlank(message = EMPTY_MESSAGE)
    @Email(message = EMAIL_MESSAGE)
    private String email;

    @NotBlank(message = EMPTY_MESSAGE)
    @Pattern(regexp = MEMBER_PW_FORMAT, message = MEMBER_PW_MESSAGE)
    private String password;

    @NotBlank(message = EMPTY_MESSAGE)
    private String name;

    @NotBlank(message = EMPTY_MESSAGE)
    private String phone;

    public Customer toEntity(){
        return Customer.builder()
                .email(this.email)
                .password(this.password)
                .name(this.name)
                .phone(this.phone)
                .build();
    }

}
