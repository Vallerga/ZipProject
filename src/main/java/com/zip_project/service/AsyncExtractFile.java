package com.zip_project.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.zip_project.exception.MyValidationException;
import com.zip_project.service.jsonschema.JsonSchemaFriendService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AsyncExtractFile {



	private static final Path SCHEMA_PATH = Path.of(
			"C:\\sviluppo\\java_workspaces\\Jersey\\ZipProject\\src\\main\\resources\\JsonSchema\\apiSchema.json");
	private static final String DESTINATION_FOLDER = "C:/sviluppo/java_workspaces/Jersey/unzipFile/";
	private static final String SOURCE_FOLDER = "C:/sviluppo/java_workspaces/Jersey/ZipProject/src/main/resources/zipFile/xdce-module-tbgtee.zip";
	private static String staticFileName = "";
	
	public List<JsonNode> extractFileManager(File paramFile)
			throws IOException {
		byte[] zipStreamBuffer = new byte[1024];
		List<JsonNode> jsonNodeList = new ArrayList<>();
		ZipInputStream zis = null;
		ZipEntry zipEntry;

		// Folder path definition
		Path localSourceFile = Paths.get(SOURCE_FOLDER);
		String tagName = generateTagName(paramFile, localSourceFile);
		Path resourcePath = Paths.get(DESTINATION_FOLDER + tagName);
		AsyncExtractFile.staticFileName = tagName;
		try {
			zis = loadLocalOrParamFile(paramFile, localSourceFile);

			zipEntry = zis.getNextEntry();

			jsonNodeList = iterateStreamFile(zipStreamBuffer, zis, resourcePath,
					zipEntry);
		} catch (IOException e) {
			log.error("Error while extracting file: " + e.getMessage());
			throw e;
		}
		return jsonNodeList;
	}

	private List<JsonNode> iterateStreamFile(byte[] zipStreamBuffer,
			ZipInputStream zis, Path resourcePath, ZipEntry zipEntry)
			throws IOException {
		List<JsonNode> jsonNodeList = null;
		while (zipEntry != null) {
			Path newFilePath = newFile(resourcePath, zipEntry);

			// Count and describe folders during the extraction process
			if (zipEntry.isDirectory()) {
				if (!Files.exists(newFilePath))
					Files.createDirectories(newFilePath);
			}

			// Count and describe files during the extraction process
			else {
				Path parent = newFilePath.getParent();
				if (!Files.exists(parent)) {
					Files.createDirectories(parent);
				}

				jsonNodeList = writeFileAndAnalize(zipStreamBuffer, zis,
						newFilePath);
			}
			zipEntry = zis.getNextEntry();
		}
		return jsonNodeList;
	}

	private List<JsonNode> writeFileAndAnalize(byte[] zipStreamBuffer,
			ZipInputStream zis, Path newFilePath) throws IOException {
		List<String> errorMessage = new ArrayList<>();
		List<JsonNode> jsonNodeList = new ArrayList<>();
		try (FileOutputStream fos = new FileOutputStream(
				newFilePath.toFile())) {
			int len;
			while ((len = zis.read(zipStreamBuffer)) > 0) {
				fos.write(zipStreamBuffer, 0, len);
			}

			// Validate Json

			errorMessage.add(JSFService.jsonValidation(newFilePath, SCHEMA_PATH));

			JsonNode result = parseJsonManager.parseJSON(newFilePath,
					staticFileName);
			if (result != null && !result.isEmpty())
				jsonNodeList.add(result);

			// String line list approach
			/*
			 * List<String> StringList =
			 * StringLineListApproach.apiTest(newFilePath);
			 */
		} catch (MyValidationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonNodeList;
	}

	private ZipInputStream loadLocalOrParamFile(File paramFile,
			Path localSourceFile) throws IOException {
		ZipInputStream zis = null;

		// Check provided source presence
		Optional<File> isNullFile = Optional.ofNullable(paramFile);
		Boolean paramIsNull = !isNullFile.isPresent();

		// Extract a file from a local source or a provided source
		try {
			if (Boolean.TRUE.equals(paramIsNull)) {
				zis = new ZipInputStream(
						new FileInputStream(localSourceFile.toFile()));
			} else {
				zis = new ZipInputStream(new FileInputStream(paramFile));
			}
		} catch (IOException e) {
			log.error("Error while extracting file: " + e.getMessage());
			throw e;
		}
		return zis;
	}

	// Generate unique name with date + time tag
	private String generateTagName(File paramFile, Path localSourceFile) {
		LocalDateTime dataOdierna = LocalDateTime.now();
		String pattern = "dd/MM/yyyy HH:mm:ss";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);

		if (paramFile != null) {
			AsyncExtractFile.staticFileName = paramFile.getName();
		} else {
			AsyncExtractFile.staticFileName = localSourceFile.getFileName()
					.toString();
		}

		return AsyncExtractFile.staticFileName.toUpperCase() + " extracted at "
				+ dataOdierna.format(formatter).replace('/', '.').replace(':',
						'.');
	}

	private static Path newFile(Path destinationDir, ZipEntry zipEntry)
			throws IOException {
		Path destFile = destinationDir.resolve(zipEntry.getName()).normalize();

		if (!destFile.startsWith(destinationDir)) {
			throw new IOException("Entry is outside of the target dir: "
					+ zipEntry.getName());
		}

		return destFile;
	}
}
