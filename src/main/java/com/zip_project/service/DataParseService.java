package com.zip_project.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zip_project.db.model.ApiList;
import com.zip_project.db.model.ApiModel;
import com.zip_project.db.model.FileStatus;
import com.zip_project.db.model.ModuleDefaults;
import com.zip_project.service.costant.Costant;
import com.zip_project.service.costant.Costant.CompleteFilePath;
import com.zip_project.service.costant.Costant.JsonValidation;
import com.zip_project.service.crud.ApiListService;
import com.zip_project.service.crud.ApiModelService;
import com.zip_project.service.crud.FileStatusService;
import com.zip_project.service.crud.ModuleDefaultService;
import com.zip_project.service.exception.MyValidationException;

@Service
public class DataParseService {

	private static String[] validHosts = Costant.getModuleDefaultHost();
	private final FileStatusService fileStatusService;
	private final ModuleDefaultService moduleDefaultService;
	private final ApiListService apiListService;
	private final ApiModelService apiModelService;

	public DataParseService(FileStatusService fileStatusService,
			ModuleDefaultService moduleDefaultService,
			ApiListService apiListService, ApiModelService apiModelService) {
		this.fileStatusService = fileStatusService;
		this.moduleDefaultService = moduleDefaultService;
		this.apiListService = apiListService;
		this.apiModelService = apiModelService;
	}

	public List<ModuleDefaults> parseJsonManager(Integer reportNumber) {
		// extract files associated with a specific report
		List<ModuleDefaults> moduleDefaultsList = new ArrayList<>();
		List<FileStatus> statusList = fileStatusService
				.findByReportNumber(reportNumber);

		for (FileStatus fileStatus : statusList) {
			// the non-environment file is always positioned at the start of the
			// list
			try {
				if (Boolean.FALSE.equals(fileStatus.getFilePath()
						.contains(Costant.FilePath.NOT_ENV.getValue())))
					moduleDefaultsList.add(0, parseSingleFile(fileStatus));
				else
					moduleDefaultsList.add(parseSingleFile(fileStatus));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return moduleDefaultsList;
	}

	public ModuleDefaults parseSingleFile(FileStatus fileStatus) {
		String jsonPath = null;
		ModuleDefaults moduleDefaults = null;
		ObjectMapper mapper = new ObjectMapper();
		JsonNode rootNode = null;
		try {
			if (fileStatus != null && fileStatus.getFilePath() != null) {
				jsonPath = fileStatus.getFilePath();
			} else {
				throw new MyValidationException(
						"Error during json validation process");
			}

			// read the JSON file
			rootNode = mapper.readTree(new File(jsonPath));
			JsonValidation validationStatus = fileStatus
					.getJsonValidationStatus();

			// extract data from root node
			if (validationStatus.equals(Costant.JsonValidation.VALIDATED)) {

				JsonNode moduleDefaultsNode = rootNode
						.path(Costant.RootNodePath.MODULE_DEFAULTS.getValue());

				JsonValidation mdTestResult = moduleDefaultsTest(
						moduleDefaultsNode, jsonPath, fileStatus.getRootName());

				if (Objects.equals(mdTestResult,
						Costant.JsonValidation.VALIDATED)) {
					moduleDefaults = loadModuleDefault(jsonPath, moduleDefaults,
							moduleDefaultsNode, fileStatus);

					// iterate throw apiListNode
					JsonNode allApiListNode = rootNode
							.path(Costant.RootNodePath.APIS.getValue());

					JsonValidation apiTestResult = apiListTest(allApiListNode);

					if (apiTestResult.equals(Costant.JsonValidation.VALIDATED))
						loadApiList(moduleDefaults, allApiListNode);
				} else {
					throw new Exception(
							"Module default data not valid and didn't pass the test");
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return moduleDefaults;
	}

	private ModuleDefaults loadModuleDefault(String jsonPath,
			ModuleDefaults moduleDefaults, JsonNode moduleDefaultsNode,
			FileStatus fileStatus) throws Exception {

		if (moduleDefaults == null && moduleDefaultsNode == null) {
			throw new Exception(
					"Invalid parameter, moduleDefaults can't be null");
		}

		List<ApiList> apiList = new ArrayList<>();

		moduleDefaults = ModuleDefaults.builder()
				.baseUrl(moduleDefaultsNode.path(
						Costant.ModuleDefaultsAttributeName.BASEURL.getValue())
						.asText())
				.path(jsonPath)
				.host(moduleDefaultsNode.path(
						Costant.ModuleDefaultsAttributeName.HOST.getValue())
						.asText())
				.security(moduleDefaultsNode.path(
						Costant.ModuleDefaultsAttributeName.SECURITY.getValue())
						.asText())
				.protocol(moduleDefaultsNode.path(
						Costant.ModuleDefaultsAttributeName.PROTOCOL.getValue())
						.asText())
				.apiList(apiList).fileStatus(fileStatus).build();

		moduleDefaultService.insertModuleDefault(moduleDefaults);

		return moduleDefaults;
	}

	private void loadApiList(ModuleDefaults moduleDefaults,
			JsonNode allApiListNode) throws Exception {
		ApiList apiList = null;
		List<ApiModel> apiModelList = new ArrayList<>();
		List<ApiList> apiListContainer;

		if (moduleDefaults == null && allApiListNode == null) {
			throw new Exception(
					"Invalid parameter, moduleDefaults can't be null");
		}

		if (moduleDefaults == null || moduleDefaults.getApiList() != null)
			apiListContainer = new ArrayList<>();
		else
			apiListContainer = moduleDefaults.getApiList();

		Iterator<Map.Entry<String, JsonNode>> apiListIterator = allApiListNode
				.fields();

		while (apiListIterator.hasNext()) {
			Map.Entry<String, JsonNode> apiListMap = apiListIterator.next();
			String apiListKey = apiListMap.getKey();
			JsonNode singleApiListNode = apiListMap.getValue();

			apiList = ApiList.builder().name(apiListKey)
					.moduleDefaults(moduleDefaults).apiModels(apiModelList)
					.build();

			apiListService.insertApiList(apiList);

			loadApiModels(apiList, singleApiListNode);

			apiListContainer.add(apiList);

			if (moduleDefaults != null)
				moduleDefaults.setApiList(apiListContainer);
		}
	}

	private void loadApiModels(ApiList apiList, JsonNode singleApiListNode)
			throws Exception {
		ApiModel apiModel = null;
		List<ApiModel> apiModelList = new ArrayList<>();

		if (apiList == null || singleApiListNode == null) {
			throw new Exception(
					"Invalid parameter, apiList and singleApiListNode can't be null");
		} else if (singleApiListNode.isArray()) {
			for (JsonNode apiModelNode : singleApiListNode) {

				apiModel = ApiModel.builder()
						.name(apiModelNode
								.path(Costant.ApiModelAttribute.NAME.getValue())
								.asText())
						.baseUrl(apiModelNode.path(
								Costant.ApiModelAttribute.BASEURL.getValue())
								.asText())
						.endpoint(apiModelNode.path(
								Costant.ApiModelAttribute.ENDPOINT.getValue())
								.asText())
						.method(apiModelNode.path(
								Costant.ApiModelAttribute.METHOD.getValue())
								.asText())
						.apiList(apiList).build();

				if (!apiModelNode
						.findPath(Costant.ApiModelAttribute.ISMOCKED.getValue())
						.isMissingNode())
					apiModel.setIsMocked(apiModelNode
							.path(Costant.ApiModelAttribute.ISMOCKED.getValue())
							.asBoolean());

				apiModelService.insertApiModel(apiModel);
			}
			apiModelList.add(apiModel);
			apiList.setApiModels(apiModelList);
		}
	}

	public JsonValidation moduleDefaultsTest(JsonNode mdjNode, String jsonPath,
			String rootName) {
		String[] moduleDefaultProtocol = Costant.getModuleDefaultProtocol();
		String protocol = moduleDefaultProtocol[0];
		String rootNodePath = extractVariablePath(jsonPath, rootName);
		String validHost = validHostSelector(rootNodePath);
		Integer fieldCounter = 0;
		Iterator<Map.Entry<String, JsonNode>> mdIterator = mdjNode.fields();
		while (mdIterator.hasNext()) {
			mdIterator.next();
			fieldCounter++;
		}

		if (validHost != null && Objects.equals(fieldCounter,
				Costant.MODULE_DEFAULTS_ATTRIBUTE)) {
			mdjNode.findPath(validHost);
			Boolean isHostMissing = mdjNode.findPath(validHost).isValueNode();
			if (validHost.equals(validHosts[1])) {
				protocol = moduleDefaultProtocol[1];
			}

			Boolean isProtocolMissing = mdjNode.findPath(protocol)
					.isValueNode();

			Boolean isBaseUrlMissing = mdjNode
					.findPath(Costant.MODULE_DEFAULT_BASEURL).isValueNode();

			Boolean isSecurityMissing = mdjNode
					.findPath(Costant.MODULE_DEFAULT_SECURITY).isValueNode();
			if (Boolean.FALSE.equals(isHostMissing)
					&& Boolean.FALSE.equals(isProtocolMissing)
					&& Boolean.FALSE.equals(isBaseUrlMissing)
					&& Boolean.FALSE.equals(isSecurityMissing)) {
				return Costant.JsonValidation.VALIDATED;
			}
		}
		return Costant.JsonValidation.NOT_VALIDATED;
	}

	public static String extractVariablePath(String filePath, String rootName) {
		int startIndex = filePath.indexOf(rootName);
		if (startIndex != -1) {
			startIndex += rootName.length();
			return filePath.substring(startIndex);
		}
		return null;
	}

	public static String validHostSelector(String selectedPath) {
		for (CompleteFilePath path : CompleteFilePath.values()) {
			if (path.getValue().equals(selectedPath)) {
				return path.getValue();
			}
		}
		return null;
	}

	public JsonValidation apiListTest(JsonNode apiListNode) {
		String apiKey;
		String[] apiListName = Costant.getApiListNames();
		boolean isPresent;
		Entry<String, JsonNode> singleApi;

		Iterator<Map.Entry<String, JsonNode>> apiListIterator = apiListNode
				.fields();

		while (apiListIterator.hasNext()) {
			singleApi = apiListIterator.next();
			apiKey = singleApi.getKey();
			isPresent = Arrays.asList(apiListName).contains(apiKey);

			if (Boolean.FALSE.equals(isPresent)) {
				return Costant.JsonValidation.NOT_VALIDATED;
			}
		}
		return Costant.JsonValidation.VALIDATED;
	}
}
