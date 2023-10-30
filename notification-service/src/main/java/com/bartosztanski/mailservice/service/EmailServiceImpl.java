package com.bartosztanski.mailservice.service;

import java.io.File;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import com.bartosztanski.mailservice.model.TemplateMailRequest;
import com.bartosztanski.mailservice.model.TextMailRequest;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements EmailService {
	
	@Value("${user.email.from}")
	private String NOREPLY_ADDRESS;
	
	@Autowired
    private JavaMailSender emailSender;
	
	@Autowired
    private SpringTemplateEngine thymeleafTemplateEngine;

	
	Logger LOGGER = LoggerFactory.getLogger(EmailServiceImpl.class);

    public void sendSimpleMessage(String to, String subject, String text)  {
    	
        SimpleMailMessage message = new SimpleMailMessage(); 
        
        message.setFrom(NOREPLY_ADDRESS);
        message.setTo(to); 
        message.setSubject(subject); 
        message.setText(text);
        
        emailSender.send(message);
        LOGGER.info("EmailServiceImpl: Sent Simple Message Email: "+subject+", To: "+ to);
    }	
	

	@Override
	public void sendTemplateMessage(TemplateMailRequest templateMailRequest) throws MessagingException {
		
		//Process template with given variables to HTML String
		Context thymeleafContext = new Context();
	    thymeleafContext.setVariables(templateMailRequest.getVariablesMap());
	    String htmlBody = thymeleafTemplateEngine.process(
	    		templateMailRequest.getTemplateName()+".html", thymeleafContext);
	    
	    TextMailRequest textMailRequest = TextMailRequest
	    			.builder()
	    			.to(templateMailRequest.getTo())
	    			.subject(templateMailRequest.getSubject())
	    			.content(htmlBody)
	    			.attachments(templateMailRequest.getAttachment())
	    			.build();
	    
	    sendHtmlMessage(textMailRequest);
		
	}
	
	@Override
	public void sendHtmlMessage(TextMailRequest textMailRequest) throws MessagingException {
		
		sendTextMessage(textMailRequest, true);
	}

	@Override
	public void sendTextMessage(TextMailRequest textMailRequest, Boolean renderHtml) throws MessagingException {

		MimeMessage message = emailSender.createMimeMessage();
	    
	    MimeMessageHelper helper = new MimeMessageHelper(message, true);
	    
	    helper.setFrom(NOREPLY_ADDRESS);
	    helper.setTo(textMailRequest.getTo());
	    helper.setSubject(textMailRequest.getSubject());
	    helper.setText(textMailRequest.getContent(), renderHtml);
	 
	    if (textMailRequest.getAttachments()!=null&&!textMailRequest.getAttachments().isEmpty()) {
	    	for (File attachment : textMailRequest.getAttachments()) {  		
	    		if (attachment.exists()) {
	    			helper.addAttachment(attachment.getName(), attachment);
	    		}
	    	}
	    }
	    
	    emailSender.send(message);
	    LOGGER.info("EmailServiceImpl: Sent Mime Message: "+textMailRequest.getSubject() +", To: "+ textMailRequest.getTo());
	}

    
}



