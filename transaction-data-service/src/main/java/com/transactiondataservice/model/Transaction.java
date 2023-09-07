package com.transactiondataservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
