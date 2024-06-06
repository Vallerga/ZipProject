package com.zip_project.db.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.zip_project.db.model.ApiList;

@Repository
public interface ApiListDaoRepository extends CrudRepository<ApiList, Long> {

}
