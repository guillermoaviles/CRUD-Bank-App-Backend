package com.transactiondataservice.service.impl;

import com.transactiondataservice.dtos.AmountDTO;
import com.transactiondataservice.model.Transaction;
import com.transactiondataservice.repository.TransactionRepository;
import com.transactiondataservice.service.interfaces.ITransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class TransactionService implements ITransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    public void generateTransactionTicket(BigDecimal accountTotal, BigDecimal amount, String owner, String counterparty, Long ownerId, Integer accountId) {
        AmountDTO amountDTO = new AmountDTO(amount);
        LocalDate transactionDate = LocalDate.now();
        Transaction newTransaction = new Transaction(transactionDate, accountTotal, amountDTO.getAmount(), owner, counterparty, ownerId, accountId);
        transactionRepository.save(newTransaction);
    }
}
