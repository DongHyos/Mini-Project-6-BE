package com.mini.money.controller;


import com.mini.money.dto.favor.FavorRequest;
import com.mini.money.dto.loan.LoanResponse;
import com.mini.money.dto.member.LoginRequest;
import com.mini.money.service.FavorService;
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
@Api(tags = {"찜 목록"}, description = "찜 목록 상품 추가, 삭제")
public class FavorController {

    private final FavorService favorService;

    @GetMapping("/mypage/favor")
    @ApiOperation(value = "내 찜 목록", notes = "회원의 찜 목록을 확인한다.")
    public ResponseEntity<List<LoanResponse>> selectFavorList(@AuthenticationPrincipal LoginRequest loginRequest){
        return ResponseEntity.ok().body(favorService.selectFavorList(loginRequest));
    }

    @PostMapping("/favor")
    @ApiOperation(value = "찜 목록 상품 추가", notes = "제품ID(snq)를 통해 찜목록에 상품을 추가한다.")
    public ResponseEntity<Void> addFavor(@RequestBody FavorRequest favorRequest, @AuthenticationPrincipal LoginRequest loginRequest){
        favorRequest.setEmail(loginRequest.getEmail());
        favorService.addFavor(favorRequest);

        return ResponseEntity
                .created(URI.create("/favor"))
                .build();
    }


    @DeleteMapping("/favor")
    @ApiOperation(value = "찜 목록 상품 삭제", notes = "제품ID(snq)를 통해 찜목록에 상품을 개별 삭제한다.")
    public ResponseEntity<Void> deleteFavor(@RequestBody FavorRequest favorRequest, @AuthenticationPrincipal LoginRequest loginRequest) {
        favorRequest.setEmail(loginRequest.getEmail());
        favorService.deleteFavor(favorRequest);

        return ResponseEntity.noContent().build();
    }
}
