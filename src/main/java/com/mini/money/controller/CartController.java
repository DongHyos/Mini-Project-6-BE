package com.mini.money.controller;

import com.mini.money.dto.LoanResDTO;
import com.mini.money.dto.LogInReqDTO;
import com.mini.money.service.CartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Api(tags = {"장바구니"}, description = "장바구니 상품 추가, 삭제")
public class CartController {

    private final CartService cartService;

    @GetMapping("/mypage/cart")
    @ApiOperation(value = "장바구니 확인", notes = "회원의 장바구니를 확인한다.")
    public List<LoanResDTO> selectCartList(@AuthenticationPrincipal LogInReqDTO logInReqDTO){
        String email = logInReqDTO.getEmail();
        return cartService.selectCartList(email);
    }

    @PostMapping("/cart")
    @ApiOperation(value = "장바구니 상품 추가", notes = "제품ID(snq)를 통해 장바구니에 상품을 추가한다.")
    public ResponseEntity<String> addCart(@RequestBody HashMap<String, Object> map, @AuthenticationPrincipal LogInReqDTO customer) {
        System.out.println(map);
        String email = customer.getEmail();
        Long snq = Long.valueOf(map.get("snq").toString());
        String message = cartService.addCart(snq, email);

        if (message.equals("success")) {
            return new ResponseEntity<>(message, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
    }
    @DeleteMapping("/cart")
    @ApiOperation(value = "장바구니 상품 삭제", notes = "제품ID(snq)를 통해 장바구니에 상품을 개별 삭제한다.")
    public String deleteProduct(@AuthenticationPrincipal LogInReqDTO customer, @RequestBody HashMap<String, Object> map){
        Long snq = Long.valueOf(map.get("snq").toString());
        String email = customer.getEmail();
        return cartService.deleteProduct(email, snq);
    }
}
