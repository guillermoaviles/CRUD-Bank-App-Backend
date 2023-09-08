package com.ironhack.crudbankapp;

import com.ironhack.crudbankapp.model.User;
import com.ironhack.crudbankapp.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableFeignClients
public class CRUDBankApplication {

	public static void main(String[] args) {

		SpringApplication.run(CRUDBankApplication.class, args);
	}

	@Bean
	CommandLineRunner run(UserRepository userRepository) {
		return args -> {
			User user1 = new User();
			user1.setName("Guillermo Aviles");
			userRepository.save(user1);
		};
	}
}
