package com.ironhack.crudbankapp.controller.interfaces;

import com.ironhack.crudbankapp.model.User;

import java.util.List;

public interface UserControllerInterface {
    /**
     * Retrieves a list of all users
     *
     * @return list of all users
     */
    List<User> getUsers();

    /**
     * Saves a new user
     *
     * @param user the user to be saved
     * @return the saved user
     */
    User saveUser(User user);
}
