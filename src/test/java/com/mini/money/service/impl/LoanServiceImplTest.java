package com.mini.money.service.impl;

import com.mini.money.dto.loan.LoanResponse;
import com.mini.money.service.FavorService;
import com.mini.money.service.LoanService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class LoanServiceImplTest {

    @Autowired
    LoanService loanService;

    @Autowired
    FavorService favorService;

    @Test
    void 메인페이지_대출리스트_조회() {
        // Given
        // When
        List<LoanResponse> loanResponses = loanService.selectLoanList();
        // Then
        System.out.println(loanResponses);
        assertThat(loanResponses).hasSize(3);
    }

    @Test
    void 전체_대출리스트() {
        // Given
        Pageable pageable = PageRequest.of(0, 10);
        // When
        Page<LoanResponse> test = loanService.selectAll(pageable);
        // Then
        System.out.println(test);
        assertThat(test).hasSize(10);
    }

    @Test
    void 로그인안했을때_추천대출_리스트() {
        // Given
        // When
        Page<LoanResponse> loanResponses = favorService.popularList();
        // Then
        System.out.println(loanResponses);
    }

    //    @Test
//    void e() {
//        Pageable pageable = PageRequest.of(0, 10);
//        Office office = Office.기타;
//
//        List<WholeResDTO> test = loanService.selectByOffice(office, pageable);
//        System.out.println(test);
//        assertThat(test).hasSize(10);
//    }
//
//    @Test
//    void f() {
//        Pageable pageable = PageRequest.of(0, 10);
//        Area office = Area.기타;
//
//        List<WholeResDTO> test = loanService.selectByArea(office, pageable);
//        System.out.println(test);
//        assertThat(test).hasSize(10);
//    }
//
//    @Test
//    void G() {
//        Pageable pageable = PageRequest.of(0, 10);
//        Repayment office = Repayment.만기일시상환;
//
//        List<WholeResDTO> test = loanService.selectByRepayment(office, pageable);
//        System.out.println(test);
//        assertThat(test).hasSize(10);
//    }
//
//    @Test
//    void H() {
//        Pageable pageable = PageRequest.of(0, 10);
//        Usge office = Usge.생계;
//
//        List<WholeResDTO> test = loanService.selectByUsge(office, pageable);
//        System.out.println(test);
//        assertThat(test).hasSize(10);
//    }
//
//    @Test
//    void Y() {
//        Pageable pageable = PageRequest.of(0, 10);
//        Target office = Target.근로자;
//
//        List<WholeResDTO> test = loanService.selectByTarget(office, pageable);
//        System.out.println(test);
//        assertThat(test).hasSize(10);
//    }
//
//    @Test
//    void z() {
//        Pageable pageable = PageRequest.of(0, 10);
//        BaseRate office = BaseRate.고정금리;
//
//        List<WholeResDTO> test = loanService.selectByBaseRate(office, pageable);
//        System.out.println(test);
//        assertThat(test).hasSize(10);
//    }
//
//    @Test
//    void Tza() {
//        Pageable pageable = PageRequest.of(0, 10);
//        String ma = "제한";
//
//        List<WholeResDTO> test = loanService.selectByMaturity(ma, pageable);
//        System.out.println(test);
//        assertThat(test).hasSize(10);
//    }
//
//    @Test
//    void za() {
//        Pageable pageable = PageRequest.of(2, 10);
//        String ma = "Y";
//
//        List<WholeResDTO> test = loanService.selectByCredit(ma, pageable);
//        System.out.println(test);
//        assertThat(test).hasSize(10);
//    }
//
//    @Test
//    void eee() {
//        Pageable pageable = PageRequest.of(0, 10);
//        String ma = "우리";
//
//        List<WholeResDTO> test = loanService.selectByKeyword(ma, pageable);
//        System.out.println(test);
//    }

}