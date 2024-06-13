package com.zip_project.service.costant;

public class Costant {

	private Costant() {
	}

	// json validation
	public enum JsonValidation {
		VALIDATED("validated"), NOT_VALIDATED("not_validated");

		private final String value;

		JsonValidation(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}

	// extract status
	public enum ExtractStatus {
		INITIALIZED("initialized"), CREATED("created");

		private final String value;

		ExtractStatus(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}

	// test status
	public enum TestStatus {
		TESTED("initialized"), NOT_TESTED("not_tested");

		private final String value;

		TestStatus(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}

	// report status
	public enum ReportStatus {
		ABSENT("absent"), STARTED("started"), COMPLETED("completed");

		private final String value;

		ReportStatus(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}

	// attribute number accepted
	public static final Integer ROOTNODE_ATTRIBUTE = 2;
	public static final Integer MODULE_DEFAULTS_ATTRIBUTE = 4;

	// rootNode path accepted
	public enum RootNodePath {
		APIS("apis"), MODULE_DEFAULTS("moduleDefaults");

		private final String value;

		RootNodePath(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}

	// module defaults attrubute name
	public enum ModuleDefaultsAttributeName {
		PROTOCOL("protocol"), BASEURL("baseUrl"), HOST("host"), SECURITY(
				"security");

		private final String value;

		ModuleDefaultsAttributeName(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}

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

	// apiList attribute name
	public enum ApiListAttributeName {
		NAME("name");

		private final String value;

		ApiListAttributeName(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}

	// api models attribute name costant
	public enum ApiModelAttribute {
		ISMOCKED("isMocked"), NAME("name"), BASEURL("baseUrl"), ENDPOINT(
				"endpoint"), METHOD("method");

		private final String value;

		ApiModelAttribute(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}

	// file path
	public enum FilePath {
		NOT_ENV("env"), ENV_LOCAL("env/local"), ENV_PROD("env/prod"), ENV_SVIL(
				"env/svil"), ENV_TEST("env/test"), ENV_UTES("env/utes");

		private final String value;

		FilePath(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}

	// ApiList name
	public enum ApiListName {
		GENERIC("prodotti-generic"), PRATICA("pratica"), MAPPING(
				"prodotti-mapping"), WORKFLOW("workflow"), ALLEGATI(
						"allegati"), QUADRO("contratto-quadro");

		private final String value;

		ApiListName(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}

	public static final String[] API_LIST_NAMES = {
			ApiListName.GENERIC.getValue(), ApiListName.PRATICA.getValue(),
			ApiListName.MAPPING.getValue(), ApiListName.WORKFLOW.getValue(),
			ApiListName.ALLEGATI.getValue(), ApiListName.QUADRO.getValue()};

	// complete file path
	public enum CompleteFilePath {
		NOT_ENV("\\xdce-module-tbgtee\\apicatalog\\tbgtee\\api.json"), LOCAL(
				"\\xdce-module-tbgtee\\env\\local\\apicatalog\\tbgtee\\api.json"), PROD(
						"\\xdce-module-tbgtee\\env\\prod\\apicatalog\\tbgtee\\api.json"), SVIL(
								"\\xdce-module-tbgtee\\env\\svil\\apicatalog\\tbgtee\\api.json"), TEST(
										"\\xdce-module-tbgtee\\env\\test\\apicatalog\\tbgtee\\api.json"), UTES(
												"\\xdce-module-tbgtee\\env\\utes\\apicatalog\\tbgtee\\api.json");

		private final String value;

		CompleteFilePath(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}

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

	public static String[] getApiListNames() {
		return API_LIST_NAMES;
	}
}
