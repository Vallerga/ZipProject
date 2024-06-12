package com.zip_project.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.stereotype.Service;

import com.zip_project.db.model.ApiModel;
import com.zip_project.db.model.FileStatus;
import com.zip_project.service.costant.Costant;
import com.zip_project.service.costant.Costant.testStatus;
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
		Map<String, Map<Long, List<ApiModel>>> compareNameApiListMap = new HashMap<>();
		Map<Long, Map<String, List<ApiModel>>> allFilesMap = fileStatusService
				.getApiModelsByReportNumberAndApiListNames(reportNumber);
		
		List<FileStatus> statusList = fileStatusService
				.findByReportNumber(reportNumber);
		
		try {
			printSingleApiList(allFilesMap);
			
			loadCompareNameApiListMap(compareNameApiListMap, allFilesMap);
			
			sortApiListByName(compareNameApiListMap);
			
			updateFileStatus(statusList, testStatus.TESTED);
		} catch (Exception e) {
			updateFileStatus(statusList, testStatus.NOT_TESTED);
			e.printStackTrace();
		}
	}
	
	private void updateFileStatus(List<FileStatus> statusList, testStatus value) {
		for(FileStatus fileStatus : statusList) {
			fileStatus.setDataTestStatus(value);
			fileStatusService.updateFileStatus(fileStatus);
		}
}
	private void loadCompareNameApiListMap(
			Map<String, Map<Long, List<ApiModel>>> compareNameApiListMap,
			Map<Long, Map<String, List<ApiModel>>> allFilesMap) {

		Map<Long, List<ApiModel>> prodGenericMap = new HashMap<>();
		Map<Long, List<ApiModel>> praticaMap = new HashMap<>();
		Map<Long, List<ApiModel>> prodMappingMap = new HashMap<>();
		Map<Long, List<ApiModel>> workflowMap = new HashMap<>();
		Map<Long, List<ApiModel>> allegatiMap = new HashMap<>();
		Map<Long, List<ApiModel>> contQuadroMap = new HashMap<>();

		for (Long i = 1l; i < allFilesMap.size(); i++) {
			Map<String, List<ApiModel>> singleFileMap = allFilesMap.get(i);

			prodGenericMap.put(i,
					singleFileMap.get(Costant.ApiListName.GENERIC.getValue()));

			praticaMap.put(i,
					singleFileMap.get(Costant.ApiListName.PRATICA.getValue()));

			prodMappingMap.put(i,
					singleFileMap.get(Costant.ApiListName.MAPPING.getValue()));

			workflowMap.put(i,
					singleFileMap.get(Costant.ApiListName.WORKFLOW.getValue()));

			allegatiMap.put(i,
					singleFileMap.get(Costant.ApiListName.ALLEGATI.getValue()));

			contQuadroMap.put(i,
					singleFileMap.get(Costant.ApiListName.QUADRO.getValue()));
		}

		compareNameApiListMap.put(Costant.ApiListName.GENERIC.getValue(),
				prodGenericMap);
		compareNameApiListMap.put(Costant.ApiListName.PRATICA.getValue(), praticaMap);
		compareNameApiListMap.put(Costant.ApiListName.MAPPING.getValue(),
				prodMappingMap);
		compareNameApiListMap.put(Costant.ApiListName.WORKFLOW.getValue(), workflowMap);
		compareNameApiListMap.put(Costant.ApiListName.ALLEGATI.getValue(), allegatiMap);
		compareNameApiListMap.put(Costant.ApiListName.QUADRO.getValue(), contQuadroMap);
	}

	private void sortApiListByName(
			Map<String, Map<Long, List<ApiModel>>> compareNameApiListMap) throws Exception {
		for (Map.Entry<String, Map<Long, List<ApiModel>>> compareNameApiListMapEntry : compareNameApiListMap
				.entrySet()) {
			String apiListName = compareNameApiListMapEntry.getKey();
			Map<Long, List<ApiModel>> apiListByNameMap = compareNameApiListMapEntry
					.getValue();
			analizeApiListByName(apiListByNameMap, apiListName);
		}
	}

	private void analizeApiListByName(Map<Long, List<ApiModel>> apiListByNameMap,
			String apiListName) throws Exception {
		List<ApiModel> baseApiList = null;
		List<ApiModel> compareApiList = null;
		Long baseFileNumber = null;
		Long compareFileNumber = null;
		for (Entry<Long, List<ApiModel>> apiListByNameMapEntry : apiListByNameMap
				.entrySet()) {

			if (apiListByNameMapEntry.getValue() == null) {
				log.debug("baseApiList: {}", baseApiList);
				log.debug("compareApiList: {}", compareApiList);
				log.info("apiListName: " + apiListName);
				continue;
			}

			if (apiListByNameMapEntry.getKey() == 1)
				baseFileNumber = apiListByNameMapEntry.getKey();
			else
				compareFileNumber = apiListByNameMapEntry.getKey();

			if (apiListByNameMapEntry.getKey() == 1)
				baseApiList = apiListByNameMapEntry.getValue();
			else
				compareApiList = apiListByNameMapEntry.getValue();

			if (compareFileNumber == null && compareApiList == null) {
				continue;
			}

			log.debug("baseApiList: {}", baseApiList);
			log.debug("compareApiList: {}", compareApiList);
			log.info("comparing apiList: " + apiListName);

			List<String> resultList = new ArrayList<>();

			apiListComparator(apiListName, baseApiList, compareApiList,
					baseFileNumber, compareFileNumber, resultList);
		}
	}

	private void apiListComparator(String apiListName, List<ApiModel> baseApiList,
			List<ApiModel> compareApiList, Long baseFileNumber,
			Long compareFileNumber, List<String> resultList) throws Exception {
		Boolean result = false;

		for (ApiModel baseApiModel : baseApiList) {
			for (ApiModel compareApiModel : compareApiList) {
				if (baseApiModel.getName().equals(compareApiModel.getName())) {
					result = true;
				}
			}

			if (Boolean.FALSE.equals(result)) {
				String error = " Not present:" + baseApiModel.getName();
				resultList.add(error);
				throw new Exception("comparison failed, apiList named: "
						+ apiListName + " at file number: " + baseFileNumber
						+ " not match any api at file: " + compareFileNumber);
			}
			// reset value
			result = false;

			if (resultList.isEmpty())
				resultList.add("all comparison success");

			// for (String error : resultList) {
			// log.info(error);
			// }
		}
	}

	private void printSingleApiList(
			Map<Long, Map<String, List<ApiModel>>> allFileMap) {
		for (Map.Entry<Long, Map<String, List<ApiModel>>> singleFileApiModelsMap : allFileMap
				.entrySet()) {
			Long fileId = singleFileApiModelsMap.getKey();
			Map<String, List<ApiModel>> apiModelsListMap = singleFileApiModelsMap
					.getValue();

			for (Map.Entry<String, List<ApiModel>> apiModelsEntry : apiModelsListMap
					.entrySet()) {
				String apiListName = apiModelsEntry.getKey();
				List<ApiModel> apiModelList = apiModelsEntry.getValue();

				log.debug(
						"Id ModuleDefaults: {} - ApiList Name: {} - ApiModels: {}",
						fileId, apiListName, apiModelList);
			}
		}
	}
}
