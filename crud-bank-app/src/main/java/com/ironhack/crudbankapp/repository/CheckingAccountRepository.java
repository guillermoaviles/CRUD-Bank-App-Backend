package com.ironhack.crudbankapp.repository;

import com.ironhack.crudbankapp.model.CheckingAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CheckingAccountRepository extends JpaRepository<CheckingAccount, Integer> {
    CheckingAccount findCheckingAccountByOwner(String owner);
}
