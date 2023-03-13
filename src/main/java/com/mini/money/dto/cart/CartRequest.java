package com.mini.money.dto.cart;

import com.mini.money.entity.Cart;
import com.mini.money.entity.Customer;
import com.mini.money.entity.Loan;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartRequest {

    private String email;

    private Long snq;

    public Cart toEntity(Customer customer, Loan loan) {
        return Cart.builder()
                .customer(customer)
                .loan(loan)
                .build();
    }

}
