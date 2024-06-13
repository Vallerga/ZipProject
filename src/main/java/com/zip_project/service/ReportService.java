package com.zip_project.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.zip_project.db.model.FileStatus;
import com.zip_project.service.costant.Costant;
import com.zip_project.service.crud.FileStatusService;

@Service
public class ReportService {

	private final FileStatusService fileStatusService;

	public ReportService(FileStatusService fileStatusService) {
		this.fileStatusService = fileStatusService;

	}

	public void compileReport(Integer reportNumber) {
		List<FileStatus> statusList = fileStatusService
				.findByReportNumber(reportNumber);
		for(FileStatus fileStatus : statusList) {
			fileStatus.setReportStatus(Costant.ReportStatus.COMPLETED);
			fileStatusService.updateFileStatus(fileStatus);
		}
	}
}
