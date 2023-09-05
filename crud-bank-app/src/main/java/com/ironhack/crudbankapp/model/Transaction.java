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
    private Integer transactionId;

    private LocalDate transactionDate;

    private BigDecimal accountTotal;

    private BigDecimal amount;

    private Integer fromId;

    private Integer destinationId;

    public Transaction(LocalDate transactionDate, BigDecimal accountTotal, BigDecimal amount, Integer fromId, Integer destinationId) {
    }
}
