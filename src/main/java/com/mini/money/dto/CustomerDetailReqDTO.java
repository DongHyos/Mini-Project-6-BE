package com.mini.money.dto;

import com.mini.money.entity.Customer;
import com.mini.money.entity.CustomerDetail;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class CustomerDetailReqDTO {
    private Integer age;
    private String address;
    private String job;
    private String bank;
    private Double crdtGrade;
    private Integer income;

    public CustomerDetail toEntity(Customer customer) {
        return CustomerDetail.builder()
                .customer(customer)
                .age(this.age)
                .address(this.address)
                .job(this.job)
                .bank(this.bank)
                .crdtGrade(this.crdtGrade)
                .income(this.income)
                .build();
    }

}
