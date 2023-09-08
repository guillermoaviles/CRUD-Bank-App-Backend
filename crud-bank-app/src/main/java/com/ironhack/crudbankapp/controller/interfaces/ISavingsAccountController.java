package com.ironhack.crudbankapp.controller.interfaces;

import com.ironhack.crudbankapp.model.SavingsAccount;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;
import java.util.List;

public interface ISavingsAccountController {
    List<SavingsAccount> getAllSavingsAccounts();
    SavingsAccount getSavingsAccountByAccountNumber(@PathVariable Integer accountNumber);
    SavingsAccount getSavingsAccountByOwner(@PathVariable String owner);
    void saveSavingsAccount(@RequestBody @Valid SavingsAccount savingsAccount);
    void updateSavingsAccount(@RequestBody @Valid SavingsAccount savingsAccount, @PathVariable Integer accountNumber);
    void withdraw(@PathVariable Integer accountNumber, @PathVariable BigDecimal amount);
    void deleteSavingsAccount(@PathVariable Integer accountNumber);
}
