package com.zip_project.service.costant;

public class Costant {

	private Costant() {
	}
	
	// RootNode path accepted
	public static final String ROOTNODE_APIS = "apis";
	public static final String ROOTNODE_MODULE_DEFAULTS = "moduleDefaults";

	// Module defaults accepted value
	private static String[] moduleDefaultProtocol = {"https", "http"};
	private static String[] moduleDefaultHost = {
			"trade-banca-svil.syssede.systest.sanpaoloimi.com",
			"localhost:4200", "trade-banca.sede.corp.sanpaoloimi.com",
			"trade-banca-svil.syssede.systest.sanpaoloimi.com",
			"trade-banca-test.syssede.systest.sanpaoloimi.com",
			"trade-banca-utes.syssede.systest.sanpaoloimi.com"};
	public static final String MODULE_DEFAULT_BASEURL = "/";
	public static final String MODULE_DEFAULT_SECURITY = "none";

	// attribute number accepted
	public static final Integer ROOTNODE_ATTRIBUTE = 2;
	public static final Integer MODULE_DEFAULTS_ATTRIBUTE = 4;
	
	// apis attribute costant
	public static final String APIS_ISMOCKED = "isMocked";
	public static final String APIS_NAME = "name";
	public static final String APIS_BASEURL = "baseUrl";
	public static final String APIS_ENDPOINT = "endpoint";
	public static final String APIS_METHOD = "method";
	
	// Array interface
	
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
}
