package com.revature.ERSBackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("com.revature.models")
@ComponentScan("com.revature")
@EnableJpaRepositories("com.revature.DAOs")
public class ErsBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(ErsBackendApplication.class, args);
		System.out.println("ERS App is Running.");
	}
}
