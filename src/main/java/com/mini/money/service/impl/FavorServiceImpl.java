package com.mini.money.service.impl;

import com.mini.money.dto.loan.LoanResponse;
import com.mini.money.dto.favor.FavorRequest;
import com.mini.money.dto.member.LoginRequest;
import com.mini.money.entity.Cart;
import com.mini.money.entity.Customer;
import com.mini.money.entity.Favor;
import com.mini.money.entity.Loan;
import com.mini.money.exceptrion.favor.DuplicateFavorException;
import com.mini.money.exceptrion.loan.NoSuchLoanException;
import com.mini.money.exceptrion.member.NoSuchMemberException;
import com.mini.money.repository.CustomerRepository;
import com.mini.money.repository.FavorRepository;
import com.mini.money.repository.LoanRepository;
import com.mini.money.service.FavorService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FavorServiceImpl implements FavorService {

    private final FavorRepository favorRepo;

    private final CustomerRepository customerRepo;

    private final LoanRepository loanRepo;

    /**
     * 찜 목록 조회
     * @param favorRequest 회원이메일(email), 상품번호(snq)
     * 찜 목록에 중복 상품 추가 시 DuplicateFavorException() 발생
     * 10개 제한으로, 10개가 넘어갈 경우 가장 먼저 추가한 대출 상품 삭제
     */
    @Transactional
    @Override
    public void addFavor(final FavorRequest favorRequest) {
        Customer customer = customerRepo.findByEmail(favorRequest.getEmail())
                .orElseThrow(NoSuchMemberException::new);
        Loan loan = loanRepo.findBySnq(favorRequest.getSnq())
                .orElseThrow(NoSuchLoanException::new);

        duplicatedFavor(customer, loan);
        deleteLastFavor(customer);

        favorRepo.save(favorRequest.toEntity(customer, loan));
    }

    /**
     * 찜 목록 삭제
     * @param favorRequest 회원이메일(email), 상품번호(snq)
     * 삭제할 데이터가 테이블에 없을 경우 NoSuchLoanException() 발생
     */
    @Transactional
    @Override
    public void deleteFavor(final FavorRequest favorRequest) {
        Customer customer = customerRepo.findByEmail(favorRequest.getEmail())
                .orElseThrow(NoSuchMemberException::new);
        Loan loan = loanRepo.findBySnq(favorRequest.getSnq())
                .orElseThrow(NoSuchLoanException::new);

        checkExistFavor(customer, loan);

        favorRepo.deleteByCustomerAndLoan(customer, loan);
    }

    /**
     * 찜 목록 조회
     * @param loginRequest 회원이메일(email), 상품번호(snq)
     * @return Favor 테이블에 존재하는 데이터(Loan)를 LoanResponse 형태로 변환 후 반환
     */
    @Override
    @Transactional(readOnly = true)
    public List<LoanResponse> selectFavorList(final LoginRequest loginRequest) {
        Customer customer = customerRepo.findByEmail(loginRequest.getEmail())
                .orElseThrow(NoSuchMemberException::new);

        return favorRepo.findFavorList(customer)
                .stream().map(loan -> new LoanResponse(loan))
                .collect(Collectors.toList());
    }

    /**
     * 메인페이지 내 로그인 안했을 때의 추천 리스트(찜 테이블 중 Top10 대출 상품)
     * @return Favor 테이블에 존재하는 데이터(Loan)를 LoanResponse 형태로 변환 후 반환
     */
    @Override
    @Transactional(readOnly = true)
    public Page<LoanResponse> popularList() {
        PageRequest page = PageRequest.of(0, 10);
        return favorRepo.findPopularData(page)
                .map(loan -> new LoanResponse(loan));
    }

    public void deleteLastFavor(final Customer customer) {
        List<Favor> existList = favorRepo.findAllByCustomer(customer);

        if (existList.size() == 10) {
            Favor favor = favorRepo.findFirstByCustomerOrderByIdAsc(customer);
            favorRepo.delete(favor);
        }
    }

    public void checkExistFavor(final Customer customer, final Loan loan) {
        if (!favorRepo.existsByCustomerAndLoan(customer, loan)) {
            throw new NoSuchLoanException();
        }
    }

    public void duplicatedFavor(final Customer customer, final Loan loan) {
        if (favorRepo.existsByCustomerAndLoan(customer, loan)) {
            throw new DuplicateFavorException();
        }
    }
}
