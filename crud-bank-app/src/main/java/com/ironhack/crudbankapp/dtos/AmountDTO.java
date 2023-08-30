package com.ironhack.crudbankapp.dtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import java.math.BigDecimal;

public class AmountDTO {
    @Max(value = 1000000, message = "The amount cannot be more than 1000000 dollars")
    @Min(value = 0, message = "The amount cannot be less than 0 dollars")
    private BigDecimal amount;

    public AmountDTO() {
    }

    public AmountDTO(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
