package com.ironhack.crudbankapp.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Entity
public class Deposit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer depositId;
    private LocalDate depositDate;
    private LocalDate unlockDate;
    private BigDecimal amount;

    public Deposit() {
    }

    public Deposit(BigDecimal amount, LocalDate depositDate, LocalDate unlockDate) {
        this.amount = amount;
        this.depositDate = depositDate;
        this.unlockDate = unlockDate;

    }

    public Integer getDepositId() {
        return depositId;
    }

    public void setDepositId(Integer depositId) {
        this.depositId = depositId;
    }

    public LocalDate getDepositDate() {
        return depositDate;
    }

    public void setDepositDate(LocalDate depositDate) {
        this.depositDate = depositDate;
    }

    public LocalDate getUnlockDate() {
        return unlockDate;
    }

    public void setUnlockDate(LocalDate unlockDate) {
        this.unlockDate = unlockDate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Deposit{" +
                "depositId=" + depositId +
                ", depositDate=" + depositDate +
                ", unlockDate=" + unlockDate +
                ", amount=" + amount +
                '}';
    }
}
