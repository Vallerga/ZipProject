package com.zip_project.service.crud;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.zip_project.db.model.ApiList;
import com.zip_project.db.model.ApiModel;
import com.zip_project.db.model.FileStatus;
import com.zip_project.db.model.ModuleDefaults;
import com.zip_project.db.repository.FileStatusDaoRepository;
import com.zip_project.service.costant.Costant;

@Service
public class FileStatusService {

	private final FileStatusDaoRepository fileStatusDao;
	private final ApiListService apiListService;
	private final ApiModelService apiModelService;

	public FileStatusService(FileStatusDaoRepository extractStatusDao,
			ApiListService apiListService, ApiModelService apiModelService) {
		this.fileStatusDao = extractStatusDao;
		this.apiListService = apiListService;
		this.apiModelService = apiModelService;
	}

	public String insertFileStatus(FileStatus fileStatus) {
		fileStatusDao.save(fileStatus);
		return "EXTRACT STATUS SAVED";
	}

	public String updateFileStatus(FileStatus fileStatus) {
		fileStatusDao.save(fileStatus);
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

	public Map<Long, Map<String, List<ApiModel>>> getApiModelsByReportNumberAndApiListNames(
			Integer reportNumber) {
		Map<Long, Map<String, List<ApiModel>>> allFilesApiModelsMap = new HashMap<>();

		List<FileStatus> fileStatusByReportNumber = fileStatusDao
				.findByReportNumber(reportNumber);

		for (FileStatus singleFileStatus : fileStatusByReportNumber) {
			ModuleDefaults moduleDefaults = singleFileStatus.getModuleDefaults();

			// initialize a new map for each file
			if (moduleDefaults != null) {
				Map<String, List<ApiModel>> apiModelsByFileMap = new HashMap<>();
				List<ApiList> apiListByModuleDefaultId = apiListService
						.getApiListsByModuleDefaultsId(
								moduleDefaults.getIdModuleDefaults());

				for (ApiList apiListSelected : apiListByModuleDefaultId) {
					for (String apiListName : Costant.getApiListNames()) {
						if (apiListSelected.getName().equals(apiListName)) {
							List<ApiModel> apiModels = apiModelService
									.getApiListsByModuleDefaultsId(
											apiListSelected.getIdApiList());

							// append to the existing list if the key is already present
							apiModelsByFileMap
									.computeIfAbsent(apiListName,
											k -> new ArrayList<>())
									.addAll(apiModels);
						}
					}
				}
				allFilesApiModelsMap.put(moduleDefaults.getIdModuleDefaults(),
						apiModelsByFileMap);
			}
		}
		return allFilesApiModelsMap;
	}
}
