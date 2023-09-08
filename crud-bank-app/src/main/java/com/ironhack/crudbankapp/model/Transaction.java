package com.ironhack.crudbankapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    private Integer id;

    private LocalDate transactionDate;

    private BigDecimal accountTotal;

    private BigDecimal amount;

    private String owner;

    private String counterparty;

    private Long ownerId;

    private Integer accountId;

    public Transaction(LocalDate transactionDate, BigDecimal accountTotal, BigDecimal amount, String owner, String counterparty, Long ownerId, Integer accountId) {
        setTransactionDate(transactionDate);
        setAccountTotal(accountTotal);
        setAmount(amount);
        setOwner(owner);
        setCounterparty(counterparty);
        setOwnerId(ownerId);
        setAccountId(accountId);
    }
}
