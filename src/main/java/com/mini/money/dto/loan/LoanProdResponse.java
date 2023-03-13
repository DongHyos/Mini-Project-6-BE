package com.mini.money.dto.loan;

import com.mini.money.entity.Loan;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoanProdResponse {

    private String loanName; //상품 이름
    private String usge; // 용도
    private String loanLimit; // 대출한도
    private String rate; // 금리
    private String overdueRate; // 연체이자율
    private String wholePeriod; // 최대총대출기간
    private String gracePeriod; // 최대거치기간
    private String repayPeriod; // 최대상환기관
    private String repayMethod; // 상환방법

    public static LoanProdResponse from(final Loan loan) {
        return new LoanProdResponse(
                loan.getLoanName(),
                loan.getUsge(),
                loan.getLoanLimit(),
                loan.getRate(),
                loan.getOverdueRate(),
                loan.getWholePeriod(),
                loan.getGracePeriod(),
                loan.getRepayPeriod(),
                loan.getRepayMethod()
        );
    }
}
