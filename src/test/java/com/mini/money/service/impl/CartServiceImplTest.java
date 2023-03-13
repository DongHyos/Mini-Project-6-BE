package com.mini.money.service.impl;

import com.mini.money.dto.loan.LoanResponse;
import com.mini.money.dto.member.LoginRequest;
import com.mini.money.service.CartService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CartServiceImplTest {

    @Autowired
    CartService cartService;

    @Test
    void 장바구니목록_조회() {
        // Given
        String email = "ddd111@naver.com";
        String password = "a11111111";
        LoginRequest loginRequest = new LoginRequest(email, password);
        // When
        List<LoanResponse> list = cartService.selectCartList(loginRequest);
        // Then
        System.out.println(list);
        assertThat(list).hasSize(2);
    }
}