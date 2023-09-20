package com.ironhack.crudbankapp.controller.impl;

import com.ironhack.crudbankapp.controller.interfaces.ICheckingAccountController;
import com.ironhack.crudbankapp.dtos.AmountDTO;
import com.ironhack.crudbankapp.model.CheckingAccount;
import com.ironhack.crudbankapp.repository.CheckingAccountRepository;
import com.ironhack.crudbankapp.service.impl.CheckingAccountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CheckingAccountController implements ICheckingAccountController {

    @Autowired
    CheckingAccountRepository checkingAccountRepository;

    @Autowired
    CheckingAccountService checkingAccountService;


    // **************************************************** GET ***********************************************

    @GetMapping("/accounts/checking")
    @ResponseStatus(HttpStatus.OK)
    public List<CheckingAccount> getAllCheckingAccounts() {
        return checkingAccountRepository.findAll();
    }

    @GetMapping("/accounts/checking/{accountNumber}")
    public CheckingAccount getCheckingAccountByAccountNumber(@PathVariable Integer accountNumber) {
        return checkingAccountService.getCheckingAccountByAccountNumber(accountNumber);
    }

    @GetMapping("/accounts/checking/owner/{owner}")
    public CheckingAccount getCheckingAccountByOwner(@PathVariable String owner) {
        return checkingAccountRepository.findFirstCheckingAccountByOwner(owner);
    }

    // **************************************************  POST  ******************************************************

    @PostMapping("/accounts/checking")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveCheckingAccount(@RequestBody @Valid CheckingAccount checkingAccount) {
        checkingAccountRepository.save(checkingAccount);
    }

    // **************************************************  PUT  *******************************************************

    @PutMapping("/accounts/checking/{accountNumber}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateCheckingAccount(@RequestBody @Valid CheckingAccount checkingAccount, @PathVariable Integer accountNumber) {
        checkingAccountService.updateCheckingAccount(checkingAccount, accountNumber);
    }

    // *************************************************  PATCH  ******************************************************

    @PatchMapping("/accounts/checking/transfer/{fromId}/{destinationId}/{amount}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void transfer(@PathVariable Integer fromId, @PathVariable Integer destinationId, @PathVariable BigDecimal amount) {
        AmountDTO amountDTO = new AmountDTO(amount);
        checkingAccountService.transfer(fromId, destinationId, amountDTO.getAmount());
    }


    //  ***********************************************  DELETE  ******************************************************

    @DeleteMapping("/accounts/checking/{accountNumber}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCheckingAccount(@PathVariable Integer accountNumber) {
        checkingAccountService.deleteCheckingAccount(accountNumber);
    }
}
