package com.zip_project.controller;

import java.io.File;
import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.zip_project.service.UnzipFile;

@RestController
@RequestMapping("/unzip")
@ResponseStatus(HttpStatus.OK)
public class UnzipRest {

	@GetMapping("/test")
	public String unzipFileTest() {
		return "Unzip file test";
	}

	@GetMapping("/local")
	@ResponseStatus(HttpStatus.CREATED)
	public String unzipFile() {
		try {
			UnzipFile.unzipFileManager(null);
		} catch (IOException e) {
			e.printStackTrace();
			return e.getLocalizedMessage() + " \n " + e.getMessage();
			
		}
		return "FILE UNZIPPED";
	}

	@PostMapping("/param")
	@ResponseStatus(HttpStatus.CREATED)
	public String unzipFile(File file) {
		try {
			UnzipFile.unzipFileManager(file);
		} catch (IOException e) {
			e.printStackTrace();
			return e.getLocalizedMessage() + " \n " + e.getMessage();
		}
		return "FILE UNZIPPED";
	}
}
