package com.zip_project.service.exception.error;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorContext {
	private List<MissingApiList> missingApiListErrors;
	private List<MismatchApi> mismatchApiErrors;
	private List<ValidationSchema> validationSchemaErrors;

	public boolean hasErrors() {
		return !missingApiListErrors.isEmpty()
				|| !mismatchApiErrors.isEmpty()|| !validationSchemaErrors.isEmpty();
	}
}
