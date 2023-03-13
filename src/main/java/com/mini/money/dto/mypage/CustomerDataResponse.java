package com.mini.money.dto.mypage;

import com.mini.money.entity.Customer;
import io.swagger.annotations.ApiModel;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "회원 필수 정보 출력")
public class CustomerDataResponse {

    private String email;
    private String name;
    private String phone;

    public static CustomerDataResponse from(final Customer customer) {
        return new CustomerDataResponse(
                customer.getEmail(),
                customer.getName(),
                customer.getPhone()
        );
    }
}
