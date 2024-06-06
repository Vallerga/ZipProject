package com.zip_project.service.old.word;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StringLineListApproachOld {

	private StringLineListApproachOld() {
	}

	// Api test
	public static List<String> apiTest(Path filePath) {
		String fileName = filePath.getFileName().toString();

		List<String> parentsDirectory = new ArrayList<>();
		List<String> apiLinesList = null;

		if (fileName.equals("api.json")) {
			apiLinesList = readFile(filePath, parentsDirectory);
		}
		return apiLinesList;
	}

	private static List<String> readFile(Path filePath,
			List<String> parentsDirectory) {
		List<String> lineList = new ArrayList<>();
		// Get the directories containing the file
		Path parentPath = filePath.getParent();

		// Traverse up the directory hierarchy
		while (parentPath != null) {
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

			String content = new String(Files.readAllBytes(filePath),
					StandardCharsets.UTF_8);
			log.info(" \n FileName: {} \n Directory:{} \n{}",
					filePath.getFileName(), parentsDirectory, content);

		} catch (IOException e) {
			log.error("Error reading file {}", filePath, e);
		}

		return lineList;
	}
}
