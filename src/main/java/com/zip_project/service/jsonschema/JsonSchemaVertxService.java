package com.zip_project.service.jsonschema;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.stereotype.Service;

import io.vertx.core.json.JsonObject;
import io.vertx.json.schema.Draft;
import io.vertx.json.schema.JsonSchema;
import io.vertx.json.schema.JsonSchemaOptions;
import io.vertx.json.schema.OutputUnit;
import io.vertx.json.schema.Validator;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class JsonSchemaVertxService {

	// @Value("${json.schema.path}")
	// private String schemaPath;

	public String jsonValidation() {
		String result = null;
		try {
			// Definisci il percorso completo dello schema JSON
			String schemaPath = "C:\\sviluppo\\java_workspaces\\Jersey\\ZipProject\\src\\main\\resources\\JsonSchema\\apiSchema.json";

			// Carica lo schema JSON
			String schemaContent = new String(
					Files.readAllBytes(Paths.get(schemaPath)));
			JsonObject schemaObject = new JsonObject(schemaContent);
			JsonSchema schema = JsonSchema.of(schemaObject);

			// Carica gli schemi JSON dai file
			// String validJsonLocation =
			// "C:\\sviluppo\\java_workspaces\\Jersey\\ZipProject\\src\\main\\resources\\JsonSchema\\api.json";
			String notValidJsonLocation = "C:\\sviluppo\\java_workspaces\\Jersey\\ZipProject\\src\\main\\resources\\JsonSchema\\notValidApi.json";

			// Carica il JSON da validare
			String jsonContent = new String(
					Files.readAllBytes(Paths.get(notValidJsonLocation)));
			JsonObject jsonObject = new JsonObject(jsonContent);

			// Valida il JSON rispetto allo schema principale
			OutputUnit resultValidation = Validator
					.create(schema,
							new JsonSchemaOptions().setDraft(Draft.DRAFT7)
									.setBaseUri("file:///"
											+ Paths.get(".").toAbsolutePath()
													.normalize().toString()
											+ "/"))
					.validate(jsonObject);

			if (Boolean.TRUE.equals(resultValidation.getValid())) {
				log.info("JSON è valido");
				result = "JSON è valido";
				return result;
			} else {
				log.info("getError: {}", resultValidation.getError());
				log.info("getAbsoluteKeywordLocation: {}",
						resultValidation.toString());
				log.info("getInstanceLocation: {}",
						resultValidation.toException(result));
				result = "JSON non è valido: " + resultValidation.getError();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
}
