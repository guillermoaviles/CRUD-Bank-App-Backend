package com.transactiondataservice.controller.impl;

import com.transactiondataservice.controller.interfaces.ITransactionController;
import com.transactiondataservice.dtos.AmountDTO;
import com.transactiondataservice.model.Transaction;
import com.transactiondataservice.repository.TransactionRepository;
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

    @PostMapping("/transactions/generateTicket/{accountTotal}/{amount}/{owner}/{counterparty}/{ownerId}/{accountId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void generateTransactionTicket(@PathVariable BigDecimal accountTotal, @PathVariable BigDecimal amount, @PathVariable String owner, @PathVariable String counterparty, @PathVariable Long ownerId, @PathVariable Integer accountId) {
        AmountDTO amountDTO = new AmountDTO(amount);
        LocalDate transactionDate = LocalDate.now();
        Transaction newTransaction = new Transaction(transactionDate, accountTotal, amountDTO.getAmount(), owner, counterparty, ownerId, accountId);
        transactionRepository.save(newTransaction);
    }
}
