package com.mini.money.dto.mypage;

import com.mini.money.entity.Customer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

import static com.mini.money.dto.ValidatorMessage.EMPTY_MESSAGE;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CheckPasswordResponse {

    @NotBlank(message = EMPTY_MESSAGE)
    private String name;

    public static CheckPasswordResponse from(final Customer customer) {
        return new CheckPasswordResponse(
                customer.getName()
        );
    }
}
