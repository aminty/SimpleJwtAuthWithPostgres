package com.springSecurity.jwt;

import com.springSecurity.jwt.domain.User;
import com.springSecurity.jwt.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class JwtApplication {

	public static int STEP=0;

	public static void main(String[] args) {
		System.out.println("inside main class --> step 1");
		SpringApplication.run(JwtApplication.class, args);
	}
	@Bean
	CommandLineRunner commandLineRunner(UserRepository userRepository){

		return args -> {
			++STEP;
			System.out.println("inside main class --> step "+STEP);

			userRepository.save(new User("admin","1234","ROLE_ADMIN,ROLE_USER"));
			userRepository.save(new User("user","1234","ROLE_USER"));
		};
	}

}
