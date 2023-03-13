package com.mini.money.dto.loan;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoanDetailResponse {
    private LoanProdResponse loanProdResponse;
    private LoanTargetResponse loanTargetResponse;
    private LoanEtcResponse loanEtcResponse;

    public static LoanDetailResponse from(final LoanProdResponse loanProdResponse,
                                          final LoanTargetResponse loanTargetResponse,
                                          final LoanEtcResponse loanEtcResponse) {
        return new LoanDetailResponse(
                loanProdResponse,
                loanTargetResponse,
                loanEtcResponse
        );
    }
}
