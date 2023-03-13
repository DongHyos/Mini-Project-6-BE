package com.mini.money.service;

import com.mini.money.dto.cart.CartRequest;
import com.mini.money.dto.loan.LoanResponse;
import com.mini.money.dto.member.LoginRequest;
import com.mini.money.entity.Customer;

import java.util.List;


public interface CartService {

    void addCart(final CartRequest cartRequest);

    void deleteProduct(final CartRequest cartRequest);

    List<LoanResponse> selectCartList(final LoginRequest loginRequest);

    void deleteLastCart(final Customer customer);
}
