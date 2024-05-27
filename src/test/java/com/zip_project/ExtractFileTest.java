package com.zip_project;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.zip_project.service.ExtractFile;

@DisplayName("ExtractFile simulation")
class ExtractFileTest {

	@Test
	void When_extractFileManager_Expect_DoesNotThrowException() {
		assertDoesNotThrow(() -> {
			ExtractFile.extractFileManager(null);
		});
	}
}
