package com.ironhack.crudbankapp.service.interfaces;

import java.math.BigDecimal;

public interface ITransactionService {
    public void generateTransactionTicket(BigDecimal accountTotal, BigDecimal amount, String owner, Long ownerId, Integer accountId);
}
