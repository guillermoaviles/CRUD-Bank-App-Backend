package com.ironhack.crudbankapp.controller.impl;

import com.ironhack.crudbankapp.dtos.AmountDTO;
import com.ironhack.crudbankapp.model.CheckingAccount;
import com.ironhack.crudbankapp.model.InvestmentAccount;
import com.ironhack.crudbankapp.model.User;
import com.ironhack.crudbankapp.repository.CheckingAccountRepository;
import com.ironhack.crudbankapp.repository.TransactionRepository;
import com.ironhack.crudbankapp.repository.UserRepository;
import com.ironhack.crudbankapp.service.impl.CheckingAccountService;
import com.ironhack.crudbankapp.service.impl.InvestmentAccountService;
import com.ironhack.crudbankapp.service.interfaces.ITransactionService;
import com.ironhack.crudbankapp.service.interfaces.UserServiceInterface;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    /**
     * User service for accessing user data
     */
    @Autowired
    private UserServiceInterface userService;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private ITransactionService transactionService;

    @Autowired
    private CheckingAccountRepository checkingAccountRepository;

    @Autowired
    private CheckingAccountService checkingAccountService;

    @Autowired
    private InvestmentAccountService investmentAccountService;

    /**
     * Get a list of all users
     *
     * @return list of all users
     */
    @GetMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    public List<User> getUsers() {
        return userService.getUsers();
    }
    /**
     * Save a new user
     *
     * @param user the user to be saved
     */

    @GetMapping("/users/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public User getUserById(@PathVariable Long userId) {
        return userService.getUserById(userId);
    }

    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveUser(@RequestBody User user) {
        userService.saveUser(user);
    }



    @PostMapping("/users/addCheckingAccount/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void addCheckingAccount(@RequestBody @Valid CheckingAccount checkingAccount, @PathVariable Long userId) {
        userService.addCheckingAccount(checkingAccount, userId);
    }

    @PostMapping("/users/addInvestmentAccount/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void addInvestmentAccount(@RequestBody @Valid InvestmentAccount investmentAccount, @PathVariable Long userId) {
        userService.addInvestmentAccount(investmentAccount, userId);
    }

    @PatchMapping("/users/sendMoney/{fromId}/{destinationId}/{amount}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void sendMoney(@PathVariable Integer fromId, @PathVariable Integer destinationId, @PathVariable BigDecimal amount) {
        AmountDTO amountDTO = new AmountDTO(amount);
        checkingAccountService.transfer(fromId, destinationId, amountDTO.getAmount());
        transactionService.generateTransaction();
    }

    @PatchMapping("/users/withdraw/{accountNumber}/{amount}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void withdraw(@PathVariable Integer accountNumber, @PathVariable BigDecimal amount) {
        AmountDTO amountDTO = new AmountDTO(amount);
        investmentAccountService.withdraw(accountNumber, amountDTO.getAmount());
    }
}
