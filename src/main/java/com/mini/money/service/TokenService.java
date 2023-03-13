package com.mini.money.service;


public interface TokenService {

    void logout(String header);

    boolean checkBlacklist(String token);

}
