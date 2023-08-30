package com.ironhack.crudbankapp.repository;

import com.ironhack.crudbankapp.model.Deposit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepositRepository extends JpaRepository<Deposit, Integer> {
}
