package com.mini.money.repository;

import com.mini.money.entity.Customer;
import com.mini.money.entity.Favor;
import com.mini.money.entity.Loan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavorRepository extends JpaRepository<Favor, Long> {

    void deleteByCustomerAndLoan(Customer customer, Loan loan);

    List<Favor> findAllByCustomer(Customer customer);

    @Query(value = "select f.loan from Favor f group by f.loan order by count(f.loan) desc")
    Page<Loan> findPopularData(Pageable pageable);

    boolean existsByCustomerAndLoan(Customer customer, Loan loan);

    Favor findFirstByCustomerOrderByIdAsc(Customer customer);

    @Query(value = "select f.loan from Favor f where f.customer = :customer")
    List<Loan> findFavorList(@Param("customer") Customer customer);

}
