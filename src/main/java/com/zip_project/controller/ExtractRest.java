package com.zip_project.controller;

import java.io.File;
import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.zip_project.service.ExtractFileService;
import com.zip_project.service.old.word.ExtractFileRepWordOld;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/extract")
@ResponseStatus(HttpStatus.OK)
public class ExtractRest {

	private final ExtractFileService extractFileService;
	private static final String EXTRACTED_EXTRACTED = "FILE EXTRACTED";

	public ExtractRest(ExtractFileService extractFileService) {
		this.extractFileService = extractFileService;
	}

	@GetMapping("/test")
	public String extractFileTest() {
		return "test connection";
	}

	@GetMapping("/word/local")
	@ResponseStatus(HttpStatus.CREATED)
	public String extractFileRepWordOld() {
		try {
			ExtractFileRepWordOld.extractFileManager(null);
		} catch (IOException e) {
			e.printStackTrace();
			return e.getLocalizedMessage() + " \n " + e.getMessage();

		}
		return EXTRACTED_EXTRACTED;
	}

	@PostMapping("/word/param")
	@ResponseStatus(HttpStatus.CREATED)
	public String extractFileRepWordOld(File file) {
		try {
			ExtractFileRepWordOld.extractFileManager(file);
		} catch (IOException e) {
			e.printStackTrace();
			return e.getLocalizedMessage() + " \n " + e.getMessage();
		}
		return EXTRACTED_EXTRACTED;
	}

	@GetMapping("/sync/local")
	@ResponseStatus(HttpStatus.CREATED)
	public String extractFileOld() {

		// try {
		// List<JsonNode> extractFileManager =
		// extractFileOld.extractFileManager(null);
		//
		// log.info("extractFileManager syze: " + extractFileManager.size());
		//
		// } catch (IOException e) {
		// e.printStackTrace();
		// return e.getLocalizedMessage() + " \n " + e.getMessage();
		//
		// }
		return EXTRACTED_EXTRACTED;
	}

	@PostMapping("/sync/param")
	@ResponseStatus(HttpStatus.CREATED)
	public String extractFileOld(File file) {

		// try {
		// List<JsonNode> extractFileManager =
		// extractFileOld.extractFileManager(file);
		//
		// log.info("extractFileManager syze: " + extractFileManager.size());
		//
		// } catch (IOException e) {
		// e.printStackTrace();
		// return e.getLocalizedMessage() + " \n " + e.getMessage();
		// }
		return EXTRACTED_EXTRACTED;
	}

	@GetMapping("/local")
	@ResponseStatus(HttpStatus.CREATED)
	public Integer localExtractFile() {
		Integer result = null;
		try {
			result = extractFileService.extractFileManager(null);
		} catch (IOException e) {
			log.info(e.getLocalizedMessage() + " \n " + e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@PostMapping("/param")
	@ResponseStatus(HttpStatus.CREATED)
	public Integer paramExtractFile(File file) {
		Integer result = null;
		try {
			result = extractFileService.extractFileManager(file);
		} catch (IOException e) {
			log.info(e.getLocalizedMessage() + " \n " + e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
