package com.ironhack.crudbankapp.controller.impl;

import com.ironhack.crudbankapp.client.TransactionDataService;
import com.ironhack.crudbankapp.controller.interfaces.ITransactionController;
import com.ironhack.crudbankapp.dtos.AmountDTO;
import com.ironhack.crudbankapp.model.Transaction;
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
    TransactionDataService transactionDataService;

    @GetMapping("/transactions/all")
    @ResponseStatus(HttpStatus.OK)
    public List<Transaction> getTransactions() {
        return transactionDataService.getTransactions();
    }
    @GetMapping("/transactions/byOwner/{ownerId}")
    @ResponseStatus(HttpStatus.OK)
    public List<Transaction> getTransactionsByOwner(@PathVariable Long ownerId) {
        return transactionDataService.getTransactionsByOwner(ownerId);
    }
    @GetMapping("/transactions/byAccount/{account}")
    @ResponseStatus(HttpStatus.OK)
    public List<Transaction> getTransactionsByAccount(@PathVariable Integer account) {
        return transactionDataService.getTransactionsByAccount(account);
    }
}