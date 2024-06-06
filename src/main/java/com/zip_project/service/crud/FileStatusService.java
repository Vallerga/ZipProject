package com.zip_project.service.crud;

import java.util.List;

import org.springframework.stereotype.Service;

import com.zip_project.model.FileStatus;
import com.zip_project.repository.FileStatusDaoRepository;

@Service
public class FileStatusService {

	private final FileStatusDaoRepository fileStatusDao;

	public FileStatusService(FileStatusDaoRepository extractStatusDao) {
		this.fileStatusDao = extractStatusDao;
	}

	public String insertFileStatus(FileStatus extractStatus) {
		fileStatusDao.save(extractStatus);
		return "EXTRACT STATUS SAVED";
	}

	public String updateFileStatus(FileStatus md) {
		fileStatusDao.save(md);
		return "EXTRACT STATUS UPDATED";
	}

	public String removeFileStatusById(Long id) {
		fileStatusDao.deleteById(id);
		return "EXTRACT STATUS REMOVED";
	}

	public String removeAll() {
		fileStatusDao.deleteAll();
		return "ALL EXTRACT STATUS REMOVED";
	}

	public FileStatus findFileStatusById(Long id) {
		return fileStatusDao.findById(id).orElse(null);
	}

	public List<FileStatus> findAll() {
		return (List<FileStatus>) fileStatusDao.findAll();
	}

	public List<FileStatus> findByfilePath(String filePath) {
		return fileStatusDao.findByfilePath(filePath);
	}

	public List<FileStatus> findByReportNumber(Integer reportNumber) {
		return fileStatusDao.findByReportNumber(reportNumber);
	}
}
