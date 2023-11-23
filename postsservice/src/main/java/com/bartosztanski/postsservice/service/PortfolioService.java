package com.bartosztanski.postsservice.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bartosztanski.notificationservice.model.TemplateMailRequest;
import com.bartosztanski.notificationservice.service.EmailService;
import com.bartosztanski.postsservice.model.ContactForm;
import com.bartosztanski.postsservice.model.PostResponse;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class PortfolioService {
	
	private final PostServiceCache postServiceCache;
	private final EmailService emailService;
	
	@Value("${spring.mail.username}")
	private String myEmail;
	
	public void sendContactEmail(ContactForm contactForm) throws MessagingException {
		
		Map<String, Object> map = new HashMap<>();
		map.put("name", contactForm.getName());
		map.put("email",contactForm.getEmail());
		map.put("message",contactForm.getMessage());
		
		TemplateMailRequest mailRequest = TemplateMailRequest
				.builder()
				.to(myEmail)
				.subject("Portfolio Contact Form Message From: "+contactForm.getName())
				.templateName("contact-form")
				.variablesMap(map)
				.attachment(null)
				.build();
		
		emailService.sendTemplateMessage(mailRequest);
		
	}
	
	public List<PostResponse> getTopPostsFromCache() {
		return postServiceCache.getTopPosts();
	}
	
}
