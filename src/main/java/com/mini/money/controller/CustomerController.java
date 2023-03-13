package com.mini.money.controller;

import com.mini.money.dto.member.LoginRequest;
import com.mini.money.dto.member.CustomerDetailRequest;
import com.mini.money.dto.member.CustomerRequest;
import com.mini.money.dto.member.LoginResponse;
import com.mini.money.service.AuthService;
import com.mini.money.service.TokenService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequiredArgsConstructor
@Api(tags = {"회원 필수 정보 서비스"}, description = "회원가입, 회원정보수정, 회원탈퇴, 패스워드 확인")
public class CustomerController {

    private final AuthService authService;
    private final TokenService tokenService;

    @PostMapping("/signup")
    @ApiOperation(value = "회원가입", notes = "필수 정보를 입력받아 회원가입 시도, 성공 시 DB에 저장한다.")
    public ResponseEntity<Void> signUp(@Valid @RequestBody CustomerRequest customerRequest) {
        authService.signup(customerRequest);
        return ResponseEntity
                .created(URI.create("/signup"))
                .build();
    }

    @PostMapping("/login")
    @ApiOperation(value = "로그인", notes = "이메일과 패스워드를 입력받아 로그인 시도, 성공시 토큰 발급한다.")
    public ResponseEntity<LoginResponse> logIn(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok().body(authService.login(loginRequest));
    }

    @PostMapping("/logout")
    @ApiOperation(value = "로그아웃", notes = "로그인 토큰을 블랙리스트 테이블에 저장한다.")
    public ResponseEntity<Void> logOut(@ApiIgnore @RequestHeader(name = "Authorization") String token) {
        tokenService.logout(token);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/signup/detail")
    @ApiOperation(value = "회원 추가 정보 입력", notes = "회원 추가 정보를 입력 받아 저장을 시도, 성공 시 DB에 저장한다.")
    public ResponseEntity<Void> customerDetailInfo(@RequestBody CustomerDetailRequest customerDetailRequest, @AuthenticationPrincipal LoginRequest loginRequest) {
        authService.customerDetailInfo(loginRequest, customerDetailRequest);
        return ResponseEntity
                .created(URI.create("/signup/detail"))
                .build();
    }
}
