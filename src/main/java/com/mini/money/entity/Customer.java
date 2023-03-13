package com.mini.money.entity;


import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "customer")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@DynamicInsert
@DynamicUpdate
public class Customer {

    @Id
    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "role")
    @ColumnDefault("'ROLE_USER'")
    private String role;

    @Column(name="age")
    private Integer age;

    @Column(name="address")
    private String address;

    @Column(name="job")
    private String job;

    @Column(name="bank")
    private String bank;

    @Column(name="crdt_grade")
    private Double crdtGrade;

    @Column(name="income")
    private String income;

    public void update(final String password, final String phone) {
        this.password = password;
        this.phone = phone;
    }
}
