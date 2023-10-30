package com.bartosztanski.mailservice.model;

import java.io.File;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TextMailRequest {
	
	private String to;
	private String subject;
	private String content;
	private List<File> attachments;
}
