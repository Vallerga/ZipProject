package com.zip_project.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.zip_project.db.model.ModuleDefaults;
import com.zip_project.service.DataParseService;
import com.zip_project.service.DataTestService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/jsonparse")
@ResponseStatus(HttpStatus.OK)
public class DataParseRest {

	private final DataParseService dataParseService;
	private final DataTestService dataTestService;

	public DataParseRest(DataParseService dataParseService,
			DataTestService dataTestService) {
		this.dataParseService = dataParseService;
		this.dataTestService = dataTestService;
	}

	@GetMapping("/test")
	public String parseJsonConnectionTest() {
		return "test connection";
	}

	@GetMapping("/parse")
	@ResponseStatus(HttpStatus.OK)
	public List<ModuleDefaults> parseJson(@RequestParam Integer reportNumber) {
		List<ModuleDefaults> result = new ArrayList<>();
		try {
			result = dataParseService.parseJsonManager(reportNumber);
		} catch (Exception e) {
			log.info(e.getMessage());
			e.printStackTrace();
		}
		return result;
	}

	@GetMapping("/datatest")
	@ResponseStatus(HttpStatus.OK)
	public List<ModuleDefaults> dataTest(@RequestParam Integer reportNumber) {
		List<ModuleDefaults> result = new ArrayList<>();
		try {
			dataTestService.dataTest(reportNumber);
		} catch (Exception e) {
			log.info(e.getMessage());
			e.printStackTrace();
		}
		return result;
	}
}
