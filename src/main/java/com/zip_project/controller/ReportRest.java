package com.zip_project.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.zip_project.service.ReportService;
import com.zip_project.service.exception.CustomException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/report")
@ResponseStatus(HttpStatus.OK)
public class ReportRest {

	private final ReportService reportService;

	public ReportRest(ReportService reportService) {
		this.reportService = reportService;
	}

	@GetMapping("/test")
	public String testConnection() {
		log.info("Connected");
		return "test connection";
	}

	@GetMapping("/compile")
	@ResponseStatus(HttpStatus.OK)
	public void parseJson(@RequestParam Integer reportNumber) {
		try {
			reportService.compileReport(reportNumber);
		} catch (CustomException e) {
			log.info(e.getMessage());
			e.printStackTrace();
		}catch (Exception e) {
			log.info(e.getMessage());
			e.printStackTrace();
		}
	}
}
