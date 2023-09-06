package com.ironhack.crudbankapp.model;

import jakarta.persistence.*;
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

    private Long ownerId;

    private Integer accountId;

    public Transaction(LocalDate transactionDate, BigDecimal accountTotal, BigDecimal amount, String owner, Long ownerId, Integer accountId) {
        setTransactionDate(transactionDate);
        setAccountTotal(accountTotal);
        setAmount(amount);
        setOwner(owner);
        setOwnerId(ownerId);
        setAccountId(accountId);
    }

    public Transaction(LocalDate transactionDate, BigDecimal accountTotal, BigDecimal amount, Long fromOwnerId, Long toOwnerId, Integer fromId, Integer destinationId) {
    }
}
