package com.ironhack.crudbankapp.service.interfaces;

import java.math.BigDecimal;

public interface ITransactionService {
    public void generateTransaction(BigDecimal accountTotal, BigDecimal amount, Integer fromId, Integer destinationId);
}
