package com.mini.money.dto.loan;

import com.mini.money.entity.Loan;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoanResponse {

    private Long snq;
    private String loanName; // 금융상품명
    private String loanDescription; // 지원대상 상세조건
    private String[] loanTarget; // 대상
    private String baseRate; // 금리구분
    private String rate; // 금리

    public LoanResponse(Loan loan) {
        this.snq = loan.getSnq();
        this.loanName = loan.getLoanName();
        this.baseRate = loan.getBaseRate();
        this.rate = loan.getRate();
        this.loanTarget = loan.getLoanTarget().split(",");
        this.loanDescription = loan.getLoanDescription();
    }

    public LoanResponse(Long snq, String loanName, String rate, String provider, String loanLimit, String loanTarget) {
        this.snq = snq;
        this.loanName = loanName;
        this.rate = rate;
        this.loanTarget = loanTarget.split(",");
    }

    public static LoanResponse from(final Loan loan) {
        return new LoanResponse(
                loan.getSnq(),
                loan.getLoanName(),
                loan.getLoanDescription(),
                loan.getLoanTarget().split(","),
                loan.getBaseRate(),
                loan.getRate()
        );
    }

}
