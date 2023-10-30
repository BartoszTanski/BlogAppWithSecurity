package com.bartosztanski.mailservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import jakarta.mail.SendFailedException;

@SpringBootApplication
public class MailServiceApplication {
	
	public static void main(String[] args) throws SendFailedException {
		SpringApplication.run(MailServiceApplication.class, args);
		
	}

}
