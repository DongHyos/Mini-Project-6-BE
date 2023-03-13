package com.mini.money.repository;

import com.mini.money.entity.Customer;
import com.mini.money.entity.Loan;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@SpringBootTest
class FavorRepositoryTest {

    @Autowired
    FavorRepository favorRepository;

    @Autowired
    CustomerRepository customerRepository;

//    @Test
//    void name() {
//        String email = "test@test.com";
//        Customer customer = customerRepository.findByEmail(email);
//        System.out.println(favorRepository.oldestFavorByCustomer(customer));
//    }


    @Test
    void name() {
        Pageable pageable = PageRequest.of(0, 10);

        Page<Loan> list = favorRepository.findPopularData(pageable);
        System.out.println(list);
    }
}