package com.bartosztanski.notificationservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import jakarta.mail.SendFailedException;

@SpringBootApplication
public class NotificationServiceApplication {
	
	public static void main(String[] args) throws SendFailedException {
		SpringApplication.run(NotificationServiceApplication.class, args);
		
	}

}
