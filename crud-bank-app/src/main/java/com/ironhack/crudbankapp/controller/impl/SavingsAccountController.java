package com.ironhack.crudbankapp.controller.impl;

import com.ironhack.crudbankapp.controller.interfaces.ISavingsAccountController;
import com.ironhack.crudbankapp.dtos.AmountDTO;
import com.ironhack.crudbankapp.model.SavingsAccount;
import com.ironhack.crudbankapp.repository.SavingsAccountRepository;
import com.ironhack.crudbankapp.service.impl.SavingsAccountService;
import com.ironhack.crudbankapp.service.interfaces.ISavingsAccountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api")
public class SavingsAccountController implements ISavingsAccountController {

    @Autowired
    SavingsAccountRepository savingsAccountRepository;
    @Autowired
    SavingsAccountService savingsAccountService;

    @GetMapping("/accounts/savings")
    @ResponseStatus(HttpStatus.OK)
    public List<SavingsAccount> getAllSavingsAccounts() {
        return savingsAccountRepository.findAll();
    }

    @GetMapping("/accounts/savings/{accountNumber}")
    public SavingsAccount getSavingsAccountByAccountNumber(@PathVariable Integer accountNumber) {
        return savingsAccountService.getSavingsAccountByAccountNumber(accountNumber);
    }

    @GetMapping("/accounts/savings/owner/{owner}")
    public SavingsAccount getSavingsAccountByOwner(@PathVariable String owner) {
        return savingsAccountRepository.findSavingsAccountByOwner(owner);
    }

    @PostMapping("/accounts/savings")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveSavingsAccount(@RequestBody @Valid SavingsAccount savingsAccount) {
        savingsAccountRepository.save(savingsAccount);
    }

    @PutMapping("/accounts/savings/{accountNumber}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateSavingsAccount(@RequestBody @Valid SavingsAccount savingsAccount, @PathVariable Integer accountNumber) {
        savingsAccountService.updateSavingsAccount(savingsAccount, accountNumber);
    }

    @PatchMapping("/accounts/savings/withdraw/{accountNumber}/{amount}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void withdraw(@PathVariable Integer accountNumber, @PathVariable BigDecimal amount) {
        AmountDTO amountDTO = new AmountDTO(amount);
        savingsAccountService.withdraw(accountNumber, amountDTO.getAmount());
    }

    @DeleteMapping("/accounts/savings/{accountNumber}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSavingsAccount(Integer accountNumber) {
        savingsAccountService.deleteSavingsAccount(accountNumber);
    }
}
