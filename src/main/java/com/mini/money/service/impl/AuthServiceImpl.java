package com.mini.money.service.impl;

import com.mini.money.dto.CustomerDetailReqDTO;
import com.mini.money.dto.CustomerReqDTO;
import com.mini.money.dto.LogInReqDTO;
import com.mini.money.dto.LogInResDTO;
import com.mini.money.dto.myinfo.MyCustomerDetailInfoResDTO;
import com.mini.money.dto.myinfo.MyCustomerInfoResDTO;
import com.mini.money.dto.myinfo.UpdateDetailReqDTO;
import com.mini.money.dto.myinfo.UpdateInfoReqDTO;
import com.mini.money.entity.Customer;
import com.mini.money.entity.CustomerDetail;
import com.mini.money.exceptrion.member.DuplicateEmailException;
import com.mini.money.exceptrion.member.IdPasswordMismatchException;
import com.mini.money.exceptrion.member.NoSuchMemberException;
import com.mini.money.jwt.JwtProvider;
import com.mini.money.repository.CustomerDetailRepository;
import com.mini.money.repository.CustomerRepository;
import com.mini.money.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final CustomerRepository customerRepository;
    private final CustomerDetailRepository customerDetailRepository;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;


    @Transactional
    @Override
    public void signup(final CustomerReqDTO signupReqDTO) {
        duplicateEmail(signupReqDTO.getEmail());

        String password = encodingPassword(signupReqDTO.getPassword());
        signupReqDTO.setPassword(password);
        Customer customer = signupReqDTO.toEntity();

        customerRepository.save(customer);

    }

    @Override
    public LogInResDTO login(LogInReqDTO logInReqDTO) {

        Customer findCustomer = customerRepository.findByEmail(logInReqDTO.getEmail())
                .orElseThrow(NoSuchMemberException::new);

        validatePassword(findCustomer, logInReqDTO.getPassword());
        String token = jwtProvider.makeToken(findCustomer);

        Customer customer = null;
            try {
                customer = customerRepository.findByEmail(logInReqDTO.getEmail());
            }
            catch (Exception e){
            }
            if(customer ==null || !passwordEncoder.matches(logInReqDTO.getPassword(),customer.getPassword())){
                throw new IllegalArgumentException();
            }

            String token = jwtProvider.makeToken(customer);
            LogInResDTO logInResDTO = new LogInResDTO(customer, token);

            return logInResDTO;


    }

//    @Transactional
//    @Modifying
//    @Override
//    public String updateInfo(UpdateInfoReqDTO updateInfoReqDTO, String email) {
//        System.out.println(updateInfoReqDTO);
//        Customer customer = customerRepository.findByEmail(email);
//        String password = updateInfoReqDTO.getPassword();
//        String phone = updateInfoReqDTO.getPhone();
//
//        if (password == null || password.isBlank()) {
//            password = customer.getPassword();
//        }
//
//        if (phone == null || phone.isBlank()) {
//            phone = customer.getPhone();
//        }
//
//        if (password.length() < 20) {
//            password = encodingPassword(password);
//        }
//
//        Integer result = customerRepository.updateInfo(password, phone, email);
//
//        if (result > 0) {
//            return "success";
//        } else {
//            return "false";
//        }
//    }
//
//    @Override
//    public Map<String, String> checkPassword(String email, String requestPassword) {
//        Customer customer = customerRepository.findByEmail(email);
//        Map<String, String> customerData = new HashMap<>();
//        if (!passwordEncoder.matches(requestPassword, customer.getPassword())) {
//            throw new IllegalArgumentException();
//        }
//        customerData.put("name", customer.getName());
//        return customerData;
//    }
//
//    @Override
//    public MyCustomerInfoResDTO findMyInfo(String email) {
//        Customer customer = customerRepository.findByEmail(email);
//        MyCustomerInfoResDTO my = new MyCustomerInfoResDTO(
//                customer.getEmail(), customer.getName(), customer.getPhone()
//        );
//        return my;
//    }
//
//    @Override
//    public MyCustomerDetailInfoResDTO findMyDetailInfo(String email) {
//        try {
//            Customer loginCustomer = customerRepository.findByEmail(email);
//            CustomerDetail customerDetail = customerDetailRepository.findByCustomer(loginCustomer).orElseThrow(() -> new IllegalStateException("추가 정보 등록 요망"));
//
//            MyCustomerDetailInfoResDTO my = new MyCustomerDetailInfoResDTO(
//                    customerDetail.getAge(), customerDetail.getAddress(), customerDetail.getJob(),
//                    customerDetail.getBank(), customerDetail.getCrdtGrade(), customerDetail.getIncome()
//            );
//
//            return my;
//        } catch (IllegalStateException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    @Transactional
//    @Override
//    public String customerDetailInfo(String email, CustomerDetailReqDTO reqDTO) {
//        Customer loginCustomer = customerRepository.findByEmail(email);
//        CustomerDetail customerDetail = reqDTO.toEntity(loginCustomer);
//        customerDetailRepository.save(customerDetail);
//
//        return "success";
//    }
//
//    @Transactional
//    @Modifying
//    @Override
//    public String updateDetailInfo(UpdateDetailReqDTO detailReqDTO, String email) {
//        Customer customer = customerRepository.findByEmail(email);
//        CustomerDetail detail = customerDetailRepository.findByCustomer(customer).orElse(null);
//
//        String address = detailReqDTO.getAddress() == null || detailReqDTO.getAddress().isBlank()? detail.getAddress():detailReqDTO.getAddress();
//        Integer age = detailReqDTO.getAge() == null? detail.getAge():detailReqDTO.getAge();
//        Double crdtGrade = detailReqDTO.getCrdtGrade() == null? detail.getCrdtGrade():detailReqDTO.getCrdtGrade();
//        String job = detailReqDTO.getJob() == null || detailReqDTO.getJob().isBlank()? detail.getJob():detailReqDTO.getJob();
//        String income = detailReqDTO.getIncome() == null? detail.getIncome():detailReqDTO.getIncome();
//        String bank = detailReqDTO.getBank() == null || detailReqDTO.getBank().isBlank()? detail.getBank():detailReqDTO.getBank();
//
//        Integer result = customerDetailRepository.updateDetailInfo(
//                address,
//                age,
//                crdtGrade,
//                job,
//                income,
//                bank,
//                detail.getId());
//
//        return result > 0? "success":"failed";
//    }


    public void duplicateEmail(final String email) {
        if (customerRepository.existsByEmail(email)) {
            throw new DuplicateEmailException();
        }
    }

    private String encodingPassword(String password) {
        return passwordEncoder.encode(password);
    }

    private void validatePassword(final Customer findCustomer, final String password) {
        if (!passwordEncoder.matches(password, findCustomer.getPassword())) {
            throw new IdPasswordMismatchException();
        }
    }

}
