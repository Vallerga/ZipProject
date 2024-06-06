package com.zip_project.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zip_project.costant.Costant;
import com.zip_project.exception.MyValidationException;
import com.zip_project.model.ApiList;
import com.zip_project.model.ApiModel;
import com.zip_project.model.FileStatus;
import com.zip_project.model.ModuleDefaults;
import com.zip_project.service.crud.ApiListService;
import com.zip_project.service.crud.ApiModelService;
import com.zip_project.service.crud.FileStatusService;
import com.zip_project.service.crud.ModuleDefaultService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DataParseService {

	private static String[] validHosts = Costant.getModuleDefaultHost();
	private final FileStatusService fileStatusService;
	private final ModuleDefaultService mdService;
	private final ApiListService apiListService;
	private final ApiModelService apiModelService;

	public DataParseService(FileStatusService fileStatusService,
			ModuleDefaultService mdService, ApiListService apiListService,
			ApiModelService apiModelService) {
		this.fileStatusService = fileStatusService;
		this.mdService = mdService;
		this.apiListService = apiListService;
		this.apiModelService = apiModelService;
	}

	public List<JsonNode> parseJsonManager(Integer reportNumber) {
		// extract files associated with a specific report
		List<JsonNode> jsonNodesList = new ArrayList<>();
		List<FileStatus> statusList = fileStatusService
				.findByReportNumber(reportNumber);

		for (FileStatus es : statusList) {
			try {
				jsonNodesList.add(parseSingleFile(es));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return jsonNodesList;
	}

	public JsonNode parseSingleFile(FileStatus es) {
		String jsonPath = null;
		ModuleDefaults mdj = null;
		ObjectMapper mapper = new ObjectMapper();
		JsonNode rootNode = null;
		List<JsonNode> apiListNode;
		try {
			if (es != null && es.getFilePath() != null) {
				jsonPath = es.getFilePath();
			} else {
				throw new MyValidationException(
						"Error during json validation process");
			}

			// read the JSON file
			rootNode = mapper.readTree(new File(jsonPath));
			String validationResult = es.getJsonValidationStatus();

			// extract data from root node
			if (validationResult.equals("validated")) {
				JsonNode md = rootNode.path(Costant.ROOTNODE_MODULE_DEFAULTS);

				String mdTestResult = moduleDefaultsTest(md, jsonPath,
						es.getRootName());

				mdj = loadModuleDefault(jsonPath, mdj, md, mdTestResult);
			}

			// iterate throw apis
			JsonNode apis = rootNode.path(Costant.ROOTNODE_APIS);

			apiListNode = apiListTest(apis);
			log.info(apiListNode.toString());
			Iterator<Map.Entry<String, JsonNode>> fields = apis.fields();

			loadApiList(mdj, fields);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rootNode;
	}

	private void loadApiList(ModuleDefaults mdj,
			Iterator<Map.Entry<String, JsonNode>> fields) {
		while (fields.hasNext()) {
			Map.Entry<String, JsonNode> field = fields.next();
			String apiCategory = field.getKey();
			JsonNode apiListNode = field.getValue();

			ApiList apiList = null;
			apiList = ApiList.builder().name(apiCategory).moduleDefault(mdj)
					.build();
			log.debug("mdj id: {}, APIList:{}",
					apiList.getModuleDefault().getIdModuleDefaults(),
					apiList.getName());

			apiListService.insertApiList(apiList);

			loadApiModels(apiListNode, apiList);
		}
	}

	private ModuleDefaults loadModuleDefault(String jsonPath,
			ModuleDefaults mdj, JsonNode md, String mdTestResult) {
		if (mdTestResult.equals("moduleDefaults validate")) {
			mdj = ModuleDefaults.builder().baseUrl(md.path("baseurl").asText())
					.path(jsonPath).host(md.path("host").asText())
					.security(md.path("security").asText())
					.protocol(md.path("protocol").asText()).build();
			log.debug("MODEL DEFAULT: {}", mdj);

			mdService.insertModuleDefault(mdj);
		}
		return mdj;
	}

	private void loadApiModels(JsonNode apiListNode, ApiList apiList) {
		if (apiListNode.isArray()) {
			for (JsonNode api : apiListNode) {
				ApiModel am = null;

				am = ApiModel.builder().name(api.path("name").asText())
						.baseUrl(api.path("baseUrl").asText())
						.endpoint(api.path("endpoint").asText())
						.method(api.path("method").asText()).apiList(apiList)
						.build();

				if (!api.findPath("isMocked").isMissingNode())
					am.setIsMocked(api.path("isMocked").asBoolean());

				apiModelService.insertApiModel(am);
				log.debug("ApiList id: {}, APIModel:{} - {}",
						am.getApiList().getIdApiList(), am.getName(),
						am.getBaseUrl());
			}
		}
	}

	public String moduleDefaultsTest(JsonNode mdjNode, String jsonPath,
			String rootName) {
		String protocol = "https";
		String rootNodePath = extractVariablePath(jsonPath, rootName);
		String validHost = validHostSelector(rootNodePath);
		Integer fieldCounter = 0;
		Iterator<Map.Entry<String, JsonNode>> mdIterator = mdjNode.fields();
		while (mdIterator.hasNext()) {
			mdIterator.next();
			fieldCounter++;
		}

		if (Objects.equals(fieldCounter, Costant.MODULE_DEFAULTS_ATTRIBUTE)) {
			mdjNode.findPath(validHost);
			Boolean isHostMissing = mdjNode.findPath(validHost).isValueNode();
			if (validHost.equals(validHosts[1])) {
				protocol = "http";
			}

			Boolean isProtocolMissing = mdjNode.findPath(protocol)
					.isValueNode();

			Boolean isBaseUrlMissing = mdjNode
					.findPath(Costant.MODULE_DEFAULT_BASEURL).isValueNode();

			Boolean isSecurityMissing = mdjNode
					.findPath(Costant.MODULE_DEFAULT_SECURITY).isValueNode();

			log.debug(
					"isHostMissing, isProtocolMissing, isBaseUrlMissing, isSecurityMissing  {} - {} - {} - {}",
					isHostMissing, isProtocolMissing, isBaseUrlMissing,
					isSecurityMissing);

			if (Boolean.FALSE.equals(isHostMissing)
					&& Boolean.FALSE.equals(isProtocolMissing)
					&& Boolean.FALSE.equals(isBaseUrlMissing)
					&& Boolean.FALSE.equals(isSecurityMissing)) {
				return "moduleDefaults validate";
			}
		}

		return "moduleDefaults not validate";
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
		String url;
		switch (selectedPath) {
			case "\\xdce-module-tbgtee\\apicatalog\\tbgtee\\api.json" :
				url = validHosts[0];
				break;
			case "\\xdce-module-tbgtee\\env\\local\\apicatalog\\tbgtee\\api.json" :
				url = validHosts[1];
				break;
			case "\\xdce-module-tbgtee\\env\\prod\\apicatalog\\tbgtee\\api.json" :
				url = validHosts[2];
				break;
			case "\\xdce-module-tbgtee\\env\\svil\\apicatalog\\tbgtee\\api.json" :
				url = validHosts[3];
				break;
			case "\\xdce-module-tbgtee\\env\\test\\apicatalog\\tbgtee\\api.json" :
				url = validHosts[4];
				break;
			case "\\xdce-module-tbgtee\\env\\utes\\apicatalog\\tbgtee\\api.json" :
				url = validHosts[5];
				break;
			default :
				url = null;
				break;
		}
		return url;
	}

	public List<JsonNode> apiListTest(JsonNode apiListNode) {
		Entry<String, JsonNode> singleApi;
		String apiKey;
		JsonNode apiValue;
		List<JsonNode> nameList = new ArrayList<>();
		Iterator<Map.Entry<String, JsonNode>> mdIterator = apiListNode.fields();
		while (mdIterator.hasNext()) {
			singleApi = mdIterator.next();
			apiKey = singleApi.getKey();
			apiValue = singleApi.getValue();
			log.info("apiKey: {}", apiKey);
			if (apiKey.equals("name")) {
				nameList.add(apiValue);
			}
		}

		return nameList;
	}
}
