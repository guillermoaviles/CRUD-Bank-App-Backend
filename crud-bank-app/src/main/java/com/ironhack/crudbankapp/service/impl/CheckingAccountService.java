package com.ironhack.crudbankapp.service.impl;

import com.ironhack.crudbankapp.model.CheckingAccount;
import com.ironhack.crudbankapp.model.Deposit;
import com.ironhack.crudbankapp.model.InvestmentAccount;
import com.ironhack.crudbankapp.repository.CheckingAccountRepository;
import com.ironhack.crudbankapp.repository.DepositRepository;
import com.ironhack.crudbankapp.repository.InvestmentAccountRepository;
import com.ironhack.crudbankapp.service.interfaces.ICheckingAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class CheckingAccountService implements ICheckingAccountService {

    @Autowired
    CheckingAccountRepository checkingAccountRepository;

    @Autowired
    InvestmentAccountRepository investmentAccountRepository;

    @Autowired
    DepositRepository depositRepository;

    @Override
    public CheckingAccount getCheckingAccountByAccountNumber(Integer accountNumber) {
        Optional<CheckingAccount> checkingAccountOptional = checkingAccountRepository.findById(accountNumber);
        if (checkingAccountOptional.isEmpty()) return null;
        return checkingAccountOptional.get();
    }

    @Override
    public void updateCheckingAccount(CheckingAccount checkingAccount, Integer accountNumber) {
        Optional<CheckingAccount> checkingAccountOptional = checkingAccountRepository.findById(accountNumber);
        if (checkingAccountOptional.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account #" + accountNumber + " not found");
        checkingAccount.setAccountNumber(accountNumber);
        checkingAccountRepository.save(checkingAccount);
    }

    @Override
    public void transfer(Integer fromId, Integer destinationId, BigDecimal amount) {
        Optional<CheckingAccount> fromCheckingAccountOptional = checkingAccountRepository.findById(fromId);
        if (fromCheckingAccountOptional.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account #" + fromId + " not found");
        CheckingAccount fromCheckingAccount = fromCheckingAccountOptional.get();
        if (amount.compareTo(fromCheckingAccount.getBalance()) > 0) {
            throw new IllegalArgumentException("Not enough funds to cover transfer");
        }

        Optional<CheckingAccount> destinationCheckingAccountOptional = checkingAccountRepository.findById(destinationId);
        Optional<InvestmentAccount> destinationInvestmentAccountOptional = investmentAccountRepository.findById(destinationId);
        if (destinationCheckingAccountOptional.isPresent()) {

            // Debit funds from origin checking account
            fromCheckingAccount.setBalance(fromCheckingAccount.getBalance().subtract(amount));
            // Credit funds to destination checking account
            destinationCheckingAccountOptional.get().setBalance(destinationCheckingAccountOptional.get().getBalance().add(amount));

            checkingAccountRepository.save(destinationCheckingAccountOptional.get());
            checkingAccountRepository.save(fromCheckingAccount);
        } else if (destinationInvestmentAccountOptional.isPresent()) {

            // Debit funds from origin checking account
            fromCheckingAccount.setBalance(fromCheckingAccount.getBalance().subtract(amount));
            // Credit funds to destination checking account
            deposit(amount, destinationInvestmentAccountOptional.get());

            investmentAccountRepository.save(destinationInvestmentAccountOptional.get());
            checkingAccountRepository.save(fromCheckingAccount);
        } else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account #" + destinationId + " not found");
    }

    public void deposit(BigDecimal amount, InvestmentAccount investmentAccount) {
        LocalDate depositDate = LocalDate.now();
        LocalDate unlockDate = depositDate.plusDays(2); // Adjust unlock period as needed

        Deposit deposit = new Deposit(amount, depositDate, unlockDate);
        depositRepository.save(deposit);
        List<Deposit> updatedDeposits = investmentAccount.getDeposits();
        updatedDeposits.add(deposit);
        investmentAccount.setDeposits(updatedDeposits);
        investmentAccount.setBalance(investmentAccount.getBalance().add(amount));

    }

    @Override
    public void deleteCheckingAccount(Integer accountNumber) {
        Optional<CheckingAccount> checkingAccountOptional = checkingAccountRepository.findById(accountNumber);
        if (checkingAccountOptional.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account number " + accountNumber + " not found");
        checkingAccountRepository.deleteById(accountNumber);
    }
}
