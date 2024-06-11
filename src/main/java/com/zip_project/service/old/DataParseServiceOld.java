package com.zip_project.service.old;

import org.springframework.stereotype.Service;


@Service
public class DataParseServiceOld {

//	private static String[] validHosts = Costant.getModuleDefaultHost();
//	private final FileStatusService fileStatusService;
//	private final ModuleDefaultService moduleDefaultService;
//	private final ApisListService apisListService;
//	private final ApiListService apiListService;
//	private final ApiModelService apiModelService;
//
//	public DataParseServiceOld(FileStatusService fileStatusService,
//			ModuleDefaultService moduleDefaultService,
//			ApisListService apisListService, ApiListService apiListService,
//			ApiModelService apiModelService) {
//		this.fileStatusService = fileStatusService;
//		this.moduleDefaultService = moduleDefaultService;
//		this.apisListService = apisListService;
//		this.apiListService = apiListService;
//		this.apiModelService = apiModelService;
//	}
//
//	public List<ModuleDefaults> parseJsonManager(Integer reportNumber) {
//		// extract files associated with a specific report
//		List<ModuleDefaults> moduleDefaultsList = new ArrayList<>();
//		List<FileStatus> statusList = fileStatusService
//				.findByReportNumber(reportNumber);
//
//		for (FileStatus es : statusList) {
//			// the non-environment file is always positioned at the start of the
//			// list
//			try {
//				if (Boolean.FALSE.equals(
//						es.getFilePath().contains(Costant.PATH_NOT_ENV)))
//					moduleDefaultsList.add(0, parseSingleFile(es));
//				else
//					moduleDefaultsList.add(parseSingleFile(es));
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//		return moduleDefaultsList;
//	}
//
//	public ModuleDefaults parseSingleFile(FileStatus es) {
//		String jsonPath = null;
//		String apisTestResult = "";
//		ModuleDefaults moduleDefaults = null;
//		ObjectMapper mapper = new ObjectMapper();
//		JsonNode rootNode = null;
//		try {
//			if (es != null && es.getFilePath() != null) {
//				jsonPath = es.getFilePath();
//			} else {
//				throw new MyValidationException(
//						"Error during json validation process");
//			}
//
//			// read the JSON file
//			rootNode = mapper.readTree(new File(jsonPath));
//			String validationResult = es.getJsonValidationStatus();
//
//			// extract data from root node
//			if (validationResult.equals(Costant.JSON_VALIDATED)) {
//				JsonNode moduleDefaultsNode = rootNode
//						.path(Costant.ROOTNODE_MODULE_DEFAULTS);
//
//				String mdTestResult = moduleDefaultsTest(moduleDefaultsNode,
//						jsonPath, es.getRootName());
//
//				moduleDefaults = loadModuleDefault(jsonPath, moduleDefaults,
//						moduleDefaultsNode, mdTestResult);
//			}
//
//			// iterate throw apis
//			JsonNode apis = rootNode.path(Costant.ROOTNODE_APIS);
//
//			apisTestResult = apisListTest(apis);
//
//			log.info("comparazione: {}",
//					apisTestResult.equals(Costant.JSON_VALIDATED));
//			if (apisTestResult.equals(Costant.JSON_VALIDATED))
//				loadApisList(moduleDefaults, apis);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return moduleDefaults;
//	}
//
//	private void loadApisList(ModuleDefaults moduleDefaults,
//			JsonNode apisNode) {
//		Iterator<Map.Entry<String, JsonNode>> fields = apisNode.fields();
//		ApisList apis = null;
//		List<ApisList> apisList = null;
//		List<ApiList> apiList = new ArrayList<>();
//		if (moduleDefaults != null && moduleDefaults.getApisList() != null)
//			apisList = new ArrayList<>();
//		else
//			apisList = moduleDefaults.getApisList();
//		while (fields.hasNext()) {
//			Map.Entry<String, JsonNode> field = fields.next();
//			String apisKey = field.getKey();
//			JsonNode apisListNode = field.getValue();
//
//			apis = ApisList.builder().name(apisKey)
//					.moduleDefault(moduleDefaults).apiList(apiList).build();
//
//			apisListService.insertApisList(apis);
//
//			loadApiList(apisListNode, apis);
//			apisList.add(apis);
//			moduleDefaults.setApisList(apisList);
//		}
//	}
//
//	private ModuleDefaults loadModuleDefault(String jsonPath,
//			ModuleDefaults moduleDefaults, JsonNode moduleDefaultsNode,
//			String mdTestResult) {
//		List<ApisList> apisList = new ArrayList<>();
//		if (mdTestResult.equals(Costant.JSON_VALIDATED)) {
//			moduleDefaults = ModuleDefaults.builder()
//					.baseUrl(moduleDefaultsNode
//							.path(Costant.MODULE_DEFAULT_NAME_BASEURL).asText())
//					.path(jsonPath)
//					.host(moduleDefaultsNode
//							.path(Costant.MODULE_DEFAULT_NAME_HOST).asText())
//					.security(moduleDefaultsNode
//							.path(Costant.MODULE_DEFAULT_NAME_SECURITY)
//							.asText())
//					.protocol(moduleDefaultsNode
//							.path(Costant.MODULE_DEFAULT_NAME_PROTOCOL)
//							.asText())
//					.apisList(apisList).build();
//
//			moduleDefaultService.insertModuleDefault(moduleDefaults);
//		}
//		return moduleDefaults;
//	}
//
//	private void loadApiList(JsonNode apisListNode, ApisList apisList) {
//		ApiList apiList = null;
//		List<ApiList> apiListContainer = apisList.getApiList();
//		List<ApiModel> apiModels = new ArrayList<>();
//
//		if (apisListNode.isArray()) {
//			for (JsonNode apiListNode : apisListNode) {
//
//				apiList = ApiList.builder()
//						.name(apiListNode.path(Costant.API_LIST_NAME).asText())
//						.apisList(apisList).apiModels(apiModels).build();
//
//				apiListService.insertApiList(apiList);
//
//				apiListContainer.add(apiList);
//				apisList.setApiList(apiListContainer);
//
//				loadApiModels(apiListNode, apiList);
//			}
//		}
//
//	}
//
//	private void loadApiModels(JsonNode apiListNode, ApiList apiList) {
//		ApiModel apiModel = null;
//		List<ApiModel> apiModelList = new ArrayList<>();
//
//		if (apiListNode.isArray()) {
//			for (JsonNode apiModelNode : apiListNode) {
//
//				apiModel = ApiModel.builder()
//						.name(apiModelNode.path(Costant.API_MODEL_NAME)
//								.asText())
//						.baseUrl(apiModelNode.path(Costant.API_MODEL_BASEURL)
//								.asText())
//						.endpoint(apiModelNode.path(Costant.API_MODEL_ENDPOINT)
//								.asText())
//						.method(apiModelNode.path(Costant.API_MODEL_METHOD)
//								.asText())
//						.apiList(apiList).build();
//
//				if (!apiModelNode.findPath(Costant.API_MODEL_ISMOCKED)
//						.isMissingNode())
//					apiModel.setIsMocked(apiModelNode
//							.path(Costant.API_MODEL_ISMOCKED).asBoolean());
//
//				apiModelService.insertApiModel(apiModel);
//
//				apiModelList.add(apiModel);
//				apiList.setApiModels(apiModelList);
//			}
//		}
//	}
//
//	public String moduleDefaultsTest(JsonNode mdjNode, String jsonPath,
//			String rootName) {
//		String[] moduleDefaultProtocol = Costant.getModuleDefaultProtocol();
//		String protocol = moduleDefaultProtocol[0];
//		String rootNodePath = extractVariablePath(jsonPath, rootName);
//		String validHost = validHostSelector(rootNodePath);
//		Integer fieldCounter = 0;
//		Iterator<Map.Entry<String, JsonNode>> mdIterator = mdjNode.fields();
//		while (mdIterator.hasNext()) {
//			mdIterator.next();
//			fieldCounter++;
//		}
//
//		if (Objects.equals(fieldCounter, Costant.MODULE_DEFAULTS_ATTRIBUTE)) {
//			mdjNode.findPath(validHost);
//			Boolean isHostMissing = mdjNode.findPath(validHost).isValueNode();
//			if (validHost.equals(validHosts[1])) {
//				protocol = moduleDefaultProtocol[1];
//			}
//
//			Boolean isProtocolMissing = mdjNode.findPath(protocol)
//					.isValueNode();
//
//			Boolean isBaseUrlMissing = mdjNode
//					.findPath(Costant.MODULE_DEFAULT_BASEURL).isValueNode();
//
//			Boolean isSecurityMissing = mdjNode
//					.findPath(Costant.MODULE_DEFAULT_SECURITY).isValueNode();
//
//			if (Boolean.FALSE.equals(isHostMissing)
//					&& Boolean.FALSE.equals(isProtocolMissing)
//					&& Boolean.FALSE.equals(isBaseUrlMissing)
//					&& Boolean.FALSE.equals(isSecurityMissing)) {
//				return Costant.JSON_VALIDATED;
//			}
//		}
//
//		return Costant.JSON_NOT_VALIDATED;
//	}
//
//	public static String extractVariablePath(String filePath, String rootName) {
//		int startIndex = filePath.indexOf(rootName);
//		if (startIndex != -1) {
//			startIndex += rootName.length();
//			return filePath.substring(startIndex);
//		}
//		return null;
//	}
//
//	public static String validHostSelector(String selectedPath) {
//		String url;
//		switch (selectedPath) {
//			case Costant.COMPLETE_PATH_NOT_ENV :
//				url = validHosts[0];
//				break;
//			case Costant.COMPLETE_PATH_LOCAL :
//				url = validHosts[1];
//				break;
//			case Costant.COMPLETE_PATH_PROD :
//				url = validHosts[2];
//				break;
//			case Costant.COMPLETE_PATH_SVIL :
//				url = validHosts[3];
//				break;
//			case Costant.COMPLETE_PATH_TEST :
//				url = validHosts[4];
//				break;
//			case Costant.COMPLETE_PATH_UTES :
//				url = validHosts[5];
//				break;
//			default :
//				url = null;
//				break;
//		}
//		return url;
//	}
//
//	public String apisListTest(JsonNode apisListNode) {
//		String apiKey;
//		String[] apisListName = Costant.getApisListName();
//		boolean isPresent;
//		Entry<String, JsonNode> singleApi;
//
//		Iterator<Map.Entry<String, JsonNode>> mdIterator = apisListNode
//				.fields();
//
//		while (mdIterator.hasNext()) {
//			singleApi = mdIterator.next();
//			apiKey = singleApi.getKey();
//
//			isPresent = Arrays.asList(apisListName).contains(apiKey);
//
//			if (Boolean.FALSE.equals(isPresent)) {
//				log.info("apiKey: {}", apiKey);
//				return Costant.JSON_NOT_VALIDATED;
//			}
//		}
//		return Costant.JSON_VALIDATED;
//	}
//
//	public String apiListTest(JsonNode apiListNode) {
//		JsonNode apiValue;
//		String[] apisListName = Costant.getApisListName();
//		boolean isPresent;
//		Entry<String, JsonNode> singleApi;
//
//		Iterator<Map.Entry<String, JsonNode>> mdIterator = apiListNode.fields();
//
//		while (mdIterator.hasNext()) {
//			singleApi = mdIterator.next();
//			apiValue = singleApi.getValue();
//
//			isPresent = Arrays.asList(apisListName).contains(apiValue.asText());
//
//			if (Boolean.FALSE.equals(isPresent)) {
//				return Costant.JSON_NOT_VALIDATED;
//			}
//		}
//		return Costant.JSON_VALIDATED;
//	}
}
