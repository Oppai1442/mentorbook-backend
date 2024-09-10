package com.hsf301.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
// @Configuration
// @EnableAutoConfiguration  // Sprint Boot Auto Configuration
// @ComponentScan(basePackages = "com.hsf301.project")
public class MentorbookBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(MentorbookBackendApplication.class, args);
	}

}