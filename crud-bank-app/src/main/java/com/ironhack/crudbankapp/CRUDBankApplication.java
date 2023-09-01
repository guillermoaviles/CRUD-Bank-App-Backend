package com.ironhack.crudbankapp;

//import com.ironhack.crudbankapp.model.Role;
//import com.ironhack.crudbankapp.model.User;
//import com.ironhack.crudbankapp.service.impl.UserService;
//import com.ironhack.crudbankapp.service.interfaces.UserServiceInterface;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
//import org.springframework.security.crypto.factory.PasswordEncoderFactories;
//import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
public class CRUDBankApplication {

	public static void main(String[] args) {

		SpringApplication.run(CRUDBankApplication.class, args);
	}

//	@Bean
//	PasswordEncoder passwordEncoder() {
//		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
//	}
//
//	@Bean
//	CommandLineRunner run(UserService userService) {
//		return args -> {
//			userService.saveRole(new Role(null, "ROLE_USER"));
//			userService.saveRole(new Role(null, "ROLE_ADMIN"));
//
//			userService.saveUser(new User(null, "Guillermo Aviles", "guille", "IronHack123", new ArrayList<>()));
//			userService.saveUser(new User(null, "Administrator", "admin1", "IronHack123", new ArrayList<>()));
//
//			userService.addRoleToUser("guille", "ROLE_USER");
//			userService.addRoleToUser("admin1", "ROLE_ADMIN");
//		};
//	}
}
