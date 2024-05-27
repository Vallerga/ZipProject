package com.zip_project.controller;

import java.io.File;
import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.zip_project.service.ExtractFile;

@RestController
@RequestMapping("/extract")
@ResponseStatus(HttpStatus.OK)
public class ExtractRest {

	@GetMapping("/test")
	public String extractFileTest() {
		return "extract file test";
	}

	@GetMapping("/local")
	@ResponseStatus(HttpStatus.CREATED)
	public String extractFile() {
		try {
			ExtractFile.extractFileManager(null);
		} catch (IOException e) {
			e.printStackTrace();
			return e.getLocalizedMessage() + " \n " + e.getMessage();
			
		}
		return "FILE EXTRACTED";
	}

	@PostMapping("/param")
	@ResponseStatus(HttpStatus.CREATED)
	public String extractFile(File file) {
		try {
			ExtractFile.extractFileManager(file);
		} catch (IOException e) {
			e.printStackTrace();
			return e.getLocalizedMessage() + " \n " + e.getMessage();
		}
		return "FILE EXTRACTED";
	}
}
