package com.zip_project.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.zip_project.service.DataParseService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/jsonparse")
@ResponseStatus(HttpStatus.OK)
public class DataParseRest {

	private final DataParseService asyncParseJSON;

	public DataParseRest(DataParseService asyncParseJSON) {
		this.asyncParseJSON = asyncParseJSON;
	}

	@GetMapping("/test")
	public String parseJsonConnectionTest() {
		return "test connection";
	}

	@GetMapping("/parse")
	@ResponseStatus(HttpStatus.OK)
	public List<JsonNode> parseJson(@RequestParam Integer reportNumber) {
		List<JsonNode> result = new ArrayList<>();
		try {
			result = asyncParseJSON.parseJsonManager(reportNumber);
		} catch (Exception e) {
			log.info(e.getMessage());
			e.printStackTrace();
		}
		return result;
	}
}
