package com.zip_project.controller;

import java.io.File;
import java.io.IOException;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.zip_project.service.ExtractFileService;
import com.zip_project.service.exception.DatabaseOperationException;
import com.zip_project.service.exception.FileExtractionException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/extract")
@ResponseStatus(HttpStatus.OK)
public class ExtractRest {

	private final ExtractFileService extractFileService;

	public ExtractRest(ExtractFileService extractFileService) {
		this.extractFileService = extractFileService;
	}

	@GetMapping("/test")
	public String extractFileTest() {
		return "test connection";
	}

	@GetMapping("/local")
	@ResponseStatus(HttpStatus.CREATED)
	public String localExtractFile() {
		String result = null;
		try {
			result = extractFileService.extractFileManager(null);
		} catch (IOException e) {
			throw new FileExtractionException(
					"Error extracting the zip file: " + e.getMessage());
		} catch (DataAccessException e) {
			throw new DatabaseOperationException(
					"An error occurred while accessing the database: " + e.getMessage());
		} catch (Exception e) {
			log.info(e.getMessage());
			return e.getMessage();
		}
		return result;
	}

	@PostMapping("/param")
	@ResponseStatus(HttpStatus.CREATED)
	public String paramExtractFile(File file) {
		String result = null;
		try {
			result = extractFileService.extractFileManager(file);
		} catch (IOException e) {
			throw new FileExtractionException(
					"Error extracting the zip file: " + e.getMessage());
		} catch (DataAccessException e) {
			throw new DatabaseOperationException(
					"An error occurred while accessing the database: " + e.getMessage());
		} catch (Exception e) {
			log.info(e.getMessage());
			return e.getMessage();
		}
		return result;
	}
}
