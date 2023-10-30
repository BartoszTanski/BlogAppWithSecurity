package com.bartosztanski.notificationservice.service;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Service;

@Service
public class TemplateServiceImpl implements TemplateService {
	
	
	@Autowired
	ResourcePatternResolver resourceResolver;
	
	Logger LOGGER = LoggerFactory.getLogger(TemplateServiceImpl.class);
	
	@Override
	public Map<String, String> getAllTemplates() throws IOException {
	
		Map<String, String> templates = new HashMap<>();
		
		Resource[] resources = resourceResolver.getResources("classpath:templates/*.html");
		Charset charset = StandardCharsets.UTF_8;
		String fileContent = "";
		String fileName = "";
		
		for (Resource resource : resources) {
			fileContent = resource.getContentAsString(charset);
			fileName = resource.getFilename().substring(0,resource.getFilename().length()-5); //cuts off .html
			templates.put(fileName, fileContent);
		}
		
		LOGGER.info("TemplateServiceImpl.getAllTemplates: returned "+templates.size()+" templates");
		return templates;
	}
}
