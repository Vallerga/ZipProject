package com.zip_project.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.zip_project.service.ExtractFile;
import com.zip_project.service.word.ExtractFileRepWord;

@RestController
@RequestMapping("/extract")
@ResponseStatus(HttpStatus.OK)
public class ExtractRest {

	@GetMapping("/test")
	public String extractFileTest() {
		return "extract file test";
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
	public String extractFile(@RequestParam List<String> test) {

		try {
			List<List<String>> extractFileManager = ExtractFile
					.extractFileManager(null, test);

			System.out.println(
					"extractFileManager syze: " + extractFileManager.size());
			System.out.println("extractFileManager: " + extractFileManager);

		} catch (IOException e) {
			e.printStackTrace();
			return e.getLocalizedMessage() + " \n " + e.getMessage();

		}
		return "FILE EXTRACTED";
	}

	@PostMapping("/param")
	@ResponseStatus(HttpStatus.CREATED)
	public String extractFile(File file, @RequestParam List<String> test) {

		try {
			List<List<String>> extractFileManager = ExtractFile
					.extractFileManager(file, test);

			System.out.println(
					"extractFileManager syze: " + extractFileManager.size());
			System.out.println("extractFileManager: " + extractFileManager);

		} catch (IOException e) {
			e.printStackTrace();
			return e.getLocalizedMessage() + " \n " + e.getMessage();
		}
		return "FILE EXTRACTED";
	}
}
