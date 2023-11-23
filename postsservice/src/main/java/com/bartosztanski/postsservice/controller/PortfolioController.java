package com.bartosztanski.postsservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.bartosztanski.postsservice.model.ContactForm;
import com.bartosztanski.postsservice.service.PortfolioService;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin(origins = {"https://bartosztanski.azurewebsites.net/","http://localhost:3000"})
@RequestMapping("/")
@RequiredArgsConstructor
public class PortfolioController {
	
	private final PortfolioService portfolioService;
	private final Logger LOGGER = LoggerFactory.getLogger(PortfolioController.class);
	
	@GetMapping("/")
	public ModelAndView home() {
		
	    ModelAndView modelAndView = new ModelAndView("index");
	    modelAndView.addObject("topBlogPosts", portfolioService.getTopPostsFromCache());
	    LOGGER.info("Returned index.html page");
	    return modelAndView;
	}
	@GetMapping("/about")
	public ModelAndView about() {
		
	    ModelAndView modelAndView = new ModelAndView("about");
	    modelAndView.addObject("topBlogPosts", portfolioService.getTopPostsFromCache());
	    LOGGER.info("Returned about.html page");
	    return modelAndView;
	}
	
	@GetMapping("/projects")
	public ModelAndView projects() {
		
	    ModelAndView modelAndView = new ModelAndView("projects");
	    modelAndView.addObject("topBlogPosts", portfolioService.getTopPostsFromCache());
	    LOGGER.info("Returned projects.html page");
	    return modelAndView;
	}
	
	@GetMapping("/project")
	public ModelAndView projects(@RequestParam("id") Long id) {
		String s = "project"+id;
	    ModelAndView modelAndView = new ModelAndView(s);
	    modelAndView.addObject("topBlogPosts", portfolioService.getTopPostsFromCache());
	    LOGGER.info("Returned "+s+".html page");
	    return modelAndView;
	}
	
	@GetMapping("/contact")
	public ModelAndView contact() {
		
	    ModelAndView modelAndView = new ModelAndView("contact");
	    ContactForm contactForm = new ContactForm();
	    modelAndView.addObject("contactForm", contactForm);
	    modelAndView.addObject("topBlogPosts", portfolioService.getTopPostsFromCache());
	    LOGGER.info("Returned contact.html page");
	    return modelAndView;
	}

	@PostMapping("/contactForm/send")
	public void sendContactEmail(@ModelAttribute ContactForm contactForm) throws MessagingException {
		
		LOGGER.info("User: "+contactForm.getName()+" with email: "+contactForm.getEmail()+" sent email from contact form");
		portfolioService.sendContactEmail(contactForm);   
	}
}
