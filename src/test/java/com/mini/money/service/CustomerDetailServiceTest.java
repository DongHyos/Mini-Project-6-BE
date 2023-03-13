//package com.mini.money.service;
//
//import com.mini.money.dto.member.CustomerDetailRequest;
//import com.mini.money.dto.mypage.CustomerDetailDataResponse;
//import com.mini.money.repository.CustomerRepository;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//class CustomerDetailServiceTest {
//
//    @Autowired
//    CustomerDetailService cs;
//    @Autowired
//    CustomerRepository customerRepository;
//
//    @Test
//    void name() {
//        String email = "dong8707@naver.com";
//        CustomerDetailRequest customerDetailReqDTO = new CustomerDetailRequest(26, "서울", "근로자", "우리은행", 800.0, 3500);
//
//        cs.customerDetailInfo(email, customerDetailReqDTO);
//
//    }
//
//    @Test
//    void name2() {
//        String email = "dong8707000@naver.com";
//
//        try {
//            CustomerDetailDataResponse my = cs.findMyDetailInfo(email);
//            System.out.println(my);
//        } catch (IllegalStateException e) {
//            e.printStackTrace();
//            System.out.println("failed");
//        }
//
//    }
//}