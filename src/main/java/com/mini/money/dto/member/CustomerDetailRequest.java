package com.mini.money.dto.member;

import com.mini.money.entity.Customer;
import io.swagger.annotations.ApiModel;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "회원 추가 정보 입력")
public class CustomerDetailRequest {
    private Integer age;
    private String address;
    private String job;
    private String bank;
    private Double crdtGrade;
    private String income;

    public Customer toEntity(final Customer customer) {
        return Customer.builder()
                .email(customer.getEmail())
                .name(customer.getName())
                .phone(customer.getPhone())
                .password(customer.getPassword())
                .role(customer.getRole())
                .age(this.age)
                .address(this.address)
                .job(this.job)
                .bank(this.bank)
                .crdtGrade(this.crdtGrade)
                .income(this.income)
                .build();
    }
}
