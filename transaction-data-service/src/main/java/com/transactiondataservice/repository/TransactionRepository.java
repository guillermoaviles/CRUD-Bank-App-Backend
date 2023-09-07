package com.transactiondataservice.repository;

import com.transactiondataservice.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    List<Transaction> findByOwnerIdOrderByTransactionDate(Long ownerId);
    List<Transaction> findByAccountIdOrderByTransactionDate(Integer accountId);
}
