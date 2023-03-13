package com.mini.money.service.impl;

import com.mini.money.dto.member.CustomerDetailRequest;
import com.mini.money.dto.member.CustomerRequest;
import com.mini.money.dto.member.LoginRequest;
import com.mini.money.dto.member.LoginResponse;
import com.mini.money.dto.mypage.*;
import com.mini.money.entity.Customer;
import com.mini.money.exceptrion.member.DuplicateEmailException;
import com.mini.money.exceptrion.member.IdPasswordMismatchException;
import com.mini.money.exceptrion.member.NoSuchMemberException;
import com.mini.money.jwt.JwtProvider;
import com.mini.money.repository.CustomerRepository;
import com.mini.money.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final CustomerRepository customerRepository;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

    /**
     * 회원가입
     * @param customerRequest 이메일(email), 비밀번호(password), 이름(name), 휴대폰번호(phone)
     * 이메일 중복 체크 후 회원 테이블에 저장
     */
    @Transactional
    @Override
    public void signup(final CustomerRequest customerRequest) {
        duplicateEmail(customerRequest.getEmail());

        String password = encodingPassword(customerRequest.getPassword());
        customerRequest.setPassword(password);
        Customer customer = customerRequest.toEntity();

        customerRepository.save(customer);
    }

    /**
     * 로그인
     * @param loginRequest 이메일(email), 비밀번호(password)
     * @return 이메일(email), 이름(name), 토큰(token) 값을 가진 LoginResponse를 반환
     * 이메일을 통한 회원 조회, 비밀번호 확인
     */
    @Override
    public LoginResponse login(final LoginRequest loginRequest) {
        Customer findCustomer = customerRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(NoSuchMemberException::new);
        validatePassword(findCustomer, loginRequest.getPassword());

        String token = jwtProvider.makeToken(findCustomer);
        return LoginResponse.from(findCustomer, token);
    }

    /**
     * 회원 정보 수정
     * @param loginRequest 이메일(email), 비밀번호(password)
     * @param updateCustomerDataRequest 비밀번호(password), 전화번호(phone)
     * 수정 시 비밀번호 or 전화번호 하나만 수정한다고 하면 기존의 값을 전달
     */
    @Transactional
    @Override
    public void updateCustomerData(final LoginRequest loginRequest, final UpdateCustomerDataRequest updateCustomerDataRequest) {
        Customer customer = customerRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(NoSuchMemberException::new);

        String password = updateCheckByPassword(updateCustomerDataRequest);
        String phone = updateCheckByPhone(updateCustomerDataRequest);

        customer.update(password, phone);
    }

    /**
     * 회원 조회를 위한 추가 비밀번호 확인
     * @param loginRequest 이메일(email), 비밀번호(password)
     * @param passwordRequest 비밀번호(password)
     * @return 회원 확인 후 성공한다면 회원의 이름 전달
     */
    @Override
    public CheckPasswordResponse checkPassword(final LoginRequest loginRequest, final CheckPasswordRequest passwordRequest) {
        Customer findCustomer = customerRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(NoSuchMemberException::new);
        validatePassword(findCustomer, passwordRequest.getPassword());

        return CheckPasswordResponse.from(findCustomer);
    }

    /**
     * 회원 필수 정보 조회
     * @param loginRequest 이메일(email), 비밀번호(password)
     * @return 이메일(email), 이름(name), 휴대폰번호(phone)
     */
    @Override
    @Transactional(readOnly = true)
    public CustomerDataResponse findData(final LoginRequest loginRequest) {
        Customer findCustomer = customerRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(NoSuchMemberException::new);
        return CustomerDataResponse.from(findCustomer);
    }

    /**
     * 회원 상세 정보(추가 정보) 조회
     * @param loginRequest 이메일(email), 비밀번호(password)
     * @return 나이(age), 주소(address), 직업 (job), bank(주거래 은행), crdtGrade(신용점수), income(소득)
     */
    @Override
    @Transactional(readOnly = true)
    public CustomerDetailDataResponse findDetailData(final LoginRequest loginRequest) {
        Customer findCustomer = customerRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(NoSuchMemberException::new);

        return CustomerDetailDataResponse.from(findCustomer);
    }

    /**
     * 회원 추가 정보 저장
     * @param loginRequest 이메일(email), 비밀번호(password)
     * @param customerDetailRequest 나이(age), 주소(address), 직업 (job), bank(주거래 은행), crdtGrade(신용점수), income(소득)
     */
    @Transactional
    @Override
    public void customerDetailInfo(final LoginRequest loginRequest, final CustomerDetailRequest customerDetailRequest) {
        Customer loginCustomer = customerRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(NoSuchMemberException::new);

        Customer customerDetail = customerDetailRequest.toEntity(loginCustomer);
        customerRepository.save(customerDetail);
    }

    public String updateCheckByPassword(final UpdateCustomerDataRequest updateCustomerDataRequest) {
        String password = encodingPassword(updateCustomerDataRequest.getPassword());
        if (password == null || password.isBlank()) {
            password = updateCustomerDataRequest.getPassword();
        }
        return password;
    }

    public String updateCheckByPhone(final UpdateCustomerDataRequest updateCustomerDataRequest) {
        String phone = updateCustomerDataRequest.getPhone();
        if (phone == null || phone.isBlank()) {
            phone = updateCustomerDataRequest.getPhone();
        }
        return phone;
    }

    public void duplicateEmail(final String email) {
        if (customerRepository.existsByEmail(email)) {
            throw new DuplicateEmailException();
        }
    }

    private String encodingPassword(final String password) {
        return passwordEncoder.encode(password);
    }

    private void validatePassword(final Customer findCustomer, final String password) {
        if (!passwordEncoder.matches(password, findCustomer.getPassword())) {
            throw new IdPasswordMismatchException();
        }
    }

}
