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
import com.zip_project.db.model.JsonLineList;
import com.zip_project.service.costant.Costant;
import com.zip_project.service.costant.Costant.TestStatus;
import com.zip_project.service.crud.FileStatusService;
import com.zip_project.service.crud.JsonLineListService;
import com.zip_project.service.exception.DataParsingException;
import com.zip_project.service.exception.DatabaseOperationException;
import com.zip_project.service.exception.TestExecutionException;
import com.zip_project.service.exception.error.ErrorContext;
import com.zip_project.service.exception.error.MismatchApi;
import com.zip_project.service.exception.error.MissingApiList;

@Service
public class DataTestService {

	private final FileStatusService fileStatusService;
	private final JsonLineListService jsonLineListService;

	public DataTestService(FileStatusService fileStatusService,
			JsonLineListService jsonLineListService) {

		this.jsonLineListService = jsonLineListService;
		this.fileStatusService = fileStatusService;
	}
	public ErrorContext dataTest(Integer reportNumber)
			throws TestExecutionException, DataAccessException,
			DataParsingException {
		ErrorContext errorContext = new ErrorContext();
		Map<String, Map<Long, List<ApiModel>>> compareNameApiListMap = new HashMap<>();

		try {
			List<FileStatus> statusList = fileStatusService
					.findByReportNumber(reportNumber);
			Map<Long, Map<String, List<ApiModel>>> allFilesMap = fileStatusService
					.getApiModelsByReportNumberAndApiListNames(reportNumber);

			loadCompareNameApiListMap(compareNameApiListMap, allFilesMap);

			sortApiListByName(compareNameApiListMap, errorContext, statusList);

			updateFileStatus(statusList, TestStatus.TESTED);
		} catch (DataAccessException e) {
			throw new DatabaseOperationException(
					"An error occurred while accessing the database: file status non accessible.");
		}
		return errorContext;
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
			ErrorContext errorContext, List<FileStatus> statusList)
			throws TestExecutionException {
		for (Map.Entry<String, Map<Long, List<ApiModel>>> compareNameApiListMapEntry : compareNameApiListMap
				.entrySet()) {
			String apiListName = compareNameApiListMapEntry.getKey();
			Map<Long, List<ApiModel>> apiListByNameMap = compareNameApiListMapEntry
					.getValue();
			analizeApiListByName(apiListByNameMap, apiListName, errorContext,
					statusList);
		}
	}

	private void analizeApiListByName(
			Map<Long, List<ApiModel>> apiListByNameMap, String apiListName,
			ErrorContext errorContext, List<FileStatus> statusList)
			throws TestExecutionException {
		List<MissingApiList> missingApiListContainer = new ArrayList<>();
		List<ApiModel> compareApiList = null;
		List<ApiModel> baseApiList = null;
		Long compareFileNumber = null;
		Long baseFileNumber = null;

		for (Entry<Long, List<ApiModel>> apiListByNameMapEntry : apiListByNameMap
				.entrySet()) {
			compareFileNumber = apiListByNameMapEntry.getKey();
			FileStatus fileStatus = statusList
					.get(compareFileNumber.intValue());

			if (apiListByNameMapEntry.getValue() == null) {

				MissingApiList missingApiList = new MissingApiList();
				missingApiList.setMissingApiListName(apiListName);
				missingApiList.setFileNumber(compareFileNumber);
				missingApiList.setMessage("Not match ApiList name");
				missingApiList.setFilePath(fileStatus.getFilePath());

				missingApiListContainer.add(missingApiList);

				errorContext.setMissingApiListErrors(missingApiListContainer);
			}

			// load data for comparison
			if (compareFileNumber == 1) {
				compareApiList = apiListByNameMapEntry.getValue();
			} else if (compareFileNumber != apiListByNameMap.size()) {
				baseFileNumber = compareFileNumber - 1;
				baseApiList = compareApiList;
				compareApiList = apiListByNameMapEntry.getValue();
			} else if (compareFileNumber == apiListByNameMap.size()) {
				baseFileNumber = compareFileNumber - 1;
				compareApiList = apiListByNameMapEntry.getValue();
			}

			if (baseFileNumber != null && baseApiList != null) {
				apiListComparator(apiListName, compareApiList, baseApiList,
						compareFileNumber, baseFileNumber, errorContext,
						statusList);
			}
		}
	}

	private void apiListComparator(String apiListName,
			List<ApiModel> compareApiList, List<ApiModel> baseApiList,
			Long compareFileNumber, Long baseFileNumber,
			ErrorContext errorContext, List<FileStatus> statusList)
			throws TestExecutionException {

		FileStatus baseFileStatus = statusList
				.get(compareFileNumber.intValue());
		FileStatus compareFileStatus = statusList
				.get(baseFileNumber.intValue());

		for (ApiModel baseApiModel : compareApiList) {
			Boolean result = false;

			for (Integer i = 0; i < baseApiList.size(); i++) {

				ApiModel compareApiModel = baseApiList.get(i);

				if (compareApiModel.getName().equals(baseApiModel.getName())) {
					result = true;
				}

				// if there isn't a match create error report
				if (Boolean.FALSE.equals(result)
						&& baseApiList.size() == i + 1) {

					loadMismatchApiError(apiListName, compareFileNumber,
							baseFileNumber, errorContext, baseFileStatus,
							compareFileStatus, compareApiModel);
				}
			}
		}
	}
	private void loadMismatchApiError(String apiListName, Long baseFileNumber,
			Long compareFileNumber, ErrorContext errorContext,
			FileStatus baseFileStatus, FileStatus compareFileStatus,
			ApiModel compareApiModel) {

		List<MismatchApi> mismatchApiContainer;

		Integer mismatchLine = findMismatchLine(compareApiModel.getName(),
				compareFileNumber, baseFileStatus);

		MismatchApi mismatchApiModel = new MismatchApi();
		mismatchApiModel.setApiName(compareApiModel.getName());
		mismatchApiModel.setLineCode(mismatchLine);
		mismatchApiModel.setBaseFileNumber(baseFileNumber);
		mismatchApiModel.setCompareFileNumber(compareFileNumber);
		mismatchApiModel.setApiListName(apiListName);
		mismatchApiModel.setMessage("Not match any Api in same apiList");
		mismatchApiModel.setBaseFilePath(baseFileStatus.getFilePath());
		mismatchApiModel.setCompareFilePath(compareFileStatus.getFilePath());

		if (errorContext.getMismatchApiErrors() != null) {
			mismatchApiContainer = errorContext.getMismatchApiErrors();
		} else {
			mismatchApiContainer = new ArrayList<>();
		}

		mismatchApiContainer.add(mismatchApiModel);

		errorContext.setMismatchApiErrors(mismatchApiContainer);
	}
	private Integer findMismatchLine(String apiName, Long compareFileNumber,
			FileStatus baseFileStatus) {
		JsonLineList compareJsonLineList = jsonLineListService
				.getJsonLineListByModuleDefaultsId(compareFileNumber);

		jsonLineListService.findJsonLineListById(compareFileNumber);

		baseFileStatus.getJsonLineList();

		List<String> compareLineCodeList = compareJsonLineList
				.getLineCodeList();

		for (Integer i = 0; i < compareLineCodeList.size(); i++) {
			String line = compareLineCodeList.get(i);
			if (line.contains(apiName))
				return i + 1;
		}
		return null;
	}

}
