package com.norbcorp.hungary.springboot.demo.backend.model;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
public class Balance {
    private Integer balance = 0;

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }
}
