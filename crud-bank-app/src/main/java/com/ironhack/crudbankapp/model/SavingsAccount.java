package com.ironhack.crudbankapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;

import java.math.BigDecimal;

@Entity
@PrimaryKeyJoinColumn(name = "accountNumber")
public class SavingsAccount extends Account {
    public SavingsAccount() {
        super();
    }

    public SavingsAccount(String owner) {
        super(owner);
    }

    @Override
    public BigDecimal getBalance() {
        return super.getBalance();
    }

    @Override
    public void setBalance(BigDecimal balance) {
        super.setBalance(balance);
    }

    public void withdraw(BigDecimal amount) {
        BigDecimal availableBalance = getBalance();

        if (amount.compareTo(availableBalance) <= 0) {
            setBalance(getBalance().subtract(amount));
        } else {
            throw new IllegalArgumentException("Insufficient funds");
        }
    }
}

