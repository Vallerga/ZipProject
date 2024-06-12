package com.zip_project.db.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.zip_project.service.costant.Costant.JsonValidation;
import com.zip_project.service.costant.Costant.extractStatus;
import com.zip_project.service.costant.Costant.testStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

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

	private extractStatus extractStatus;

	private JsonValidation jsonValidationStatus;

	private testStatus dataTestStatus;

	@OneToOne(mappedBy = "fileStatus", cascade = CascadeType.ALL, fetch = FetchType.EAGER, optional = false)
	@JsonManagedReference
	@ToString.Exclude
	private ModuleDefaults moduleDefaults;
}
