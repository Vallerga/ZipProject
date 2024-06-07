package com.zip_project.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zip_project.db.model.ApiList;
import com.zip_project.db.model.ApiModel;
import com.zip_project.db.model.ModuleDefaults;
import com.zip_project.service.costant.Costant;
import com.zip_project.service.crud.FileStatusService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DataTestService {

	private final FileStatusService fileStatusService;
	private final DataParseService dataParseService;

	public DataTestService(FileStatusService fileStatusService,
			DataParseService dataParseService) {
		this.dataParseService = dataParseService;
		this.fileStatusService = fileStatusService;
	}

	@Transactional
	public List<ModuleDefaults> dataTest(Integer reportNumber) {
		Map<String, Map<Integer, List<ApiList>>> allFileApiMap = new HashMap<>();
		List<ModuleDefaults> moduleDefaultsList = dataParseService
				.parseJsonManager(reportNumber);
		Hibernate.initialize(moduleDefaultsList.get(1).getApiList());
		for (ApiList a : moduleDefaultsList.get(1).getApiList()) {
			Hibernate.initialize(a.getApiModels());
			for (ApiModel apiModel : a.getApiModels()) {
				log.info(apiModel.toString());
			}
		}

		if (moduleDefaultsList != null && !moduleDefaultsList.isEmpty()) {
			for (int i = 1; i < moduleDefaultsList.size(); i++) {
				ModuleDefaults moduleDefaults = moduleDefaultsList.get(i);
				Hibernate.initialize(moduleDefaults.getApiList());
				List<ApiList> apiListCollection = moduleDefaults.getApiList();
				apiListAllocator(apiListCollection, i, allFileApiMap);
			}

			Map<Integer, List<ApiList>> genericApiListsMap = allFileApiMap
					.get(Costant.API_LIST_GENERIC);
			if (genericApiListsMap != null) {
				for (Map.Entry<Integer, List<ApiList>> entry : genericApiListsMap
						.entrySet()) {
					int fileIndex = entry.getKey();
					List<ApiList> apiLists = entry.getValue();
					for (ApiList apiList : apiLists) {
						Hibernate.initialize(apiList.getApiModels());
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

// public List<ModuleDefaults> dataTest(Integer reportNumber) {
// List<List<ApiList>> allFileApiList = new ArrayList<>();
// ModuleDefaults moduleDefaults;
// List<ApiList> apiListCollSingleFile;
// List<ModuleDefaults> moduleDefaultsList = dataParseService
// .parseJsonManager(reportNumber);
//
// if (moduleDefaultsList != null && !moduleDefaultsList.isEmpty()) {
// // test only env file
// for (Integer i = 0; i < moduleDefaultsList.size(); i++) {
// if (i == 0)
// continue;
//
// moduleDefaults = moduleDefaultsList.get(i);
// apiListCollSingleFile = moduleDefaults.getApiList();
// apiListAllocator(apiListCollSingleFile, allFileApiList);
// }
// for (ApiList apiList : allFileApiList.get(0))
// for (ApiModel model : apiList.getApiModels()) {
// log.info("model: {}", model);
// }
// }
// return moduleDefaultsList;
// }

// public List<ModuleDefaults> dataTest(Integer reportNumber) {
// Map<String, List<ApiList>> allFileApiMap = new HashMap<>();
// ModuleDefaults moduleDefaults;
// List<ApiList> apiListCollection;
// List<ModuleDefaults> moduleDefaultsList = dataParseService
// .parseJsonManager(reportNumber);
//
// if (moduleDefaultsList != null && !moduleDefaultsList.isEmpty()) {
// // Test only env file
// for (int i = 1; i < moduleDefaultsList.size(); i++) {
// moduleDefaults = moduleDefaultsList.get(i);
// apiListCollection = moduleDefaults.getApiList();
// apiListAllocator(apiListCollection, allFileApiMap);
// }
//
// // Example of logging the models
// List<ApiList> genericApiLists = allFileApiMap
// .get(Costant.API_LIST_GENERIC);
// if (genericApiLists != null) {
// for (ApiList apiList : genericApiLists) {
// for (ApiModel model : apiList.getApiModels()) {
// log.info("model: {}", model);
// }
// }
// }
// }
// return moduleDefaultsList;
// }
//
// private void apiListAllocator(List<ApiList> apiListCollSingleFile,
// Map<String, List<ApiList>> allFileApiMap) {
// for (ApiList apiList : apiListCollSingleFile) {
// String name = apiList.getName();
// allFileApiMap.computeIfAbsent(name, k -> new ArrayList<>())
// .add(apiList);
// }
// }
//
// private void apiListAllocator2(List<ApiList> apiListCollSingleFile,
// List<List<ApiList>> allFileApiList) {
// List<ApiList> prodottiGeneric = new ArrayList<>();
// List<ApiList> pratica = new ArrayList<>();
// List<ApiList> prodottiMapping = new ArrayList<>();
// List<ApiList> workflow = new ArrayList<>();
// List<ApiList> allegati = new ArrayList<>();
// List<ApiList> contrattoQuadro = new ArrayList<>();
//
// for (ApiList apiList : apiListCollSingleFile) {
// String name = apiList.getName();
// switch (name) {
// case Costant.API_LIST_GENERIC :
// prodottiGeneric.add(apiList);
// break;
// case Costant.API_LIST_PRATICA :
// pratica.add(apiList);
// break;
// case Costant.API_LIST_MAPPING :
// prodottiMapping.add(apiList);
// break;
// case Costant.API_LIST_WORKFLOW :
// workflow.add(apiList);
// break;
// case Costant.API_LIST_ALLEGATI :
// allegati.add(apiList);
// break;
// case Costant.API_LIST_QUADRO :
// contrattoQuadro.add(apiList);
// break;
// default :
// break;
// }
//
// if (!prodottiGeneric.isEmpty())
// allFileApiList.add(prodottiGeneric);
// if (!pratica.isEmpty())
// allFileApiList.add(pratica);
// if (!prodottiMapping.isEmpty())
// allFileApiList.add(prodottiMapping);
// if (!workflow.isEmpty())
// allFileApiList.add(workflow);
// if (!allegati.isEmpty())
// allFileApiList.add(allegati);
// if (!contrattoQuadro.isEmpty())
// allFileApiList.add(contrattoQuadro);
// }
// }
