package com.bartosztanski.userservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.bartosztanski*"})
public class UserDetailsServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserDetailsServiceApplication.class, args);
	}

}
