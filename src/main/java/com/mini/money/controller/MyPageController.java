package com.mini.money.controller;

import com.mini.money.dto.member.LoginRequest;
import com.mini.money.dto.mypage.*;
import com.mini.money.service.AuthService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mypage")
public class MyPageController {
    private final AuthService authService;

    @PostMapping("/check")
    @ApiOperation(value = "비밀번호 확인", notes = "비밀번호 확인 기능")
    public ResponseEntity<CheckPasswordResponse> checkPassword(@AuthenticationPrincipal LoginRequest loginRequest, @RequestBody CheckPasswordRequest passwordRequest){
        return ResponseEntity.ok().body(authService.checkPassword(loginRequest, passwordRequest));
    }

    @GetMapping("/info")
    @ApiOperation(value = "회원 정보 조회", notes = "회원 정보를 확인한다.")
    public ResponseEntity<CustomerDataResponse> findData(@AuthenticationPrincipal LoginRequest loginRequest) {
        return ResponseEntity.ok().body(authService.findData(loginRequest));
    }

    @GetMapping("/detail/info")
    @ApiOperation(value = "회원 추가 정보 조회", notes = "회원 추가 정보를 확인한다.")
    public ResponseEntity<CustomerDetailDataResponse> findDetailData(@AuthenticationPrincipal LoginRequest loginRequest) {
        return ResponseEntity.ok().body(authService.findDetailData(loginRequest));
    }

    @PutMapping("/member")
    @ApiOperation(value = "회원 정보 수정", notes = "회원 정보(비밀번호, 핸드폰번호) 수정이 가능하다.")
    public ResponseEntity<Void> updateInfo(@RequestBody @Valid UpdateCustomerDataRequest updateCustomerDataRequest, @AuthenticationPrincipal LoginRequest loginRequest) {
        authService.updateCustomerData(loginRequest, updateCustomerDataRequest);
        return ResponseEntity.ok().build();
    }
}
