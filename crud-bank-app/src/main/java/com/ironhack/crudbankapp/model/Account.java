package com.ironhack.crudbankapp.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Inheritance(strategy= InheritanceType.JOINED)
public abstract class Account {

    @Id
    @SequenceGenerator(name = "mySeqGen", sequenceName = "mySeq", initialValue = 100000, allocationSize = 1)
    @GeneratedValue(generator = "mySeqGen")
    private Integer accountNumber;
//    @NotEmpty
    private String owner;
    @Column(columnDefinition = "DECIMAL(5,2)")
    private BigDecimal balance;

    public Account() {
        this.balance = new BigDecimal(0);
    }

    public Account(String owner) {
        this.owner = owner;
        this.balance = new BigDecimal(0);
    }

    public Integer getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Integer accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountNumber=" + accountNumber +
                ", owner='" + owner + '\'' +
                ", balance=" + balance +
                '}';
    }
}
