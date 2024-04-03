package com.example.cyberParc;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

@SpringBootApplication
@EnableScheduling
public class CyberParcApplication {

	public static void main(String[] args) {
		SpringApplication.run(CyberParcApplication.class, args);
	}
	@Bean
	CommandLineRunner start(JdbcUserDetailsManager JdbcUserDetailsManager, PasswordEncoder PasswordEncoder) {

		return args -> {
			if (!JdbcUserDetailsManager.userExists("admin"))
				JdbcUserDetailsManager.createUser(User.withUsername("admin").password(PasswordEncoder.encode("12345")).authorities("CLIENT", "ADMIN").build());
			if (!JdbcUserDetailsManager.userExists("ayoub"))
				JdbcUserDetailsManager.createUser(User.withUsername("ayoub").password(PasswordEncoder.encode("12345")).authorities("CLIENT").build());

		};
	}

}
