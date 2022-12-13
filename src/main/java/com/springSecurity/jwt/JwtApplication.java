package com.springSecurity.jwt;

import com.springSecurity.jwt.domain.User;
import com.springSecurity.jwt.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class JwtApplication {

	public static void main(String[] args) {
		SpringApplication.run(JwtApplication.class, args);
	}
	@Bean
	CommandLineRunner commandLineRunner(UserRepository userRepository){

		return args -> {
			userRepository.save(new User("admin","1234","ROLE_ADMIN,ROLE_USER"));
			userRepository.save(new User("user","1234","ROLE_USER"));
		};
	}

}
