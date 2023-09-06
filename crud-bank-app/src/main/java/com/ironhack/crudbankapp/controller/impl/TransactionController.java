package com.ironhack.crudbankapp.controller.impl;

import com.ironhack.crudbankapp.controller.interfaces.ITransactionController;
import com.ironhack.crudbankapp.dtos.AmountDTO;
import com.ironhack.crudbankapp.model.Transaction;
import com.ironhack.crudbankapp.model.User;
import com.ironhack.crudbankapp.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api")
public class TransactionController implements ITransactionController {

    @Autowired
    TransactionRepository transactionRepository;

    @GetMapping("/transactions/all")
    @ResponseStatus(HttpStatus.OK)
    public List<Transaction> getTransactions() {
        return transactionRepository.findAll();
    }
    @GetMapping("/transactions/byOwner/{ownerId}")
    @ResponseStatus(HttpStatus.OK)
    public List<Transaction> getTransactionsByOwner(@PathVariable Long ownerId) {
        return transactionRepository.findByOwnerIdOrderByTransactionDate(ownerId);
    }
    @GetMapping("/transactions/byAccount/{account}")
    @ResponseStatus(HttpStatus.OK)
    public List<Transaction> getTransactionsByAccount(@PathVariable Integer account) {
        return transactionRepository.findByAccountIdOrderByTransactionDate(account);
    }

    public void generateTransactionTicket(BigDecimal accountTotal, BigDecimal amount, String owner, Long ownerId, Integer accountId) {
        AmountDTO amountDTO = new AmountDTO(amount);
        LocalDate transactionDate = LocalDate.now();
        Transaction newTransaction = new Transaction(transactionDate, accountTotal, amountDTO.getAmount(), owner, ownerId, accountId);
        transactionRepository.save(newTransaction);
    }
}
