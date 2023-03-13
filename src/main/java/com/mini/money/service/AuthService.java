package com.mini.money.service;

import com.mini.money.dto.member.LoginRequest;
import com.mini.money.dto.member.CustomerDetailRequest;
import com.mini.money.dto.member.CustomerRequest;
import com.mini.money.dto.member.LoginResponse;
import com.mini.money.dto.mypage.*;

public interface AuthService {
    void signup(CustomerRequest signupReqDTO);

    LoginResponse login(LoginRequest loginRequest);

    void updateCustomerData(LoginRequest loginRequest, UpdateCustomerDataRequest updateCustomerDataRequest);

    CheckPasswordResponse checkPassword(LoginRequest loginRequest, CheckPasswordRequest passwordRequest);

    CustomerDataResponse findData(LoginRequest loginRequest);

    CustomerDetailDataResponse findDetailData(LoginRequest loginRequest);

    void customerDetailInfo(LoginRequest loginRequest, CustomerDetailRequest reqDTO);
}
