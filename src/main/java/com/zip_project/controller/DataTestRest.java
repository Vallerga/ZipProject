package com.zip_project.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.zip_project.service.DataTestService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/datatest")
@ResponseStatus(HttpStatus.OK)
public class DataTestRest {

	private final DataTestService dataTestService;

	public DataTestRest(DataTestService dataTestService) {
		this.dataTestService = dataTestService;
	}

	@GetMapping("/test")
	public String testConnection() {
		log.info("Connected");
		return "test connection";
	}

	@GetMapping("/datatest")
	@ResponseStatus(HttpStatus.OK)
	public void parseJson(@RequestParam Integer reportNumber) {
		try {
			dataTestService.dataTest(reportNumber);
		} catch (Exception e) {
			log.info(e.getMessage());
			e.printStackTrace();
		}
	}
}
