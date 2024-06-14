package com.zip_project.service.exception.error;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MismatchApi {
	private String apiName;
	private Integer lineCode;
	private Long baseFileNumber;
	private Long compareFileNumber;
	private String apiListName;
	private String message;
	private String baseFilePath;
	private String compareFilePath;
}
