package com.bartosztanski.postsservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@CrossOrigin(origins = {"https://bartosztanski.azurewebsites.net/","http://localhost:3000"})
@RequestMapping("/")
public class HomeController {
	
	private final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);
	
	@GetMapping("/")
	public ModelAndView home() {
		
	    ModelAndView modelAndView = new ModelAndView("index");
	    LOGGER.info("Returned index.html page");
	    return modelAndView;
	}
	@GetMapping("/about")
	public ModelAndView about() {
		
	    ModelAndView modelAndView = new ModelAndView("about");
	    LOGGER.info("Returned about.html page");
	    return modelAndView;
	}
	
	@GetMapping("/projects")
	public ModelAndView projects() {
		
	    ModelAndView modelAndView = new ModelAndView("projects");
	    LOGGER.info("Returned projects.html page");
	    return modelAndView;
	}
	
	@GetMapping("/project")
	public ModelAndView projects(@RequestParam("id") Long id) {
		String s = "project"+id;
	    ModelAndView modelAndView = new ModelAndView(s);
	    LOGGER.info("Returned "+s+".html page");
	    return modelAndView;
	}
	
	@GetMapping("/contact")
	public ModelAndView contact() {
		
	    ModelAndView modelAndView = new ModelAndView("contact");
	    LOGGER.info("Returned contact.html page");
	    return modelAndView;
	}
}
