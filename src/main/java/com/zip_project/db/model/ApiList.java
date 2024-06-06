package com.zip_project.db.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "ApiLists")
public class ApiList {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idApiList;

	private String name;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "idModuleDefault")
	@JsonBackReference
	private ModuleDefaults moduleDefault;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "idApiModel")
	@JsonManagedReference
	@JsonIgnore
	@ToString.Exclude
	private List<ApiModel> apiModels;
}
