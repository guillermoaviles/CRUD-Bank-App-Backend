package com.ironhack.crudbankapp.repository;

import com.ironhack.crudbankapp.model.InvestmentAccount;
import com.ironhack.crudbankapp.model.SavingsAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface SavingsAccountRepository extends JpaRepository<SavingsAccount, Integer> {
    SavingsAccount findSavingsAccountByOwner(String owner);
    SavingsAccount findSavingsAccountByAccountNumber(Integer id);
}

