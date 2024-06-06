package com.zip_project.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/datatest")
@ResponseStatus(HttpStatus.OK)
public class DataTestRest {

	@GetMapping("/test")
	public String testConnection() {
		log.info("Connected");
		return "test connection";
	}
}
