package com.zip_project.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "FilesStatus")
public class FileStatus {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idFileStatus;

	private String fileName;

	private String rootName;

	private String filePath;
	
	private Integer reportNumber;

	private String extractFileStatus;

	private String jsonValidationStatus;

	private String dataTestStatus;
}
