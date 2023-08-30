package com.ironhack.crudbankapp.controller.impl;

import com.ironhack.crudbankapp.controller.interfaces.IInvestmentAccountController;
import com.ironhack.crudbankapp.dtos.AmountDTO;
import com.ironhack.crudbankapp.model.InvestmentAccount;
import com.ironhack.crudbankapp.repository.InvestmentAccountRepository;
import com.ironhack.crudbankapp.service.impl.InvestmentAccountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api")
public class InvestmentAccountController implements IInvestmentAccountController {

    @Autowired
    InvestmentAccountRepository investmentAccountRepository;

    @Autowired
    InvestmentAccountService investmentAccountService;

    // **************************************************  GET  *******************************************************
    @GetMapping("/accounts/investment")
    @ResponseStatus(HttpStatus.OK)
    public List<InvestmentAccount> getAllInvestmentAccounts() {
        return investmentAccountRepository.findAll();
    }

    @GetMapping("/accounts/investment/{accountNumber}")
    public InvestmentAccount getInvestmentAccountByAccountNumber(@PathVariable Integer accountNumber) {
        return investmentAccountService.getInvestmentAccountByAccountNumber(accountNumber);
    }

    @GetMapping("/accounts/investment/owner/{name}")
    public InvestmentAccount getInvestmentAccountByOwner(@PathVariable String owner) {
        return investmentAccountRepository.findInvestmentAccountByOwner(owner);
    }

    // **************************************************  POST  ******************************************************

    @PostMapping("/accounts/investment")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveAccount(@RequestBody @Valid InvestmentAccount investmentAccount) {
        investmentAccountRepository.save(investmentAccount);
    }

    //  ****************************************************  PUT  ****************************************************

    @PutMapping("/accounts/investment/{accountNumber}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateAccount(@RequestBody @Valid InvestmentAccount investmentAccount, @PathVariable Integer accountNumber) {
        investmentAccountService.updateInvestmentAccount(investmentAccount, accountNumber);
    }

    //  **************************************************  PATCH  ****************************************************

    @PatchMapping("/accounts/investment/withdraw/{accountNumber}/{amount}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void withdraw(@PathVariable Integer accountNumber, @PathVariable BigDecimal amount) {
        AmountDTO amountDTO = new AmountDTO(amount);
        investmentAccountService.withdraw(accountNumber, amountDTO.getAmount());
    }

    //  **************************************************  DELETE  ***************************************************

    @DeleteMapping("/accounts/investment/{accountNumber}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAccount(@PathVariable Integer accountNumber) {
        investmentAccountService.deleteInvestmentAccount(accountNumber);
    }

}
