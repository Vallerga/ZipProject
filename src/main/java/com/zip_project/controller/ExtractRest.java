package com.zip_project.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.zip_project.service.ExtractFile;
import com.zip_project.service.word.ExtractFileRepWord;

@RestController
@RequestMapping("/extract")
@ResponseStatus(HttpStatus.OK)
public class ExtractRest {

	@Autowired
	ExtractFile ef;

	@GetMapping("/test")
	public String extractFileTest() {
		return "test connection";
	}

	@GetMapping("/word/local")
	@ResponseStatus(HttpStatus.CREATED)
	public String extractFileRepWord() {
		try {
			ExtractFileRepWord.extractFileManager(null);
		} catch (IOException e) {
			e.printStackTrace();
			return e.getLocalizedMessage() + " \n " + e.getMessage();

		}
		return "FILE EXTRACTED";
	}

	@PostMapping("/word/param")
	@ResponseStatus(HttpStatus.CREATED)
	public String extractFileRepWord(File file) {
		try {
			ExtractFileRepWord.extractFileManager(file);
		} catch (IOException e) {
			e.printStackTrace();
			return e.getLocalizedMessage() + " \n " + e.getMessage();
		}
		return "FILE EXTRACTED";
	}

	@GetMapping("/local")
	@ResponseStatus(HttpStatus.CREATED)
	public String extractFile() {

		try {
			List<JsonNode> extractFileManager = ef.extractFileManager(null);

			System.out.println(
					"extractFileManager syze: " + extractFileManager.size());
			// System.out.println("extractFileManager: " + extractFileManager);

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
			List<JsonNode> extractFileManager = ef.extractFileManager(file);

			System.out.println(
					"extractFileManager syze: " + extractFileManager.size());
			// System.out.println("extractFileManager: " + extractFileManager);

		} catch (IOException e) {
			e.printStackTrace();
			return e.getLocalizedMessage() + " \n " + e.getMessage();
		}
		return "FILE EXTRACTED";
	}
}
