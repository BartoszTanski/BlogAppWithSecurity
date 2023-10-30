package com.bartosztanski.notificationservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bartosztanski.notificationservice.model.TemplateMailRequest;
import com.bartosztanski.notificationservice.model.TextMailRequest;
import com.bartosztanski.notificationservice.service.EmailService;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/mail/")
@RequiredArgsConstructor
public class MailController {
	
	private final EmailService emailService;
	
	@PostMapping("/send/textMessage")
	public ResponseEntity<String> sendTextMessage(
			@RequestBody TextMailRequest textMailRequest) throws MessagingException {
		
		emailService.sendTextMessage(textMailRequest, false);
		return ResponseEntity.ok("Text message sent");
	}
	
	@PostMapping("/send/templateMessage")
	public ResponseEntity<String> sendTemplateMessage(
			@RequestBody TemplateMailRequest templateMailRequest) throws MessagingException {
		
		emailService.sendTemplateMessage(templateMailRequest);
		return ResponseEntity.ok("Template message sent"); 
	}
	
	@PostMapping("/send/htmlMessage")
	public ResponseEntity<String> sendHtmlMessage(
			@RequestBody TextMailRequest textMailRequest) throws MessagingException {
		
		emailService.sendHtmlMessage(textMailRequest);
		return ResponseEntity.ok("HTML message sent");
	}
	
}






