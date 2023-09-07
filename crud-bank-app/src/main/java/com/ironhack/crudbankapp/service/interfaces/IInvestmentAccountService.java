package com.ironhack.crudbankapp.service.interfaces;

import com.ironhack.crudbankapp.model.InvestmentAccount;

import java.math.BigDecimal;

public interface IInvestmentAccountService {

    InvestmentAccount getInvestmentAccountByAccountNumber(Integer accountNumber);

    void updateInvestmentAccount(InvestmentAccount investmentAccount, Integer accountNumber);

    void deleteInvestmentAccount(Integer accountNumber);

    void withdraw(Integer accountNumber, BigDecimal amount);

    BigDecimal calculateAvailableBalance(Integer accountNumber);

    BigDecimal calculateTotalBalanceWithYield(Integer accountNumber);
}
