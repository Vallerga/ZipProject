package com.zip_project.controller;

import java.nio.file.Path;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.zip_project.service.SchemaValidationService;

@RestController
@RequestMapping("/jsonschema")
@ResponseStatus(HttpStatus.OK)
public class SchemaValidationRest {

	private static final Path SCHEMA_PATH = Path.of(
			"C:\\sviluppo\\java_workspaces\\Jersey\\ZipProject\\src\\main\\resources\\JsonSchema\\apiSchema.json");
	private final SchemaValidationService jsonSchemaService;

	public SchemaValidationRest(SchemaValidationService jsonSchemaService) {
		this.jsonSchemaService = jsonSchemaService;
	}

	@GetMapping("/test")
	public String validateJsonConnectionTest() {
		return "test connection";
	}

	@GetMapping("/validate/test")
	@ResponseStatus(HttpStatus.OK)
	public String validateJsonTest() {
		String result = "";
		try {
			jsonSchemaService.jsonValidationTest();
		} catch (Exception e) {
			return e.getMessage();
		}
		return result;
	}

	@GetMapping("/validate")
	@ResponseStatus(HttpStatus.OK)
	public String validateJson(@RequestParam Integer reportNumber) {
		String result = "";
		try {
			result = jsonSchemaService.jsonValidation(reportNumber,
					SCHEMA_PATH);
		} catch (Exception e) {
			return e.getMessage();
		}
		return result;
	}
}
