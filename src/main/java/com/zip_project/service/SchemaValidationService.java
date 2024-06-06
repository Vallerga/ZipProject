package com.zip_project.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.zip_project.exception.MyValidationException;
import com.zip_project.model.FileStatus;
import com.zip_project.service.crud.FileStatusService;

import lombok.extern.slf4j.Slf4j;
import net.jimblackler.jsonschemafriend.Schema;
import net.jimblackler.jsonschemafriend.SchemaException;
import net.jimblackler.jsonschemafriend.SchemaStore;
import net.jimblackler.jsonschemafriend.Validator;

@Slf4j
@Service
public class SchemaValidationService {
	@Value("${json.schema.path}")
	private String schemaPath;

	private final FileStatusService fileStatusService;

	public SchemaValidationService(FileStatusService fileStatusService) {
		this.fileStatusService = fileStatusService;
	}

	public void jsonValidationTest() throws MyValidationException {

		String validJsonLocation = "C:\\sviluppo\\java_workspaces\\Jersey\\ZipProject\\src\\main\\resources\\JsonSchema\\api.json";
		String notValidJsonLocation = "C:\\sviluppo\\java_workspaces\\Jersey\\ZipProject\\src\\main\\resources\\JsonSchema\\notValidApi.json";
		try {
			// load the schema data from the local file system
			String schema = new String(
					Files.readAllBytes(Paths.get(schemaPath)));
			// load JSON files from the local file system
			String validJsonContent = new String(
					Files.readAllBytes(Paths.get(validJsonLocation)));
			String notValidJsonContent = new String(
					Files.readAllBytes(Paths.get(notValidJsonLocation)));

			// initialize a SchemaStore
			SchemaStore schemaStore = new SchemaStore();

			// load the schema
			Schema schemaLoaded = schemaStore.loadSchemaJson(schema);

			// create a validator
			Validator validator = new Validator();

			// validate the valid JSON content (this will not throw an
			// exception)
			validator.validateJson(schemaLoaded, validJsonContent);

			// validate the invalid JSON content (this will throw a
			// ValidationException)
			validator.validateJson(schemaLoaded, notValidJsonContent);

		} catch (SchemaException e) {
			throw new MyValidationException(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			log.info(e.getMessage());
		}
	}

	public String jsonValidation(Integer reportNumber, Path schemaPath) {
		// extract files associated with a specific report
		List<FileStatus> statusList = fileStatusService
				.findByReportNumber(reportNumber);

		for (FileStatus es : statusList) {
			try {
				validateSingleFile(schemaPath, es);
			} catch (MyValidationException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public String validateSingleFile(Path schemaPath, FileStatus es)
			throws MyValidationException {
		String jsonPath = null;

		try {
			if (es != null && es.getFilePath() != null)
				jsonPath = es.getFilePath();
			else
				throw new MyValidationException(
						"Error during json validation process");

			// load the schema data from the local file system
			String schema = new String(Files.readAllBytes(schemaPath));
			String jsonContent = new String(
					Files.readAllBytes(Path.of(jsonPath)));

			// initialize a SchemaStore
			SchemaStore schemaStore = new SchemaStore();

			// load the schema
			Schema schemaLoaded = schemaStore.loadSchemaJson(schema);

			// create a validator
			Validator validator = new Validator();

			// validate the JSON content (this will throw an exception if
			// invalid)
			validator.validateJson(schemaLoaded, jsonContent);

			// update file validation status
			es.setJsonValidationStatus("validated");
			fileStatusService.updateFileStatus(es);
		} catch (SchemaException e) {
			es.setJsonValidationStatus("failed");
			fileStatusService.updateFileStatus(es);
			throw new MyValidationException(e.getMessage());
		} catch (IOException e) {
			es.setJsonValidationStatus("failed");
			fileStatusService.updateFileStatus(es);
			e.printStackTrace();
			log.info(e.getMessage());
		}
		return "";
	}

	public String validateSingleFileOld(Path jsonPath, Path schemaPath)
			throws MyValidationException {

		try {
			// load the schema data from the local file system
			String schema = new String(Files.readAllBytes(schemaPath));
			String jsonContent = new String(Files.readAllBytes(jsonPath));

			// initialize a SchemaStore
			SchemaStore schemaStore = new SchemaStore();

			// load the schema
			Schema schemaLoaded = schemaStore.loadSchemaJson(schema);

			// create a validator
			Validator validator = new Validator();

			// validate the JSON content (this will throw an exception if
			// invalid)
			validator.validateJson(schemaLoaded, jsonContent);

		} catch (SchemaException e) {
			throw new MyValidationException(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			log.info(e.getMessage());
		}
		return "";
	}
}