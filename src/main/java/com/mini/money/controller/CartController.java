package com.mini.money.controller;

import com.mini.money.dto.cart.CartRequest;
import com.mini.money.dto.loan.LoanResponse;
import com.mini.money.dto.member.LoginRequest;
import com.mini.money.service.CartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Api(tags = {"장바구니"}, description = "장바구니 상품 추가, 삭제")
public class CartController {

    private final CartService cartService;

    @GetMapping("/mypage/cart")
    @ApiOperation(value = "장바구니 확인", notes = "회원의 장바구니를 확인한다.")
    public ResponseEntity<List<LoanResponse>> selectCartList(@AuthenticationPrincipal LoginRequest loginRequest){
        return ResponseEntity.ok().body(cartService.selectCartList(loginRequest));
    }

    @PostMapping("/cart")
    @ApiOperation(value = "장바구니 상품 추가", notes = "제품ID(snq)를 통해 장바구니에 상품을 추가한다.")
    public ResponseEntity<Void> addCart(@RequestBody CartRequest cartRequest, @AuthenticationPrincipal LoginRequest loginRequest) {
        cartRequest.setEmail(loginRequest.getEmail());
        cartService.addCart(cartRequest);

        return ResponseEntity
                .created(URI.create("/cart"))
                .build();
    }

    @DeleteMapping("/cart")
    @ApiOperation(value = "장바구니 상품 삭제", notes = "제품ID(snq)를 통해 장바구니에 상품을 개별 삭제한다.")
    public ResponseEntity<Void> deleteProduct(@RequestBody CartRequest cartRequest, @AuthenticationPrincipal LoginRequest loginRequest) {
        cartRequest.setEmail(loginRequest.getEmail());
        cartService.deleteProduct(cartRequest);

        return ResponseEntity.noContent().build();
    }
}
