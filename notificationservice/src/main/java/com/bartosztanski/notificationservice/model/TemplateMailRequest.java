package com.bartosztanski.notificationservice.model;

import java.io.File;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TemplateMailRequest {
	
	private String to;
	private String subject;
	private String templateName;
	private Map<String, Object> variablesMap;
	private List<File> attachment;
}
