package com.zip_project.db.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "ApiModels")
public class ApiModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idApiModel;
	
	private Boolean isMocked;
	
	private String name;
	
	private String baseUrl;
	
	private String endpoint;
	
	private String method;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "idApiList")
	@JsonBackReference
	private ApiList apiList;
}
