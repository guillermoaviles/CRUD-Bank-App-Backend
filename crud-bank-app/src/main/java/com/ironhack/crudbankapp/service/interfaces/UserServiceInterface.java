package com.ironhack.crudbankapp.service.interfaces;

//import com.ironhack.crudbankapp.model.Role;
import com.ironhack.crudbankapp.model.CheckingAccount;
import com.ironhack.crudbankapp.model.User;

import java.util.List;

public interface UserServiceInterface {

    /**
     * This method is used to save a User entity to the database.
     *
     * @param user the User entity to be saved.
     * @return the saved User entity.
     */
    User saveUser(User user);

    /**
     * This method is used to save a Role entity to the database.
     *
     * @param role the Role entity to be saved.
     * @return the saved Role entity.
     */
//    Role saveRole(Role role);

    /**
     * This method is used to add a Role to a User.
     *
     * @param username the username of the User to which the Role is to be added.
     * @param roleName the name of the Role to be added.
     */
//    void addRoleToUser(String username, String roleName);

    /**
     * This method is used to retrieve a User from the database by its username.
     *
     * @param username the username of the User to be retrieved.
     * @return the retrieved User entity.
     */
    User getUser(String username);

    /**
     * This method is used to retrieve all User entities from the database.
     *
     * @return a List of all User entities.
     */
    List<User> getUsers();

    void addCheckingAccount(CheckingAccount checkingAccount, Integer userId);

    void addCheckingAccount(CheckingAccount checkingAccount, Long userId);
}
