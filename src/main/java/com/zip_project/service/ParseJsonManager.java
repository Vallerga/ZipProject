package com.zip_project.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zip_project.costant.Costant;
import com.zip_project.model.ApiList;
import com.zip_project.model.ApiModel;
import com.zip_project.model.ModuleDefaults;
import com.zip_project.service.crud.ApiListService;
import com.zip_project.service.crud.ApiModelService;
import com.zip_project.service.crud.ModuleDefaultService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ParseJsonManager {

	private static String[] validHosts = Costant.getModuleDefaultHost();

	@Autowired
	private ModuleDefaultService mdService;
	@Autowired
	private ApiListService apiListService;
	@Autowired
	private ApiModelService apiModelService;

	public JsonNode parseJSON(Path newFilePath, String fileName)
			throws IOException {
		ModuleDefaults mdj = null;
		ObjectMapper mapper = new ObjectMapper();
		newFilePath.toString();

		// Read the JSON file
		JsonNode rootNode = mapper.readTree(new File(newFilePath.toString()));

		String rootNodeResult = rootNodeTest(rootNode);

		// Extract data from root node
		if (rootNodeResult.equals("rootNode validate")) {
			JsonNode md = rootNode.path(Costant.ROOTNODE_MODULE_DEFAULTS);

			String mdTestResult = moduleDefaultsTest(md, newFilePath, fileName);

			if (mdTestResult.equals("moduleDefaults validate")) {
				mdj = ModuleDefaults.builder()
						.baseUrl(md.path("baseurl").asText())
						.path(newFilePath.toString())
						.host(md.path("host").asText())
						.security(md.path("security").asText())
						.protocol(md.path("protocol").asText()).build();
				log.debug("MODEL DEFAULT: {}", mdj);

				mdService.insertModuleDefault(mdj);
			}
		}

		// Iterate throw apis
		JsonNode apis = rootNode.path(Costant.ROOTNODE_APIS);
		
		apiListTest(apis);

		Iterator<Map.Entry<String, JsonNode>> fields = apis.fields();

		while (fields.hasNext()) {
			Map.Entry<String, JsonNode> field = fields.next();
			String apiCategory = field.getKey();
			JsonNode apiList = field.getValue();

			ApiList al = null;
			al = ApiList.builder().name(apiCategory).moduleDefault(mdj).build();
			log.debug("mdj id: {}, APIList:{}",
					al.getModuleDefault().getIdModuleDefaults(), al.getName());

			apiListService.insertApiList(al);

			if (apiList.isArray()) {
				for (JsonNode api : apiList) {
					ApiModel am = null;

					am = ApiModel.builder().name(api.path("name").asText())
							.baseUrl(api.path("baseUrl").asText())
							.endpoint(api.path("endpoint").asText())
							.method(api.path("method").asText()).apiList(al)
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
		return rootNode;
	}

	public String rootNodeTest(JsonNode rootNode) {
		Integer fieldCounter = 0;

		Iterator<Map.Entry<String, JsonNode>> rootNodeIterator = rootNode
				.fields();
		while (rootNodeIterator.hasNext()) {
			rootNodeIterator.next();
			fieldCounter++;
		}

		if (Objects.equals(fieldCounter, Costant.ROOTNODE_ATTRIBUTE)) {
			Boolean moduleDefaultsMissing = rootNode
					.findPath(Costant.ROOTNODE_MODULE_DEFAULTS).isMissingNode();
			Boolean isApisMissing = rootNode.findPath(Costant.ROOTNODE_APIS)
					.isMissingNode();

			log.debug("moduleDefaultsMissing, isApisMissing: {} - {}",
					moduleDefaultsMissing, isApisMissing);
			if (Boolean.FALSE.equals(moduleDefaultsMissing)
					&& Boolean.FALSE.equals(isApisMissing)) {
				return "rootNode validate";
			}
		}
		return "rootNode not validate";
	}

	public String moduleDefaultsTest(JsonNode mdjNode, Path filePath,
			String fileName) {
		String protocol = "https";
		String rootNodePath = extractVariablePath(filePath.toString(),
				fileName);
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

	public static String extractVariablePath(String filePath, String fileName) {
		int startIndex = filePath.indexOf(fileName);
		if (startIndex != -1) {
			startIndex += fileName.length();
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

		public String apiListTest(JsonNode apiListNode){
		return null;
	}
}
