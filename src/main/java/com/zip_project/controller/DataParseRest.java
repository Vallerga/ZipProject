package com.zip_project.controller;

import java.io.IOException;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.zip_project.service.DataParseService;
import com.zip_project.service.DataTestService;
import com.zip_project.service.exception.DataParsingException;
import com.zip_project.service.exception.DatabaseOperationException;
import com.zip_project.service.exception.TestExecutionException;
import com.zip_project.service.exception.error.ErrorContext;

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
	public String parseJson(@RequestParam Integer reportNumber) {
		try {
			dataParseService.parseJsonManager(reportNumber);
		} catch (IOException e) {
			throw new DataParsingException(
					"Data parsing error: " + e.getMessage());
		} catch (DataAccessException e) {
			throw new DatabaseOperationException(
					"An error occurred while accessing the database: "
							+ e.getMessage());
		} catch (Exception e) {
			log.info(e.getMessage());
			return e.getMessage();
		}
		return "data parsed";
	}

	@GetMapping("/datatest")
	@ResponseStatus(HttpStatus.OK)
	public ErrorContext dataTest(@RequestParam Integer reportNumber) {
		ErrorContext errorContext = new ErrorContext();
		try {
			errorContext = dataTestService.dataTest(reportNumber);
		} catch (TestExecutionException e) {
			log.info(e.getMessage());
			throw new TestExecutionException(
					"Data parsing error: " + e.getMessage());
		} catch (DataParsingException e) {
			throw new DataParsingException(
					"Test api validation failed: " + e.getMessage());
		} catch (DataAccessException e) {
			throw new DatabaseOperationException(
					"An error occurred while accessing the database: "
							+ e.getMessage());
		} catch (Exception e) {
			log.info(e.getMessage());
		}
		return errorContext;
	}
}
