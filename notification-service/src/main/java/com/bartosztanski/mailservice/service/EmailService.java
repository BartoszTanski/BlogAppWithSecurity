package com.bartosztanski.mailservice.service;

import org.springframework.stereotype.Service;

import com.bartosztanski.mailservice.model.TemplateMailRequest;
import com.bartosztanski.mailservice.model.TextMailRequest;

import jakarta.mail.MessagingException;

@Service
public interface EmailService {

	public void sendSimpleMessage(String to, String subject, String text);

	public void sendTemplateMessage(TemplateMailRequest templateMailRequest) throws MessagingException;
	
	public void sendHtmlMessage(TextMailRequest textMailRequest) throws MessagingException;

	public void sendTextMessage(TextMailRequest textMailRequest, Boolean renderHtml) throws MessagingException;
	

}
