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
import java.util.List;
import java.util.Optional;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.zip_project.db.model.FileStatus;
import com.zip_project.service.costant.Costant;
import com.zip_project.service.costant.Costant.ExtractStatus;
import com.zip_project.service.costant.Costant.JsonValidation;
import com.zip_project.service.crud.FileStatusService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ExtractFileService {

	private final FileStatusService fileStatusService;

	public ExtractFileService(FileStatusService fileStatusService) {
		this.fileStatusService = fileStatusService;
	}

	@Value("${folder.destination}")
	private String destinationFolder;
	@Value("${folder.source}")
	private String sourceFolder;

	public String extractFileManager(File paramFile)
			throws IOException, DataAccessException {
		byte[] zipStreamBuffer = new byte[1024];
		ZipInputStream zipInputStream = null;
		ZipEntry zipEntry;
		Integer numReport = generateNumReport();

		// folder path definition
		Path localSourceFile = Paths.get(sourceFolder);
		String rootName = generateRootName(paramFile, localSourceFile);
		Path resourcePath = Paths.get(destinationFolder + rootName);

		zipInputStream = loadLocalOrParamFile(paramFile, localSourceFile);

		zipEntry = zipInputStream.getNextEntry();

		iterateStreamFile(zipStreamBuffer, zipInputStream, resourcePath,
				zipEntry, numReport, rootName);

		return numReport.toString();
	}

	private void iterateStreamFile(byte[] zipStreamBuffer,
			ZipInputStream zipInputStream, Path resourcePath, ZipEntry zipEntry,
			Integer numReport, String rootName)
			throws IOException, DataAccessException {
		FileStatus fileStatus = null;
		while (zipEntry != null) {
			Path newFilePath = newFile(resourcePath, zipEntry);

			// restore the path of the compressed file.
			if (zipEntry.isDirectory()) {
				if (!Files.exists(newFilePath))
					Files.createDirectories(newFilePath);
			}

			else {
				// fix for Windows-created archives
				Path parent = newFilePath.getParent();
				if (!Files.exists(parent)) {
					Files.createDirectories(parent);
				}

				fileStatus = extractStatusManager(newFilePath,
						Costant.ExtractStatus.INITIALIZED, null, numReport,
						rootName);

				// write file content
				try (FileOutputStream fos = new FileOutputStream(
						newFilePath.toFile())) {

					int len;
					while ((len = zipInputStream.read(zipStreamBuffer)) > 0) {
						fos.write(zipStreamBuffer, 0, len);
					}
				}

				extractStatusManager(newFilePath, Costant.ExtractStatus.CREATED,
						fileStatus, numReport, rootName);
			}
			zipEntry = zipInputStream.getNextEntry();
		}
	}

	private ZipInputStream loadLocalOrParamFile(File paramFile,
			Path localSourceFile) throws IOException {
		ZipInputStream zis = null;

		// check provided source presence
		Optional<File> isNullFile = Optional.ofNullable(paramFile);
		Boolean paramIsNull = !isNullFile.isPresent();

		// extract a file from a local source or a provided source
		if (Boolean.TRUE.equals(paramIsNull)) {
			zis = new ZipInputStream(
					new FileInputStream(localSourceFile.toFile()));
		} else {
			zis = new ZipInputStream(new FileInputStream(paramFile));
		}

		return zis;
	}

	// generate unique name with date + time tag
	private String generateRootName(File paramFile, Path localSourceFile) {
		LocalDateTime dataOdierna = LocalDateTime.now();
		String pattern = "dd/MM/yyyy HH:mm:ss";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		String fileName;

		if (paramFile != null) {
			fileName = paramFile.getName();
		} else {
			fileName = localSourceFile.getFileName().toString();
		}

		return fileName + " extracted at " + dataOdierna.format(formatter)
				.replace('/', '.').replace(':', '.');
	}

	private static Path newFile(Path destinationDir, ZipEntry zipEntry)
			throws IOException {
		Path rootName = destinationDir.resolve(zipEntry.getName()).normalize();

		if (!rootName.startsWith(destinationDir)) {
			throw new IOException("Entry is outside of the target dir: "
					+ zipEntry.getName());
		}

		return rootName;
	}

	public FileStatus extractStatusManager(Path filePath,
			ExtractStatus extractStatus, FileStatus paramES, Integer numReport,
			String rootName) throws DataAccessException {
		FileStatus fileStatus = null;
		Optional<FileStatus> optionalFileStatus = Optional.ofNullable(paramES);
		Boolean paramNotNull = optionalFileStatus.isPresent();
		List<FileStatus> esList;

		if (Boolean.TRUE.equals(paramNotNull)) {
			esList = fileStatusService.findByfilePath(paramES.getFilePath());
			paramES.setIdFileStatus(esList.get(0).getIdFileStatus());
			fileStatusService.insertFileStatus(paramES);
			return fileStatus;
		} else if (filePath != null) {
			fileStatus = FileStatus.builder()
					.fileName(filePath.getFileName().toString())
					.rootName(rootName).filePath(filePath.toString())
					.reportNumber(numReport).extractStatus(extractStatus)
					.jsonValidationStatus(JsonValidation.NOT_VALIDATED)
					.testStatus(Costant.TestStatus.NOT_TESTED)
					.reportStatus(Costant.ReportStatus.ABSENT).build();
			log.debug("EXTRACT STATUS: {}", fileStatus);

			fileStatusService.insertFileStatus(fileStatus);
		} else {
			log.debug("Extract Status not created, filePath equal Null");
		}
		return fileStatus;
	}

	public Integer generateNumReport() throws DataAccessException {
		Integer numReport = 0;
		List<FileStatus> fileStatusList = fileStatusService.findAll();
		for (FileStatus fileStatus : fileStatusList) {
			if (fileStatus != null && fileStatus.getReportNumber() != null
					&& fileStatus.getReportNumber() > numReport) {
				numReport = fileStatus.getReportNumber();
			}
		}
		if (numReport != null) {
			numReport++;
		}
		return numReport;
	}
}
