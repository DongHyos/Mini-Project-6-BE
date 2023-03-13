package com.mini.money.service.impl;

import com.mini.money.entity.Blacklist;
import com.mini.money.repository.TokenRepository;
import com.mini.money.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService{

    private final TokenRepository tokenRepository;

    /**
     * 로그아웃
     * @param token 로그인 시 발급되는 토큰
     */
    @Transactional
    @Override
    public void logout(String token) {
        existedBlackList(token);
        tokenRepository.save(Blacklist.builder().token(token).build());
    }

    /**
     * 테이블 내의 토큰 확인
     * @param token 로그인 시 발급되는 토큰
     * @return 로그인 시 발급되는 토큰이 BlackList 테이블 내에 있다면 True, 없다면 False 반환
     */
    @Override
    public boolean checkBlacklist(String token) {
        return tokenRepository.existsByToken(token);
    }

    public void existedBlackList(String token) {
        if (checkBlacklist(token)) {
            tokenRepository.delete(tokenRepository.findByToken(token));
        }
    }
}