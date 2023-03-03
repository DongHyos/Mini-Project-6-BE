package com.mini.money.service;

import com.mini.money.dto.CustomerDetailReqDTO;
import com.mini.money.dto.CustomerReqDTO;
import com.mini.money.dto.LogInReqDTO;
import com.mini.money.dto.LogInResDTO;
import com.mini.money.dto.myinfo.MyCustomerDetailInfoResDTO;
import com.mini.money.dto.myinfo.MyCustomerInfoResDTO;
import com.mini.money.dto.myinfo.UpdateDetailReqDTO;
import com.mini.money.dto.myinfo.UpdateInfoReqDTO;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface AuthService {
    void signup(CustomerReqDTO signupReqDTO);

    LogInResDTO login(LogInReqDTO logInReqDTO);
//
//    String updateInfo(UpdateInfoReqDTO updateInfoReqDTO, String email);
//
//    Map<String, String> checkPassword(String email, String requestPassword);
//
//    MyCustomerInfoResDTO findMyInfo(String email);
//
//    MyCustomerDetailInfoResDTO findMyDetailInfo(String email);
//
//    String customerDetailInfo(String email, CustomerDetailReqDTO reqDTO);
//
//    String updateDetailInfo(UpdateDetailReqDTO updateDetailReqDTO, String email);
}
