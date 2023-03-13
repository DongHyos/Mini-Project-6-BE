package com.mini.money.controller;


import com.mini.money.dto.loan.LoanDetailResponse;
import com.mini.money.dto.loan.LoanResponse;
import com.mini.money.dto.member.LoginRequest;
import com.mini.money.parameter.*;
import com.mini.money.service.FavorService;
import com.mini.money.service.LoanService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Api(tags = {"상품 서비스"}, description = "전체 상품 조회, 항목 별 상품 조회, 상품 추천")
public class LoanController {

    private final LoanService loanService;
    private final FavorService favorService;

    @GetMapping("/finance/loan")
    public ResponseEntity<List<LoanResponse>> selectLoanList() {
        return ResponseEntity.ok().body(loanService.selectLoanList());
    }

    @GetMapping("/finance/itemlist")
    public ResponseEntity<Page<LoanResponse>> allSelectList(Pageable pageable) {
        return ResponseEntity.ok().body(loanService.selectAll(pageable));
    }

    @GetMapping("/finance/itemlist/office")
    public ResponseEntity<Page<LoanResponse>> selectByOffice(@RequestParam(name = "office") Office office, Pageable pageable) {
        return ResponseEntity.ok().body(loanService.selectByOffice(office, pageable));
    }

    @GetMapping("/finance/itemlist/area")
    public ResponseEntity<Page<LoanResponse>> selectByArea(@RequestParam(name = "area") Area area, Pageable pageable) {
        return ResponseEntity.ok().body(loanService.selectByArea(area, pageable));
    }

    @GetMapping("/finance/itemlist/repayment")
    public ResponseEntity<Page<LoanResponse>> selectByRepayment(@RequestParam(name = "repayment") Repayment repayment, Pageable pageable) {
        return ResponseEntity.ok().body(loanService.selectByRepayment(repayment, pageable));
    }

    @GetMapping("/finance/itemlist/usge")
    public ResponseEntity<Page<LoanResponse>> selectByUsge(@RequestParam(name = "usge") Usge usge, Pageable pageable) {
        return ResponseEntity.ok().body(loanService.selectByUsge(usge, pageable));
    }

    @GetMapping("/finance/itemlist/target")
    public ResponseEntity<Page<LoanResponse>> selectByTarget(@RequestParam(name = "target") Target target, Pageable pageable) {
        return ResponseEntity.ok().body(loanService.selectByTarget(target, pageable));
    }

    @GetMapping("/finance/itemlist/baseRate")
    public ResponseEntity<Page<LoanResponse>> selectByBaseRate(@RequestParam(name = "baseRate") BaseRate baseRate, Pageable pageable) {
        return ResponseEntity.ok().body(loanService.selectByBaseRate(baseRate, pageable));
    }

    @GetMapping("/finance/itemlist/maturity")
    public ResponseEntity<Page<LoanResponse>> selectByMaturity(@RequestParam(name = "maturity") String maturity, Pageable pageable) {
        return ResponseEntity.ok().body(loanService.selectByMaturity(maturity, pageable));
    }

    @GetMapping("/finance/itemlist/credit")
    public ResponseEntity<Page<LoanResponse>> selectByCredit(@RequestParam(name = "credit") String credit, Pageable pageable) {
        return ResponseEntity.ok().body(loanService.selectByCredit(credit, pageable));
    }

    @GetMapping("/finance/itemlist/keyword")
    public ResponseEntity<Page<LoanResponse>> selectByKeyword(@RequestParam(name = "keyword") String keyword, Pageable pageable) {
        return ResponseEntity.ok().body(loanService.selectByKeyword(keyword, pageable));
    }

    @GetMapping("/finance/loan/detail/{snq}")
    @ApiOperation(value = "상품 상세 정보 반환", notes = "상품 상세 정보를 반환")
    public ResponseEntity<LoanDetailResponse> selectLoanDetail(@PathVariable Long snq) {
        return ResponseEntity.ok().body(loanService.selectLoanDetail(snq));
    }

    @GetMapping("/finance/member/recommend/loan")
    @ApiOperation(value = "(로그인 상태)상품 추천 리스트 반환", notes = "사용자 지역의 상품 리스트를 반환, 설정한 지역이 없다면 전국")
    public ResponseEntity<Page<LoanResponse>> memberRecommendList(@AuthenticationPrincipal LoginRequest logInReqDTO) {
        return ResponseEntity.ok().body(loanService.memberCommendLoanList(logInReqDTO));
    }

    @GetMapping("/finance/recommend/loan")
    public ResponseEntity<Page<LoanResponse>> recommendDatas() {
        return ResponseEntity.ok().body(favorService.popularList());
    }
}
