package com.zip_project.db.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.zip_project.service.costant.Costant.ExtractStatus;
import com.zip_project.service.costant.Costant.JsonValidation;
import com.zip_project.service.costant.Costant.ReportStatus;
import com.zip_project.service.costant.Costant.TestStatus;

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

	private ExtractStatus extractStatus;

	private JsonValidation jsonValidationStatus;

	private TestStatus testStatus;

	private ReportStatus reportStatus;

	@OneToOne(mappedBy = "fileStatus", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JsonManagedReference
	@ToString.Exclude
	private ModuleDefaults moduleDefaults;

	@OneToOne(mappedBy = "fileStatus", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JsonManagedReference
	@ToString.Exclude
	private JsonLineList jsonLineList;
}
