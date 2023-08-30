package com.ironhack.crudbankapp.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.math.MathContext;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Entity
@PrimaryKeyJoinColumn(name = "accountNumber")
public class InvestmentAccount extends Account{
    private BigDecimal apy;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "investmentAccount_id")
    private List<Deposit> deposits = new ArrayList<>();
    public InvestmentAccount() {
        super();
        this.apy = BigDecimal.valueOf(10);
    }

    public InvestmentAccount(String owner, BigDecimal apy) {
        super(owner);
        this.apy = BigDecimal.valueOf(10);
    }

    public BigDecimal getAPY() {
        return apy;
    }

    public void setAPY(BigDecimal apy) {
        this.apy = apy;
    }

    public List<Deposit> getDeposits() {
        return deposits;
    }

    public void setDeposits(List<Deposit> deposits) {
        this.deposits = deposits;
    }

    @Override
    public BigDecimal getBalance() {
        return super.getBalance();
    }

    @Override
    public void setBalance(BigDecimal balance) {
        super.setBalance(balance);
    }

    public void withdraw(BigDecimal amount) {
        BigDecimal availableBalance = calculateAvailableBalance();

        if (amount.compareTo(availableBalance) <= 0) {
            setBalance(getBalance().subtract(amount));
            // Logic to liquidate deposits
            liquidateDeposits(amount);
        } else {
            throw new IllegalArgumentException("Insufficient funds");
        }
    }

    private BigDecimal calculateAvailableBalance() {
        LocalDate currentDate = LocalDate.now();
        BigDecimal availableBalance = BigDecimal.valueOf(0);

        for (Deposit deposit : deposits) {
            if (currentDate.isAfter(deposit.getUnlockDate())) {
                BigDecimal depositAmountWithInterest = calculateAmountWithInterest(deposit);
                availableBalance = availableBalance.add(depositAmountWithInterest);
            }
        }
        return availableBalance;
    }

    private void liquidateDeposits(BigDecimal withdrawalAmount) {
        LocalDate currentDate = LocalDate.now();

        List<Deposit> depositsToDelete = new ArrayList<>();
        for (Deposit deposit : deposits) {
            if (currentDate.isAfter(deposit.getUnlockDate())) {
                BigDecimal depositAmount = calculateAmountWithInterest(deposit);
                if (withdrawalAmount.compareTo(depositAmount) >= 0) {
                    withdrawalAmount = withdrawalAmount.subtract(depositAmount);
                    setBalance(getBalance().subtract(depositAmount));
                    depositsToDelete.add(deposit);
                } else if (withdrawalAmount.compareTo(depositAmount) < 0) {
                    deposit.setAmount(depositAmount.subtract(withdrawalAmount));
                    setBalance(deposit.getAmount());
                    withdrawalAmount = BigDecimal.valueOf(0);
                }
            }
        }
        for (Deposit deposit : depositsToDelete) {
            deposits.remove(deposit);
        }
    }

    private BigDecimal calculateAmountWithInterest(Deposit deposit) {
        BigDecimal amount = deposit.getAmount();
        int daysBetween = (int) ChronoUnit.DAYS.between(deposit.getDepositDate(), deposit.getUnlockDate());

        // Calculate the compound interest formula: amount * (1 + APY)^days
        BigDecimal compoundFactor = BigDecimal.ONE.add(apy.divide(BigDecimal.valueOf(100), MathContext.DECIMAL128).divide(BigDecimal.valueOf(365), MathContext.DECIMAL128)).pow(daysBetween);
        return amount.multiply(compoundFactor);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
