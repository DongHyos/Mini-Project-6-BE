package com.mini.money.service;

import com.mini.money.dto.loan.LoanDetailResponse;
import com.mini.money.dto.loan.LoanResponse;
import com.mini.money.dto.member.LoginRequest;
import com.mini.money.parameter.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.HashMap;
import java.util.List;

public interface LoanService {

    List<LoanResponse> selectLoanList();

    Page<LoanResponse> selectAll(Pageable pageable);

    Page<LoanResponse> selectByOffice(Office office, Pageable pageable);

    Page<LoanResponse> selectByArea(Area area, Pageable pageable);

    Page<LoanResponse> selectByRepayment(Repayment repayment, Pageable pageable);

    Page<LoanResponse> selectByUsge(Usge usge, Pageable pageable);

    Page<LoanResponse> selectByTarget(Target target, Pageable pageable);

    Page<LoanResponse> selectByBaseRate(BaseRate baseRate, Pageable pageable);

    Page<LoanResponse> selectByMaturity(String maturity, Pageable pageable);

    Page<LoanResponse> selectByCredit(String credit, Pageable pageable);

    Page<LoanResponse> selectByKeyword(String keyword, Pageable pageable);

    LoanDetailResponse selectLoanDetail(Long snq);

    Page<LoanResponse> memberCommendLoanList(LoginRequest logInReqDTO);

}
