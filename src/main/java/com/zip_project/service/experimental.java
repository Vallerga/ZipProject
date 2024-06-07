package com.zip_project.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zip_project.db.model.ApiList;
import com.zip_project.db.model.ApiModel;
import com.zip_project.db.model.ModuleDefaults;
import com.zip_project.service.costant.Costant;
import com.zip_project.service.crud.FileStatusService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class experimental {
	private final FileStatusService fileStatusService;
	private final DataParseService dataParseService;

	public experimental(FileStatusService fileStatusService,
			DataParseService dataParseService) {
		this.dataParseService = dataParseService;
		this.fileStatusService = fileStatusService;
	}
	
	public List<ModuleDefaults> dataTest(Integer reportNumber) {
		Map<String, Map<Integer, List<ApiList>>> allFileApiMap = new HashMap<>();
		ModuleDefaults moduleDefaults;
		List<ApiList> apiListCollection;
		List<ModuleDefaults> moduleDefaultsList = dataParseService
				.parseJsonManager(reportNumber);

		if (moduleDefaultsList != null && !moduleDefaultsList.isEmpty()) {
			// Test only env file
			for (int i = 1; i < moduleDefaultsList.size(); i++) {
				moduleDefaults = moduleDefaultsList.get(i);
				apiListCollection = moduleDefaults.getApiList();
				apiListAllocator(apiListCollection, i, allFileApiMap);
			}

			// Example of logging the models
			Map<Integer, List<ApiList>> genericApiListsMap = allFileApiMap
					.get(Costant.API_LIST_GENERIC);
			if (genericApiListsMap != null) {
				for (Map.Entry<Integer, List<ApiList>> entry : genericApiListsMap
						.entrySet()) {
					int fileIndex = entry.getKey();
					List<ApiList> apiLists = entry.getValue();
					for (ApiList apiList : apiLists) {
						for (ApiModel model : apiList.getApiModels()) {
							log.info("fileIndex: {}, model: {}", fileIndex,
									model);
						}
					}
				}
			}
		}
		return moduleDefaultsList;
	}

	private void apiListAllocator(List<ApiList> apiListCollSingleFile,
			int fileIndex,
			Map<String, Map<Integer, List<ApiList>>> allFileApiMap) {
		for (ApiList apiList : apiListCollSingleFile) {
			String name = apiList.getName();
			allFileApiMap.computeIfAbsent(name, k -> new HashMap<>())
					.computeIfAbsent(fileIndex, k -> new ArrayList<>())
					.add(apiList);
		}
	}
}
