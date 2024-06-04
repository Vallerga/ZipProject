package com.zip_project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.zip_project.service.jsonschema.JsonSchemaFriendService;

@RestController
@RequestMapping("/jsonschema")
@ResponseStatus(HttpStatus.OK)
public class JsonSchemaValidatorRest {

	@Autowired
	JsonSchemaFriendService jsonSchemaService;

	@GetMapping("/test")
	public String extractFileTest() {
		return "test connection";
	}

	@GetMapping("/validate")
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
}
