package com.volvocars.spring.reactive.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(scanBasePackages = {"com.volvocars.spring.reactive.controller","com.volvocars.spring.reactive.router","com.volvocars.spring.reactive.handler"})
public class PetStoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(PetStoreApplication.class, args);
	}

}
