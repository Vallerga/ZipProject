package com.zip_project.db.model;

import java.util.List;
import java.util.Objects;

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
	@JoinColumn(name = "idModuleDefaults")
	@JsonBackReference
	private ModuleDefaults moduleDefaults;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "idApiModel")
	@JsonManagedReference
	@JsonIgnore
	@ToString.Exclude
	private List<ApiModel> apiModels;

	@Override
	public int hashCode() {
		return Objects.hash(idApiList);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		ApiList apiList = (ApiList) o;
		return Objects.equals(idApiList, apiList.idApiList);
	}
}
