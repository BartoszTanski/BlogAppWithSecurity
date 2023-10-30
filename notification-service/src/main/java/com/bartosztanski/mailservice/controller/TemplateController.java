package com.bartosztanski.mailservice.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bartosztanski.mailservice.service.TemplateService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/templates")
@RequiredArgsConstructor
public class TemplateController {
	
	private final TemplateService templateService;
	
	@GetMapping("")  
	public ResponseEntity<Map<String, String>> getTemplates() throws IOException {
		Map<String, String> templates = new HashMap<>();
		templates = templateService.getAllTemplates();
		return ResponseEntity.ok(templates);
	} // TODO create Template model with Long id, String templateName, Map<String, String> params - that could be stored in db?  
	// All templates could be stored as files - named by numbers / stored in db as String
}
