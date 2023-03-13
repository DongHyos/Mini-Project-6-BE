package com.mini.money.dto.mypage;

import com.mini.money.entity.Customer;
import io.swagger.annotations.ApiModel;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@ApiModel(value = "회원 추가 정보 출력")
public class CustomerDetailDataResponse {
    private Integer age;
    private String address;
    private String job;
    private String bank;
    private Double crdtGrade;
    private String income;

    public static CustomerDetailDataResponse from(final Customer customer) {
        return new CustomerDetailDataResponse(
                customer.getAge(),
                customer.getAddress(),
                customer.getJob(),
                customer.getBank(),
                customer.getCrdtGrade(),
                customer.getIncome()
        );
    }
}
