package com.mini.money.controller;

import com.mini.money.dto.CustomerReqDTO;
import com.mini.money.dto.LogInReqDTO;
import com.mini.money.dto.LogInResDTO;
import com.mini.money.dto.myinfo.MyCustomerInfoResDTO;
import com.mini.money.dto.myinfo.UpdateInfoReqDTO;
import com.mini.money.service.AuthService;
import com.mini.money.service.TokenService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Api(tags = {"회원 필수 정보 서비스"}, description = "회원가입, 회원정보수정, 회원탈퇴, 패스워드 확인")
public class CustomerController {

    private final AuthService authService;
    private final TokenService tokenService;

    @GetMapping("/mypage/info")
    @ApiOperation(value = "회원정보 확인", notes = "회원 정보를 확인 기능")
    public ResponseEntity<MyCustomerInfoResDTO> findMyInfo(@AuthenticationPrincipal LogInReqDTO customer) {
        String email = customer.getEmail();
        MyCustomerInfoResDTO myCustomerInfoResDTO = authService.findMyInfo(email);

        return new ResponseEntity<>(myCustomerInfoResDTO, HttpStatus.OK);
    }

    @PostMapping("/signup")
    @ApiOperation(value = "회원가입", notes = "필수 정보를 입력받아 회원가입 시도, 성공 시 DB에 저장한다.")
    public ResponseEntity<Void> signUp(@Valid @RequestBody CustomerReqDTO signupReqDTO) {
        authService.signup(signupReqDTO);
        return ResponseEntity
                .created(URI.create("/signup"))
                .build();
    }

    @PostMapping("/login")
    @ApiOperation(value = "로그인", notes = "이메일과 패스워드를 입력받아 로그인 시도, 성공시 토큰 발급한다.")
    public LogInResDTO logIn(@RequestBody LogInReqDTO logInReqDTO) {
        return authService.login(logInReqDTO);
    }

    @PostMapping("/logout")
    @ApiOperation(value = "로그아웃", notes = "로그인 토큰을 블랙리스트 테이블에 저장한다.")
    public ResponseEntity logOut(@ApiIgnore @RequestHeader(name = "Authorization") String token) {

        return tokenService.logout(token);
    }

    @PutMapping("/mypage/member")
    @ApiOperation(value = "회원 정보 수정", notes = "회원 정보(비밀번호, 핸드폰번호) 수정이 가능하다.")
    public ResponseEntity<String> updateInfo(@RequestBody UpdateInfoReqDTO updateInfoReqDTO, @AuthenticationPrincipal LogInReqDTO customer) {
        String email = customer.getEmail();
        String message = authService.updateInfo(updateInfoReqDTO, email);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @PostMapping("/mypage/check")
    @ApiOperation(value = "비밀번호 확인", notes = "비밀번호 확인 기능")
    public Map<String, String> checkPassword(@AuthenticationPrincipal LogInReqDTO logInReqDTO, @RequestBody HashMap<String, Object> map){
        return authService.checkPassword(logInReqDTO.getEmail(), map.get("password").toString());
    }
}
