package com.zip_project.db.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
@Table(name = "JsonLineLists")
public class JsonLineList {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idJsonLineList;
	
	@JsonIgnore
	@ToString.Exclude
	private List<String> lineCodeList;
	
	@OneToOne
    @JoinColumn(name = "idFileStatus")
	@JsonBackReference
    private FileStatus fileStatus;
}
