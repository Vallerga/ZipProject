package com.zip_project.service.exception.error;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MissingApiList {
	private Long fileNumber;
	private String apiListName;
	private String message;
	private String filePath;
}
