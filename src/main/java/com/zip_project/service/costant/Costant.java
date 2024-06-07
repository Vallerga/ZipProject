package com.zip_project.service.costant;

public class Costant {

	private Costant() {
	}

	// json validation
	public static final String JSON_VALIDATED = "validated";
	public static final String JSON_NOT_VALIDATED = "not validated";

	// attribute number accepted
	public static final Integer ROOTNODE_ATTRIBUTE = 2;
	public static final Integer MODULE_DEFAULTS_ATTRIBUTE = 4;

	// rootNode path accepted
	public static final String ROOTNODE_APIS = "apis";
	public static final String ROOTNODE_MODULE_DEFAULTS = "moduleDefaults";

	// module defaults attrubute name
	public static final String MODULE_DEFAULT_NAME_PROTOCOL = "protocol";
	public static final String MODULE_DEFAULT_NAME_BASEURL = "baseUrl";
	public static final String MODULE_DEFAULT_NAME_HOST = "host";
	public static final String MODULE_DEFAULT_NAME_SECURITY = "security";

	// module defaults accepted value
	private static String[] moduleDefaultProtocol = {"https", "http"};
	private static String[] moduleDefaultHost = {
			"trade-banca-svil.syssede.systest.sanpaoloimi.com",
			"localhost:4200", "trade-banca.sede.corp.sanpaoloimi.com",
			"trade-banca-svil.syssede.systest.sanpaoloimi.com",
			"trade-banca-test.syssede.systest.sanpaoloimi.com",
			"trade-banca-utes.syssede.systest.sanpaoloimi.com"};
	public static final String MODULE_DEFAULT_BASEURL = "/";
	public static final String MODULE_DEFAULT_SECURITY = "none";

	// apisList attribute name
	public static final String APIS_LIST_NAME = "name";

	// apiList attribute name
	public static final String API_LIST_NAME = "name";

	// api models attribute name costant
	public static final String API_MODEL_ISMOCKED = "isMocked";
	public static final String API_MODEL_NAME = "name";
	public static final String API_MODEL_BASEURL = "baseUrl";
	public static final String API_MODEL_ENDPOINT = "endpoint";
	public static final String API_MODEL_METHOD = "method";

	// file path
	public static final String PATH_NOT_ENV = "env";
	public static final String PATH_ENV_LOCAL = "env/local";
	public static final String PATH_ENV_PROD = "env/prod";
	public static final String PATH_ENV_SVIL = "env/svil";
	public static final String PATH_ENV_TEST = "env/test";
	public static final String PATH_ENV_UTES = "env/utes";

	// apiList name
	public static final String API_LIST_GENERIC = "prodotti-generic";
	public static final String API_LIST_PRATICA = "pratica";
	public static final String API_LIST_MAPPING = "prodotti-mapping";
	public static final String API_LIST_WORKFLOW = "workflow";
	public static final String API_LIST_ALLEGATI = "allegati";
	public static final String API_LIST_QUADRO = "contratto-quadro";
	private static String[] apisListName = {API_LIST_GENERIC, API_LIST_PRATICA,
			API_LIST_MAPPING, API_LIST_WORKFLOW, API_LIST_ALLEGATI, API_LIST_QUADRO};

	// complete file path
	public static final String COMPLETE_PATH_NOT_ENV = "\\xdce-module-tbgtee\\apicatalog\\tbgtee\\api.json";
	public static final String COMPLETE_PATH_LOCAL = "\\xdce-module-tbgtee\\env\\local\\apicatalog\\tbgtee\\api.json";
	public static final String COMPLETE_PATH_PROD = "\\xdce-module-tbgtee\\env\\prod\\apicatalog\\tbgtee\\api.json";
	public static final String COMPLETE_PATH_SVIL = "\\xdce-module-tbgtee\\env\\svil\\apicatalog\\tbgtee\\api.json";
	public static final String COMPLETE_PATH_TEST = "\\xdce-module-tbgtee\\env\\test\\apicatalog\\tbgtee\\api.json";
	public static final String COMPLETE_PATH_UTES = "\\xdce-module-tbgtee\\env\\utes\\apicatalog\\tbgtee\\api.json";

	// array interface
	public static String[] getModuleDefaultProtocol() {
		return moduleDefaultProtocol;
	}

	public static void setModuleDefaultProtocol(
			String[] moduleDefaultProtocol) {
		Costant.moduleDefaultProtocol = moduleDefaultProtocol;
	}

	public static String[] getModuleDefaultHost() {
		return moduleDefaultHost;
	}

	public static void setModuleDefaultHost(String[] moduleDefaultHost) {
		Costant.moduleDefaultHost = moduleDefaultHost;
	}

	public static String[] getApisListName() {
		return apisListName;
	}

	public static void setApisListName(String[] apisListName) {
		Costant.apisListName = apisListName;
	}
}
