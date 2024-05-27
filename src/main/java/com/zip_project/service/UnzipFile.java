package com.zip_project.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.poi.xwpf.usermodel.XWPFDocument;

import com.zip_project.service.word.WordGenerator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UnzipFile {

	private UnzipFile() {
	}

	private static final String DESTINATION_FOLDER = "C:/sviluppo/java_workspaces/Jersey/ZipProject/src/main/resources/unzipFile/";
	private static final String SOURCE_FOLDER = "C:/sviluppo/java_workspaces/Jersey/ZipProject/src/main/resources/zipFile/webapp.zip";
	private static final String DOCUMENT_NAME = "WEBAPP";

	public static void unzipFileManager(File paramFile) throws IOException {
		byte[] zipStreamBuffer = new byte[1024];
		XWPFDocument document;
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

			// Initialize report document
			document = WordGenerator.generateWord(DOCUMENT_NAME);
			WordGenerator.summary(counterNum, folderNum, filesNum, document);

			WordGenerator.subtitle(document, "FILE DESCRIPTION");
			while (zipEntry != null) {
				Path newFilePath = newFile(resourcePath, zipEntry);

				// Count and describe folders during the extraction process
				if (zipEntry.isDirectory()) {
					if (!Files.exists(newFilePath))
						Files.createDirectories(newFilePath);

					folderNum++;
					paragraphCreator(newFilePath, true, document);
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
					}

					filesNum++;
					paragraphCreator(newFilePath, false, document);
				}
				zipEntry = zis.getNextEntry();
				counterNum++;
			}
			WordGenerator.documentWrite(document, resourcePath, DOCUMENT_NAME);
		} catch (IOException e) {
			log.error("Error while extracting file: " + e.getMessage());
			throw e;
		}
	}

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

	private static void paragraphCreator(Path filePath, Boolean isFolder,
			XWPFDocument document) throws IOException {
		List<String> fileAttributes = new ArrayList<>();

		fileAttributes.add("File name: ");
		fileAttributes.add(filePath.getFileName().toString());

		if (Boolean.TRUE.equals(isFolder)) {
			fileAttributes.add("Is folder: ");
			fileAttributes.add("true");
		} else {
			fileAttributes.add("Is folder: ");
			fileAttributes.add("false");
		}

		if (Boolean.FALSE.equals(isFolder) && Files.isReadable(filePath)) {
			fileAttributes.add("File size: ");
			Long size = Files
					.readAttributes(filePath, BasicFileAttributes.class).size();
			fileAttributes.add(size.toString());
		} else {
			fileAttributes.add("File size: ");
			fileAttributes.add("0");
		}

		WordGenerator.describeFile(document, fileAttributes);
	}

}
