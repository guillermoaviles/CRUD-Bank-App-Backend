package com.ironhack.crudbankapp.controller.impl;

import com.ironhack.crudbankapp.controller.interfaces.ICheckingAccountController;
import com.ironhack.crudbankapp.dtos.AmountDTO;
import com.ironhack.crudbankapp.model.CheckingAccount;
import com.ironhack.crudbankapp.model.User;
import com.ironhack.crudbankapp.repository.CheckingAccountRepository;
import com.ironhack.crudbankapp.service.impl.CheckingAccountService;
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
    private CheckingAccountRepository checkingAccountRepository;

    @Autowired
    private CheckingAccountService checkingAccountService;

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

    @PatchMapping("/users/transfer/{fromId}/{destinationId}/{amount}")
    @ResponseStatus(HttpStatus.CREATED)
    public void sendMoney(@PathVariable Integer fromId, @PathVariable Integer destinationId, @PathVariable BigDecimal amount) {
        AmountDTO amountDTO = new AmountDTO(amount);
        checkingAccountService.transfer(fromId, destinationId, amountDTO.getAmount());
    }
}
