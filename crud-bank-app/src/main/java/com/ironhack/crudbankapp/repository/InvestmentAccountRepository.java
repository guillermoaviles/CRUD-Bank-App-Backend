package com.ironhack.crudbankapp.repository;

import com.ironhack.crudbankapp.model.InvestmentAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvestmentAccountRepository extends JpaRepository<InvestmentAccount, Integer> {
    InvestmentAccount findInvestmentAccountByOwner(String owner);
    InvestmentAccount findInvestmentAccountByAccountNumber(Integer id);
}
