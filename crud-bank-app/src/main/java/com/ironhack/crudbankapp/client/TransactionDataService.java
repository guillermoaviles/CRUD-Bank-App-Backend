package com.ironhack.crudbankapp.client;

import com.ironhack.crudbankapp.model.Transaction;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.math.BigDecimal;
import java.util.List;

@FeignClient("transaction-data-service")
public interface TransactionDataService {
    @GetMapping("/api/transactions/all")
    @ResponseStatus(HttpStatus.OK)
    List<Transaction> getTransactions();

    @GetMapping("/api/transactions/byOwner/{ownerId}")
    @ResponseStatus(HttpStatus.OK)
    List<Transaction> getTransactionsByOwner(@PathVariable Long ownerId);

    @GetMapping("/api/transactions/byAccount/{account}")
    @ResponseStatus(HttpStatus.OK)
    List<Transaction> getTransactionsByAccount(@PathVariable Integer account);

    @PostMapping("/api/transactions/generateTicket/{accountTotal}/{amount}/{owner}/{counterparty}/{ownerId}/{accountId}")
    @ResponseStatus(HttpStatus.CREATED)
    void generateTransactionTicket(@PathVariable BigDecimal accountTotal, @PathVariable BigDecimal amount, @PathVariable String owner, @PathVariable String counterparty, @PathVariable Long ownerId, @PathVariable Integer accountId);
}
