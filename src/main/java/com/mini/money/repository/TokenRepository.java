package com.mini.money.repository;

import com.mini.money.entity.Blacklist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Blacklist, String> {

    boolean existsByToken(String token);
    Blacklist findByToken(String token);
}
