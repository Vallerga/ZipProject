package com.zip_project.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.zip_project.db.model.ApiModel;
import com.zip_project.service.crud.FileStatusService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DataTestService {

	private final FileStatusService fileStatusService;

	public DataTestService(FileStatusService fileStatusService) {
		this.fileStatusService = fileStatusService;
	}
	public void dataTest(Integer reportNumber) {
		Map<Long, Map<String, List<ApiModel>>> selectedModelList = fileStatusService
				.getApiModelsByReportNumberAndApiListNames(reportNumber);

		for (Map.Entry<Long, Map<String, List<ApiModel>>> entryModelList : selectedModelList
				.entrySet()) {
			Long moduleDefaultsId = entryModelList.getKey();
			Map<String, List<ApiModel>> apiModelsByFileMap = entryModelList
					.getValue();

			for (Map.Entry<String, List<ApiModel>> entry : apiModelsByFileMap
					.entrySet()) {
				String apiListName = entry.getKey();
				List<ApiModel> apiModels = entry.getValue();

				log.info(
						"Id ModuleDefaults: {} - ApiList Name: {} - ApiModels: {}",
						moduleDefaultsId, apiListName, apiModels);
			}
		}
	}
}
