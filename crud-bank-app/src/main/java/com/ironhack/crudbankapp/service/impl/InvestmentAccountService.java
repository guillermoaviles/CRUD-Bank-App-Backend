package com.ironhack.crudbankapp.service.impl;

import com.ironhack.crudbankapp.client.TransactionDataService;
import com.ironhack.crudbankapp.model.CheckingAccount;
import com.ironhack.crudbankapp.model.InvestmentAccount;
import com.ironhack.crudbankapp.repository.CheckingAccountRepository;
import com.ironhack.crudbankapp.repository.InvestmentAccountRepository;
import com.ironhack.crudbankapp.repository.UserRepository;
import com.ironhack.crudbankapp.service.interfaces.IInvestmentAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class InvestmentAccountService implements IInvestmentAccountService {

    @Autowired
    InvestmentAccountRepository investmentAccountRepository;

    @Autowired
    CheckingAccountRepository checkingAccountRepository;

//    @Autowired
//    ITransactionService transactionService;

    @Autowired
    TransactionDataService transactionDataService;

    @Autowired
    UserRepository userRepository;

    @Override
    public InvestmentAccount getInvestmentAccountByAccountNumber(Integer accountNumber) {
        Optional<InvestmentAccount> investmentAccountOptional = investmentAccountRepository.findById(accountNumber);
        if (investmentAccountOptional.isEmpty()) return null;
        return investmentAccountOptional.get();
    }

    @Override
    public void updateInvestmentAccount(InvestmentAccount investmentAccount, Integer accountNumber) {
        Optional<InvestmentAccount> investmentAccountOptional = investmentAccountRepository.findById(accountNumber);
        if (investmentAccountOptional.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account #" + accountNumber + " not found");
        investmentAccount.setAccountNumber(accountNumber);
        investmentAccountRepository.save(investmentAccount);
    }

    @Override
    public void withdraw(Integer accountNumber, BigDecimal amount) {
        Optional<InvestmentAccount> investmentAccountOptional = investmentAccountRepository.findById(accountNumber);
        if (investmentAccountOptional.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account #" + accountNumber + " not found");
        Optional<CheckingAccount> checkingAccountOptional = Optional.ofNullable(checkingAccountRepository.findFirstCheckingAccountByOwner(investmentAccountOptional.get().getOwner()));
        if (checkingAccountOptional.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account #" + accountNumber + " not found");

        investmentAccountOptional.get().withdraw(amount);
        checkingAccountOptional.get().setBalance(checkingAccountOptional.get().getBalance().add(amount));

        investmentAccountRepository.save(investmentAccountOptional.get());
        transactionDataService.generateTransactionTicket(
                investmentAccountRepository.findInvestmentAccountByAccountNumber(accountNumber).getBalance(),
                amount.multiply(BigDecimal.valueOf(-1)),
                investmentAccountRepository.findInvestmentAccountByAccountNumber(accountNumber).getOwner(),
                checkingAccountRepository.findFirstCheckingAccountByOwner(investmentAccountOptional.get().getOwner()).getOwner(),
                userRepository.findByName(investmentAccountRepository.findInvestmentAccountByAccountNumber(accountNumber).getOwner()).getId(),
                accountNumber
        );

        checkingAccountRepository.save(checkingAccountOptional.get());
        transactionDataService.generateTransactionTicket(
                checkingAccountRepository.findCheckingAccountByAccountNumber(checkingAccountOptional.get().getAccountNumber()).getBalance(),
                amount,
                checkingAccountRepository.findFirstCheckingAccountByOwner(investmentAccountOptional.get().getOwner()).getOwner(),
                investmentAccountRepository.findInvestmentAccountByAccountNumber(accountNumber).getOwner(),
                userRepository.findByName(checkingAccountRepository.findFirstCheckingAccountByOwner(investmentAccountOptional.get().getOwner()).getOwner()).getId(),
                checkingAccountOptional.get().getAccountNumber()
        );
    }

    @Override
    public BigDecimal calculateAvailableBalance(Integer accountNumber) {
        return investmentAccountRepository.findInvestmentAccountByAccountNumber(accountNumber).calculateAvailableBalance();
    }

    @Override
    public BigDecimal calculateTotalBalanceWithYield(Integer accountNumber) {
        return investmentAccountRepository.findInvestmentAccountByAccountNumber(accountNumber).calculateTotalBalanceWithYield();
    }

    @Override
    public void deleteInvestmentAccount(Integer accountNumber) {
        Optional<InvestmentAccount> investmentAccountOptional = investmentAccountRepository.findById(accountNumber);
        if (investmentAccountOptional.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account #" + accountNumber + " not found");
        investmentAccountRepository.deleteById(accountNumber);
    }
}
