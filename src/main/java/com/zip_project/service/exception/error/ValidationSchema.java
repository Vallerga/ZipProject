package com.zip_project.service.exception.error;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ValidationSchema {
	private String context;
	String errorPosition;
	String expectedStructure;
	String problemDescription;
	private String filePath;
}
