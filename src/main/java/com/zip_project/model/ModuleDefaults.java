package com.zip_project.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
@Table(name = "ModuleDefaults")
public class ModuleDefaults {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idModuleDefaults;
	
	private String path;
	
	private String protocol;
	
	private String host;
	
	private String baseUrl;
	
	private String security;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "idApiList")
	@JsonManagedReference
	@JsonIgnore
	@ToString.Exclude
	private List<ApiList> apisList;
}
