package com.example.demo;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepo;

@SpringBootApplication
@EnableScheduling
public class EjemploRestServiceJwtApplication extends SpringBootServletInitializer{

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public static void main(String[] args) {
		SpringApplication.run(EjemploRestServiceJwtApplication.class, args);
	}
	
	@Bean
	CommandLineRunner initData (UserRepo repositorioUsers) {
		User user = new User("joaquin96.RM@gmail.com",passwordEncoder.encode("12345"), "Joaquín", "Marín García", "ADMIN");
			
		return (args) -> {
			repositorioUsers.saveAll(
					Arrays.asList(user));
		};
	}

}
