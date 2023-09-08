package com.ironhack.crudbankapp.controller.interfaces;

import com.ironhack.crudbankapp.model.Transaction;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface ITransactionController {
    List<Transaction> getTransactions();
    List<Transaction> getTransactionsByOwner(@PathVariable Long ownerId);
    List<Transaction> getTransactionsByAccount(@PathVariable Integer account);
}
