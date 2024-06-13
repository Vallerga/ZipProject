package com.zip_project.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.zip_project.db.model.ApiModel;
import com.zip_project.db.model.FileStatus;
import com.zip_project.service.costant.Costant;
import com.zip_project.service.costant.Costant.TestStatus;
import com.zip_project.service.crud.FileStatusService;
import com.zip_project.service.exception.DataParsingException;
import com.zip_project.service.exception.DatabaseOperationException;
import com.zip_project.service.exception.TestExecutionException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DataTestService {

	private final FileStatusService fileStatusService;

	public DataTestService(FileStatusService fileStatusService) {
		this.fileStatusService = fileStatusService;
	}
	public List<String> dataTest(Integer reportNumber)
			throws TestExecutionException, DataAccessException,
			DataParsingException {
		List<String> errorList = new ArrayList<>();
		Map<String, Map<Long, List<ApiModel>>> compareNameApiListMap = new HashMap<>();

		try {
			List<FileStatus> statusList = fileStatusService
					.findByReportNumber(reportNumber);
			Map<Long, Map<String, List<ApiModel>>> allFilesMap = fileStatusService
					.getApiModelsByReportNumberAndApiListNames(reportNumber);

			loadCompareNameApiListMap(compareNameApiListMap, allFilesMap);

			sortApiListByName(compareNameApiListMap, errorList);

			updateFileStatus(statusList, TestStatus.TESTED);
		} catch (DataAccessException e) {
			throw new DatabaseOperationException(
					"An error occurred while accessing the database: file status non accessible.");
		}
		return errorList;
	}

	private void updateFileStatus(List<FileStatus> statusList,
			TestStatus value) {
		try {
			for (FileStatus fileStatus : statusList) {
				fileStatus.setTestStatus(value);
				fileStatusService.updateFileStatus(fileStatus);
			}
		} catch (DataAccessException e) {
			throw new DatabaseOperationException(
					"An error occurred while accessing the database: file status non accessible.");
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
		compareNameApiListMap.put(Costant.ApiListName.PRATICA.getValue(),
				praticaMap);
		compareNameApiListMap.put(Costant.ApiListName.MAPPING.getValue(),
				prodMappingMap);
		compareNameApiListMap.put(Costant.ApiListName.WORKFLOW.getValue(),
				workflowMap);
		compareNameApiListMap.put(Costant.ApiListName.ALLEGATI.getValue(),
				allegatiMap);
		compareNameApiListMap.put(Costant.ApiListName.QUADRO.getValue(),
				contQuadroMap);
	}

	private void sortApiListByName(
			Map<String, Map<Long, List<ApiModel>>> compareNameApiListMap,
			List<String> errorList) throws TestExecutionException {
		for (Map.Entry<String, Map<Long, List<ApiModel>>> compareNameApiListMapEntry : compareNameApiListMap
				.entrySet()) {
			String apiListName = compareNameApiListMapEntry.getKey();
			Map<Long, List<ApiModel>> apiListByNameMap = compareNameApiListMapEntry
					.getValue();
			analizeApiListByName(apiListByNameMap, apiListName, errorList);
		}
	}

	private void analizeApiListByName(
			Map<Long, List<ApiModel>> apiListByNameMap, String apiListName,
			List<String> errorList) throws TestExecutionException {
		String error = "";
		List<ApiModel> baseApiList = null;
		List<ApiModel> compareApiList = null;
		Long baseFileNumber = null;
		Long compareFileNumber = null;
		for (Entry<Long, List<ApiModel>> apiListByNameMapEntry : apiListByNameMap
				.entrySet()) {
			baseFileNumber = apiListByNameMapEntry.getKey();
			if (apiListByNameMapEntry.getValue() == null) {
				error = "At file number: " + baseFileNumber
						+ ", apiList named: " + apiListName
						+ " not match any ApiList name. \n ";
				errorList.add(error);
				log.info("missing apiList: " + apiListName);
			}

			if (apiListByNameMapEntry.getKey() == 1)
				baseFileNumber = apiListByNameMapEntry.getKey();
			else
				compareFileNumber = apiListByNameMapEntry.getKey();

			if (apiListByNameMapEntry.getKey() == 1)
				baseApiList = apiListByNameMapEntry.getValue();
			else
				compareApiList = apiListByNameMapEntry.getValue();

			if (compareFileNumber != null && compareApiList != null) {
				log.info("comparing apiList: " + apiListName);

				apiListComparator(apiListName, baseApiList, compareApiList,
						baseFileNumber, compareFileNumber, errorList);
			}
		}
	}

	private void apiListComparator(String apiListName,
			List<ApiModel> baseApiList, List<ApiModel> compareApiList,
			Long baseFileNumber, Long compareFileNumber, List<String> errorList)
			throws TestExecutionException {
		Boolean result = false;

		for (ApiModel baseApiModel : baseApiList) {
			for (ApiModel compareApiModel : compareApiList) {
				if (baseApiModel.getName().equals(compareApiModel.getName())) {
					result = true;
				}
			}

			if (Boolean.FALSE.equals(result)) {
				String error = "At file number: " + baseFileNumber
						+ ", apiList named: " + apiListName + "contain Api: "
						+ baseApiModel.getName()
						+ " not match any Api at file: " + compareFileNumber
						+ " \n ";
				errorList.add(error);
			}
			// reset value
			result = false;
		}
	}
}
