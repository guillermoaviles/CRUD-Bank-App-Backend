package com.ironhack.crudbankapp.service.interfaces;

import com.ironhack.crudbankapp.model.SavingsAccount;

import java.math.BigDecimal;

public interface ISavingsAccountService {
    SavingsAccount getSavingsAccountByAccountNumber(Integer accountNumber);

    void updateSavingsAccount(SavingsAccount savingsAccount, Integer accountNumber);

    void withdraw(Integer accountNumber, BigDecimal amount);

    void deleteSavingsAccount(Integer accountNumber);
}
