package com.ironhack.crudbankapp.controller.impl;

import com.ironhack.crudbankapp.controller.interfaces.ITransactionController;
import com.ironhack.crudbankapp.dtos.AmountDTO;
import com.ironhack.crudbankapp.model.Transaction;
import com.ironhack.crudbankapp.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TransactionController implements ITransactionController {

    @Autowired
    TransactionRepository transactionRepository;

    public void generateTransaction(BigDecimal accountTotal, BigDecimal amount, Integer fromId, Integer destinationId) {
        AmountDTO amountDTO = new AmountDTO(amount);
        LocalDate transactionDate = LocalDate.now();
        Transaction newTransaction = new Transaction(transactionDate, accountTotal, amountDTO.getAmount(), fromId, destinationId);
        transactionRepository.save(newTransaction);
    }
}
