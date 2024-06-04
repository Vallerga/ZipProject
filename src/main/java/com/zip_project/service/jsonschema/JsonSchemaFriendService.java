package com.zip_project.service.jsonschema;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Service;

import com.zip_project.exception.MyValidationException;

import lombok.extern.slf4j.Slf4j;
import net.jimblackler.jsonschemafriend.Schema;
import net.jimblackler.jsonschemafriend.SchemaException;
import net.jimblackler.jsonschemafriend.SchemaStore;
import net.jimblackler.jsonschemafriend.Validator;

@Slf4j
@Service
public class JsonSchemaFriendService {
	public void jsonValidationTest() throws MyValidationException {
		// carico lo schema JSON da store locale
		String schemaPath = "C:\\sviluppo\\java_workspaces\\Jersey\\ZipProject\\src\\main\\resources\\JsonSchema\\apiSchema.json";

		// Carica File JSON da store locali
		String validJsonLocation = "C:\\sviluppo\\java_workspaces\\Jersey\\ZipProject\\src\\main\\resources\\JsonSchema\\api.json";
		String notValidJsonLocation = "C:\\sviluppo\\java_workspaces\\Jersey\\ZipProject\\src\\main\\resources\\JsonSchema\\notValidApi.json";
		try {
			// Load the data from the local
			String schema = new String(
					Files.readAllBytes(Paths.get(schemaPath)));
			String validJsonContent = new String(
					Files.readAllBytes(Paths.get(validJsonLocation)));
			String notValidJsonContent = new String(
					Files.readAllBytes(Paths.get(notValidJsonLocation)));
			// Initialize a SchemaStore.
			SchemaStore schemaStore = new SchemaStore();
			// Load the schema.
			Schema schemaLoaded = schemaStore.loadSchemaJson(schema);
			// Create a validator.
			Validator validator = new Validator();
			// Will not throw an exception.
			validator.validateJson(schemaLoaded, validJsonContent);
			// Will throw a ValidationException.
			validator.validateJson(schemaLoaded, notValidJsonContent);
		} catch (SchemaException e) {
			throw new MyValidationException(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			log.info(e.getMessage());
		}
	}

	public String jsonValidation(Path jsonPath, Path schemaPath)
			throws MyValidationException {
		// carico lo schema JSON da store locale
		// String schemaPath =
		// "C:\\sviluppo\\java_workspaces\\Jersey\\ZipProject\\src\\main\\resources\\JsonSchema\\apiSchema.json";

		try {
			// Load the data from the local
			String schema = new String(Files.readAllBytes(schemaPath));
			String jsonContent = new String(Files.readAllBytes(jsonPath));
			// Initialize a SchemaStore.
			SchemaStore schemaStore = new SchemaStore();
			// Load the schema.
			Schema schemaLoaded = schemaStore.loadSchemaJson(schema);
			// Create a validator.
			Validator validator = new Validator();
			// If isn't valid Json will throw an exception.
			// validator.validateJson(schemaLoaded, jsonContent);
		} catch (SchemaException e) {
			throw new MyValidationException(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			log.info(e.getMessage());
		}
		return "";
	}
}
