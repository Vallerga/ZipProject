package com.zip_project.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.zip_project.db.model.FileStatus;
import com.zip_project.service.costant.Costant.JsonValidation;
import com.zip_project.service.crud.FileStatusService;
import com.zip_project.service.exception.SchemaValidationException;
import com.zip_project.service.exception.error.ErrorContext;
import com.zip_project.service.exception.error.ValidationSchema;

import net.jimblackler.jsonschemafriend.Schema;
import net.jimblackler.jsonschemafriend.SchemaException;
import net.jimblackler.jsonschemafriend.SchemaStore;
import net.jimblackler.jsonschemafriend.Validator;

@Service
public class SchemaValidationService {
	@Value("${json.schema.path}")
	private String schemaPath;

	private final FileStatusService fileStatusService;

	public SchemaValidationService(FileStatusService fileStatusService) {
		this.fileStatusService = fileStatusService;
	}

	public ErrorContext jsonValidation(Integer reportNumber, Path schemaPath)
			throws IOException, SchemaValidationException, DataAccessException {
		ErrorContext errorContext = new ErrorContext();

		// extract files associated with a specific report
		List<FileStatus> statusList = fileStatusService
				.findByReportNumber(reportNumber);

		for (FileStatus fileStatus : statusList) {
			validateSingleFile(schemaPath, fileStatus, errorContext);

		}
		return errorContext;
	}

	public void validateSingleFile(Path schemaPath, FileStatus fileStatus,
			ErrorContext errorContext)
			throws IOException, SchemaValidationException, DataAccessException {
		String jsonPath = null;

		try {
			if (fileStatus != null && fileStatus.getFilePath() != null)
				jsonPath = fileStatus.getFilePath();
			else
				throw new SchemaValidationException(
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
			fileStatus.setJsonValidationStatus(JsonValidation.VALIDATED);
			fileStatusService.updateFileStatus(fileStatus);
		} catch (SchemaException e) {
			fileStatus.setJsonValidationStatus(JsonValidation.NOT_VALIDATED);
			fileStatusService.updateFileStatus(fileStatus);

			errorContext.setValidationSchemaErrors(parseErrorMessage(
					e.getMessage(), fileStatus, errorContext));

			// throw new SchemaValidationException(e.getMessage());
		} catch (IOException e) {
			fileStatus.setJsonValidationStatus(JsonValidation.NOT_VALIDATED);
			fileStatusService.updateFileStatus(fileStatus);
			throw new IOException(e.getMessage());
		}
	}

	public void jsonValidationTest()
			throws SchemaValidationException, IOException {

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
			throw new SchemaValidationException(e.getMessage());
		} catch (IOException e) {
			throw new IOException(e.getMessage());
		}
	}

	private List<ValidationSchema> parseErrorMessage(String errorMessage,
			FileStatus fileStatus, ErrorContext errorContext) {
		TextContentRead errorParser = new TextContentRead();
		List<ValidationSchema> schemaValidationContainer;

		if (errorContext.getMismatchApiErrors() != null) {
			schemaValidationContainer = errorContext
					.getValidationSchemaErrors();
		} else {
			schemaValidationContainer = new ArrayList<>();
		}

		List<String> errorList = new ArrayList<>(
				Arrays.asList(errorMessage.split("\\r\\n")));

		for (String singleError : errorList) {
			ValidationSchema validationSchema = new ValidationSchema();
			validationSchema = errorParser.readTextContent(singleError,
					validationSchema);
			validationSchema.setFilePath(fileStatus.getFilePath());

			schemaValidationContainer.add(validationSchema);
		}

		errorContext.setValidationSchemaErrors(schemaValidationContainer);
		return schemaValidationContainer;
	}
}
