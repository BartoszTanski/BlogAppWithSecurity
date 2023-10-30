package com.bartosztanski.mailservice.service;

import java.io.IOException;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public interface TemplateService {

	public Map<String, String> getAllTemplates() throws IOException;
}
