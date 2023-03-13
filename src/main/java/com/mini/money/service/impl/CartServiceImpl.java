package com.mini.money.service.impl;

import com.mini.money.dto.loan.LoanResponse;
import com.mini.money.dto.cart.CartRequest;
import com.mini.money.dto.member.LoginRequest;
import com.mini.money.entity.Cart;
import com.mini.money.entity.Customer;
import com.mini.money.entity.Loan;
import com.mini.money.exceptrion.cart.DuplicateCartException;
import com.mini.money.exceptrion.loan.NoSuchLoanException;
import com.mini.money.exceptrion.member.NoSuchMemberException;
import com.mini.money.repository.CartRepository;
import com.mini.money.repository.CustomerRepository;
import com.mini.money.repository.LoanRepository;
import com.mini.money.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepo;
    private final CustomerRepository customerRepo;
    private final LoanRepository loanRepo;

    /**
     * 장바구니에 대출 상품 추가
     * @param cartRequest 회원이메일(email), 상품번호(snq)
     * 장바구니에 중복 상품 추가 시 DuplicateCartException() 발생
     * 10개 제한으로, 10개가 넘어갈 경우 가장 먼저 추가한 대출 상품 삭제
     */
    @Transactional
    @Override
    public void addCart(final CartRequest cartRequest) {
        Customer customer = customerRepo.findByEmail(cartRequest.getEmail())
                .orElseThrow(NoSuchMemberException::new);
        Loan loan = loanRepo.findBySnq(cartRequest.getSnq())
                .orElseThrow(NoSuchLoanException::new);

        duplicatedCart(customer, loan);
        deleteLastCart(customer);

        cartRepo.save(cartRequest.toEntity(customer, loan));
    }

    /**
     * 장바구니 삭제
     * @param cartRequest 회원이메일(email), 상품번호(snq)
     * 삭제할 데이터가 테이블에 없을 경우 NoSuchLoanException() 발생
     */
    @Transactional
    @Override
    public void deleteProduct(final CartRequest cartRequest) {
        Customer customer = customerRepo.findByEmail(cartRequest.getEmail())
                .orElseThrow(NoSuchMemberException::new);
        Loan loan = loanRepo.findBySnq(cartRequest.getSnq())
                .orElseThrow(NoSuchLoanException::new);

        checkExistCart(customer, loan);

        cartRepo.deleteByCustomerAndLoan(customer, loan);
    }

    /**
     * 장바구니 조회
     * @param loginRequest 회원이메일(email), 상품번호(snq)
     * @return Cart 테이블에 존재하는 데이터(Loan)를 LoanResponse 형태로 변환 후 반환
     */
    @Override
    @Transactional(readOnly = true)
    public List<LoanResponse> selectCartList(final LoginRequest loginRequest) {
        Customer customer = customerRepo.findByEmail(loginRequest.getEmail())
                .orElseThrow(NoSuchMemberException::new);

        return cartRepo.findCartList(customer)
                .stream().map(loan -> new LoanResponse(loan))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteLastCart(final Customer customer) {
        List<Cart> carts = cartRepo.findAllByCustomer(customer);

        if (carts.size() == 10) {
            Cart cart = cartRepo.findFirstByCustomerOrderByIdAsc(customer);
            cartRepo.deleteById(cart.getId());
        }
    }

    public void checkExistCart(final Customer customer, final Loan loan) {
        if (!cartRepo.existsByCustomerAndLoan(customer, loan)) {
            throw new NoSuchLoanException();
        }
    }

    public void duplicatedCart(final Customer customer, final Loan loan) {
        if (cartRepo.existsByCustomerAndLoan(customer, loan)) {
            throw new DuplicateCartException();
        }
    }
}