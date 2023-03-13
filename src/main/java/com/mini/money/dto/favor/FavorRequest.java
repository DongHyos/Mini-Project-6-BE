package com.mini.money.dto.favor;

import com.mini.money.entity.Customer;
import com.mini.money.entity.Favor;
import com.mini.money.entity.Loan;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FavorRequest {

    private String email;

    private Long snq;

    public Favor toEntity(Customer customer, Loan loan){
        return Favor.builder()
                .customer(customer)
                .loan(loan)
                .build();
    }


}
