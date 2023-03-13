package com.mini.money.service;

import com.mini.money.dto.favor.FavorRequest;
import com.mini.money.dto.loan.LoanResponse;
import com.mini.money.dto.member.LoginRequest;
import com.mini.money.entity.Favor;
import org.springframework.data.domain.Page;

import java.util.List;

public interface FavorService {

    void addFavor(final FavorRequest favorRequest);

    void deleteFavor(final FavorRequest favorRequest);

    List<LoanResponse> selectFavorList(final LoginRequest loginRequest);

    Page<LoanResponse> popularList();
}
