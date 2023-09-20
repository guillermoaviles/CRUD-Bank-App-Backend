package com.ironhack.crudbankapp.service.impl;

import com.ironhack.crudbankapp.client.TransactionDataService;
import com.ironhack.crudbankapp.model.CheckingAccount;
import com.ironhack.crudbankapp.model.SavingsAccount;
import com.ironhack.crudbankapp.repository.CheckingAccountRepository;
import com.ironhack.crudbankapp.repository.SavingsAccountRepository;
import com.ironhack.crudbankapp.repository.UserRepository;
import com.ironhack.crudbankapp.service.interfaces.ISavingsAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class SavingsAccountService implements ISavingsAccountService {

    @Autowired
    SavingsAccountRepository savingsAccountRepository;

    @Autowired
    CheckingAccountRepository checkingAccountRepository;

    @Autowired
    TransactionDataService transactionDataService;

    @Autowired
    UserRepository userRepository;

    @Override
    public SavingsAccount getSavingsAccountByAccountNumber(Integer accountNumber) {
        Optional<SavingsAccount> savingsAccountOptional = savingsAccountRepository.findById(accountNumber);
        if (savingsAccountOptional.isEmpty()) return null;
        return savingsAccountOptional.get();
    }

    @Override
    public void updateSavingsAccount(SavingsAccount savingsAccount, Integer accountNumber) {
        Optional<SavingsAccount> savingsAccountOptional = savingsAccountRepository.findById(accountNumber);
        if (savingsAccountOptional.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account #" + accountNumber + " not found");
        savingsAccount.setAccountNumber(accountNumber);
        savingsAccountRepository.save(savingsAccount);
    }

    @Override
    public void withdraw(Integer accountNumber, BigDecimal amount) {
        Optional<SavingsAccount> savingsAccountOptional = savingsAccountRepository.findById(accountNumber);
        if (savingsAccountOptional.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account #" + accountNumber + " not found");
        Optional<CheckingAccount> checkingAccountOptional = Optional.ofNullable(checkingAccountRepository.findFirstCheckingAccountByOwner(savingsAccountOptional.get().getOwner()));
        if (checkingAccountOptional.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account #" + accountNumber + " not found");

        savingsAccountOptional.get().withdraw(amount);
        checkingAccountOptional.get().setBalance(checkingAccountOptional.get().getBalance().add(amount));

        savingsAccountRepository.save(savingsAccountOptional.get());
        transactionDataService.generateTransactionTicket(
                savingsAccountRepository.findSavingsAccountByAccountNumber(accountNumber).getBalance(),
                amount.multiply(BigDecimal.valueOf(-1)),
                savingsAccountRepository.findSavingsAccountByAccountNumber(accountNumber).getOwner(),
                checkingAccountRepository.findFirstCheckingAccountByOwner(checkingAccountOptional.get().getOwner()).getOwner(),
                userRepository.findByName(savingsAccountRepository.findSavingsAccountByAccountNumber(accountNumber).getOwner()).getId(),
                accountNumber
        );

        checkingAccountRepository.save(checkingAccountOptional.get());
        transactionDataService.generateTransactionTicket(
                checkingAccountRepository.findCheckingAccountByAccountNumber(checkingAccountOptional.get().getAccountNumber()).getBalance(),
                amount,
                checkingAccountRepository.findFirstCheckingAccountByOwner(savingsAccountOptional.get().getOwner()).getOwner(),
                savingsAccountRepository.findSavingsAccountByAccountNumber(accountNumber).getOwner(),
                userRepository.findByName(checkingAccountRepository.findFirstCheckingAccountByOwner(savingsAccountOptional.get().getOwner()).getOwner()).getId(),
                checkingAccountOptional.get().getAccountNumber()
        );
    }

    @Override
    public void deleteSavingsAccount(Integer accountNumber) {
        Optional<SavingsAccount> savingsAccountOptional = savingsAccountRepository.findById(accountNumber);
        if (savingsAccountOptional.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account number " + accountNumber + " not found");
        savingsAccountRepository.deleteById(accountNumber);
    }
}
