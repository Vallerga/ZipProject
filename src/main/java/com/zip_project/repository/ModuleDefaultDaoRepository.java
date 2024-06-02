package com.zip_project.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.zip_project.model.ModuleDefaults;

@Repository
public interface ModuleDefaultDaoRepository extends CrudRepository<ModuleDefaults, Long> {

}
