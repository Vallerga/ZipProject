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
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExtractFile {

	private ExtractFile() {
	}

	private static final String DESTINATION_FOLDER = "C:/sviluppo/java_workspaces/Jersey/unzipFile/";
	private static final String SOURCE_FOLDER = "C:/sviluppo/java_workspaces/Jersey/ZipProject/src/main/resources/zipFile/webapp.zip";
	private static final String DOCUMENT_NAME = "WEBAPP";

	public static List<List<String>> extractFileManager(File paramFile,
			List<String> test) throws IOException {
		byte[] zipStreamBuffer = new byte[1024];
		List<List<String>> genericLinesList = new ArrayList<>();
		Integer counterNum = 0;
		Integer folderNum = 0;
		Integer filesNum = 0;

		// Check provided source presence
		Optional<File> isNullFile = Optional.ofNullable(paramFile);
		Boolean paramIsNull = !isNullFile.isPresent();

		// Folders path definition
		String tagName = generateTagName();
		Path resourcePath = Paths.get(DESTINATION_FOLDER + tagName);
		Path localSourceFile = Paths.get(SOURCE_FOLDER);

		// Extract a file from a local source or a provided source
		try (ZipInputStream zis = Boolean.TRUE.equals(paramIsNull)
				? new ZipInputStream(
						new FileInputStream(localSourceFile.toFile()))
				: new ZipInputStream(new FileInputStream(paramFile))) {

			ZipEntry zipEntry = zis.getNextEntry();

			while (zipEntry != null) {
				Path newFilePath = newFile(resourcePath, zipEntry);

				// Count and describe folders during the extraction process
				if (zipEntry.isDirectory()) {
					if (!Files.exists(newFilePath))
						Files.createDirectories(newFilePath);

					folderNum++;
				}

				// Count and describe files during the extraction process
				else {
					Path parent = newFilePath.getParent();
					if (!Files.exists(parent)) {
						Files.createDirectories(parent);
					}

					try (FileOutputStream fos = new FileOutputStream(
							newFilePath.toFile())) {
						int len;
						while ((len = zis.read(zipStreamBuffer)) > 0) {
							fos.write(zipStreamBuffer, 0, len);
						}

						specificFileTest(genericLinesList, newFilePath, test);
					}
					filesNum++;
				}
				zipEntry = zis.getNextEntry();
				counterNum++;
			}
		} catch (IOException e) {
			log.error("Error while extracting file: " + e.getMessage());
			throw e;
		}
		return genericLinesList;
	}

	private static void specificFileTest(List<List<String>> apiFilesLinesList,
			Path newFilePath, List<String> test) {

		if (test.contains("test") || test.contains("Api")) {
			List<String> ApiLineList = ApiTest(newFilePath);
			if (ApiLineList != null && !ApiLineList.isEmpty())
				apiFilesLinesList.add(ApiLineList);
		}

		if (test.contains("test") || test.contains("endpoints")) {
			List<String> endpointsLineList = endpointsTest(newFilePath);
			if (endpointsLineList != null && !endpointsLineList.isEmpty())
				apiFilesLinesList.add(endpointsLineList);
		}

		if (test.contains("test") || test.contains("public")) {
			List<String> publicLinesList = publicTest(newFilePath);
			if (publicLinesList != null && !publicLinesList.isEmpty())
				apiFilesLinesList.add(publicLinesList);
		}
		
		if (test.contains("test") || test.contains("funcAuths")) {
			List<String> funcAuthsLinesList = funcAuthsTest(newFilePath);
			if (funcAuthsLinesList != null && !funcAuthsLinesList.isEmpty())
				apiFilesLinesList.add(funcAuthsLinesList);
		}
		
		if (test.contains("test") || test.contains("aliases")) {
			List<String> aliasesLinesList = aliasesTest(newFilePath);
			if (aliasesLinesList != null && !aliasesLinesList.isEmpty())
				apiFilesLinesList.add(aliasesLinesList);
		}
		
		if (test.contains("test") || test.contains("funcaliases")) {
			List<String> funcaliasesLinesList = funcaliasesTest(newFilePath);
			if (funcaliasesLinesList != null && !funcaliasesLinesList.isEmpty())
				apiFilesLinesList.add(funcaliasesLinesList);
		}
		
		if (test.contains("test") || test.contains("microservices")) {
			List<String> microservicesLinesList = microservicesTest(newFilePath);
			if (microservicesLinesList != null && !microservicesLinesList.isEmpty())
				apiFilesLinesList.add(microservicesLinesList);
		}
	}

	// Generate unique name with date + time tag
	private static String generateTagName() {
		LocalDateTime dataOdierna = LocalDateTime.now();
		String pattern = "dd/MM/yyyy HH:mm:ss";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);

		return DOCUMENT_NAME + " extracted at " + dataOdierna.format(formatter)
				.replace('/', '.').replace(':', '.');
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

	// Api test
	private static List<String> ApiTest(Path filePath) {
		String fileName = filePath.getFileName().toString();
		String parentName = filePath.getParent().getFileName().toString();
		List<String> parentsDirectory = new ArrayList<>();
		List<String> apiLinesList = null;

		if (fileName.equals("api.json") && parentName.equals("common")) {
			apiLinesList = readFile(filePath, parentsDirectory);
		}
		return apiLinesList;
	}

	// Endpoints test
	private static List<String> endpointsTest(Path filePath) {
		String fileName = filePath.getFileName().toString();
		List<String> parentsDirectory = new ArrayList<>();
		List<String> endpointsLinesList = null;

		if (fileName.equals("endpoints.json")) {
			endpointsLinesList = readFile(filePath, parentsDirectory);
		}
		return endpointsLinesList;
	}

	// Public test
	private static List<String> publicTest(Path filePath) {
		String fileName = filePath.getFileName().toString();
		List<String> parentsDirectory = new ArrayList<>();
		List<String> publicLinesList = null;

		if (fileName.equals("public.js")) {
			publicLinesList = readFile(filePath, parentsDirectory);
		}
		return publicLinesList;
	}

	// FunctionalitiesAuths test
	private static List<String> funcAuthsTest(Path filePath) {
		String fileName = filePath.getFileName().toString();
		List<String> parentsDirectory = new ArrayList<>();
		List<String> funcAuthsLinesList = null;

		if (fileName.equals("functionalitiesAuths.json")) {
			funcAuthsLinesList = readFile(filePath, parentsDirectory);
		}
		return funcAuthsLinesList;
	}

	// Aliases test
	private static List<String> aliasesTest(Path filePath) {
		String fileName = filePath.getFileName().toString();
		List<String> parentsDirectory = new ArrayList<>();
		List<String> aliasesLinesList = null;

		if (fileName.equals("aliases.json")) {
			aliasesLinesList = readFile(filePath, parentsDirectory);
		}
		return aliasesLinesList;
	}

	// Functionaliases test
	private static List<String> funcaliasesTest(Path filePath) {
		String fileName = filePath.getFileName().toString();
		List<String> parentsDirectory = new ArrayList<>();
		List<String> funcaliasesLinesList = null;

		if (fileName.equals("functionaliases.json")) {
			funcaliasesLinesList = readFile(filePath, parentsDirectory);
		}
		return funcaliasesLinesList;
	}

	// Microservices test
	private static List<String> microservicesTest(Path filePath) {
		String fileName = filePath.getFileName().toString();
		List<String> parentsDirectory = new ArrayList<>();
		List<String> microservicesLinesList = null;

		if (fileName.equals("microservices.json")) {
			microservicesLinesList = readFile(filePath, parentsDirectory);
		}
		return microservicesLinesList;
	}

	private static List<String> readFile(Path filePath,
			List<String> parentsDirectory) {
		List<String> lineList = new ArrayList<>();
		// Get the directories containing the file
		Path parentPath = filePath.getParent();

		// Traverse up the directory hierarchy
		while (parentPath != null
				&& !parentPath.getFileName().toString().equals("webapp")) {
			Path parentName = parentPath.getFileName();
			if (parentName != null) {
				parentsDirectory.add(parentName.toString());
			}
			parentPath = parentPath.getParent();
		}
		Collections.reverse(parentsDirectory);

		// Save all lines of the file as a list of string
		try {
			lineList = Files.readAllLines(filePath);

			// String content = new String(Files.readAllBytes(filePath),
			// StandardCharsets.UTF_8);
			// log.info(" \n FileName: {} \n Directory:{} \n{}",
			// filePath.getFileName(), parentsDirectory, content);
		} catch (IOException e) {
			log.error("Error reading file {}", filePath, e);
		}

		return lineList;
	}
}
