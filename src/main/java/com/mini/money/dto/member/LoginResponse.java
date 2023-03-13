package com.mini.money.dto.member;


import com.mini.money.entity.Customer;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@ApiModel(value = "로그인 후 토큰 출력")
public class LoginResponse {
    private String email;
    private String name;
    private String token;

    public static LoginResponse from(final Customer customer, final String token) {
        return new LoginResponse(
                customer.getEmail(),
                customer.getName(),
                token
        );
    }

}
