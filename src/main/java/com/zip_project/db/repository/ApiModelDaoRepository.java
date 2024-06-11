package com.zip_project.db.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.zip_project.db.model.ApiModel;

@Repository
public interface ApiModelDaoRepository extends CrudRepository<ApiModel, Long> {
	List<ApiModel> findByApiList_idApiList(Long idApiList);
}
