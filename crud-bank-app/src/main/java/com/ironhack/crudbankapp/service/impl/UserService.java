package com.ironhack.crudbankapp.service.impl;

//import com.ironhack.crudbankapp.model.Role;
import com.ironhack.crudbankapp.model.CheckingAccount;
import com.ironhack.crudbankapp.model.InvestmentAccount;
import com.ironhack.crudbankapp.model.User;
//import com.ironhack.crudbankapp.repository.RoleRepository;
import com.ironhack.crudbankapp.repository.CheckingAccountRepository;
import com.ironhack.crudbankapp.repository.UserRepository;
import com.ironhack.crudbankapp.service.interfaces.UserServiceInterface;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements UserServiceInterface {
//    public class UserService implements UserServiceInterface, UserDetailsService {

    /**
     * Autowired UserRepository for database operations.
     */
    @Autowired
    private UserRepository userRepository;

    @Autowired
    CheckingAccountRepository checkingAccountRepository;

    /**
     * Autowired RoleRepository for database operations.
     */
//    @Autowired
//    private RoleRepository roleRepository;

    /**
     * Injects a bean of type PasswordEncoder into this class.
     * The bean is used for encoding passwords before storing them.
     */
//    @Autowired
//    private PasswordEncoder passwordEncoder;

    /**
     * Loads the user by its username
     *
     * @param username the username to search for
     * @return the UserDetails object that matches the given username
     * @throws UsernameNotFoundException if the user with the given username is not found
     */
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        // Retrieve user with the given username
//        User user = userRepository.findByUsername(username);
//        // Check if user exists
//        if (user == null) {
//            log.error("User not found in the database");
//            throw new UsernameNotFoundException("User not found in the database");
//        } else {
//            log.info("User found in the database: {}", username);
//            // Create a collection of SimpleGrantedAuthority objects from the user's roles
//            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
//            user.getRoles().forEach(role -> {
//                authorities.add(new SimpleGrantedAuthority(role.getName()));
//            });
//            // Return the user details, including the username, password, and authorities
//            return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
//        }
//    }

    /**
     * Saves a new user to the database
     *
     * @param user the user to be saved
     * @return the saved user
     */
    @Override
    public User saveUser(User user) {
        log.info("Saving new user {} to the database", user.getName());
        // Encode the user's password for security before saving
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    /**
     * Saves a new role to the database
     *
     * @param role the role to be saved
     * @return the saved role
     */
//    @Override
//    public Role saveRole(Role role) {
//        log.info("Saving new role {} to the database", role.getName());
//        return roleRepository.save(role);
//    }

    /**
     * Adds a role to the user with the given username
     *
     * @param username the username of the user to add the role to
     * @param roleName the name of the role to be added
     */
//    @Override
//    public void addRoleToUser(String username, String roleName) {
//        log.info("Adding role {} to user {}", roleName, username);
//
//        // Retrieve the user and role objects from the repository
//        User user = userRepository.findByUsername(username);
//        Role role = roleRepository.findByName(roleName);
//
//        // Add the role to the user's role collection
//        user.getRoles().add(role);
//
//        // Save the user to persist the changes
//        userRepository.save(user);
//    }

    /**
     * Retrieves the user with the given username
     *
     * @param username the username to search for
     * @return the user with the given username
     */
    @Override
    public User getUser(String username) {
        log.info("Fetching user {}", username);
        return userRepository.findByUsername(username);
    }

    /**
     * Retrieves all users from the database
     *
     * @return a list of all users
     */
    @Override
    public List<User> getUsers() {
        log.info("Fetching all users");
        return userRepository.findAll();
    }

    @Override
    public void addCheckingAccount(CheckingAccount checkingAccount, Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User ID #" + userId + " not found");
        User user = userOptional.get();
        List<CheckingAccount> updatedCheckingAccounts = user.getCheckingAccounts();
        updatedCheckingAccounts.add(checkingAccount);
        user.setCheckingAccounts(updatedCheckingAccounts);
        userRepository.save(user);
    }

    @Override
    public void addInvestmentAccount(InvestmentAccount investmentAccount, Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User ID #" + userId + " not found");
        User user = userOptional.get();
        List<InvestmentAccount> updatedInvestmentAccounts = user.getInvestmentAccounts();
        updatedInvestmentAccounts.add(investmentAccount);
        user.setInvestmentAccounts(updatedInvestmentAccounts);
        userRepository.save(user);
    }

    @Override
    public User getUserById(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User #" + userId + " not found");
        return userOptional.get();
    }
}
