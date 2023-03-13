package com.mini.money.repository;

import com.mini.money.entity.Cart;
import com.mini.money.entity.Customer;
import com.mini.money.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    void deleteByCustomerAndLoan(Customer customer, Loan loan);

    List<Cart> findAllByCustomer(Customer customer);

    boolean existsByCustomerAndLoan(Customer customer, Loan loan);

    Cart findFirstByCustomerOrderByIdAsc(Customer customer);

    @Query(value = "select c.loan from Cart c where c.customer = :customer")
    List<Loan> findCartList(@Param("customer") Customer customer);

}
