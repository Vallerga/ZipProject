package com.zip_project.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.zip_project.model.FileStatus;

@Repository
public interface FileStatusDaoRepository
		extends
			CrudRepository<FileStatus, Long> {
	public List<FileStatus> findByfilePath(String filePath);
	public List<FileStatus> findByReportNumber(Integer reportNumber);
}
