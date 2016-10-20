package com.bda.analyticsplatform.utils;

public class BDAConstants {
	
	public static final String DATA_FILES_PATH = "/DataFiles/";
	
	public static final String FAILURE_INTEGRATION = "&failue&";
	
	public static final String SUCCESS_INTEGRATION = "&success&";

	public static final String SERVICE_STATUS = "status";

	public static final String SERVICE_OUTPUT = "output";

	public static final Object FIRST_DASH_INDICATOR = "&DASH_FIRST&";

	public static final String NO_DASHBOARD_ERROR_MESSAGE = "No Dashboard Present";
	
	public static final String NO_CHART_ERROR_MESSAGE = "No Chart Present";

	public static final String DASH_ALREADY_EXISTS = "Dashboard Already Exists";

	//public static final String DASHBOARDS_FILE_PATH = "/Users/naveen/Naveen_Bajaj/containers/dashboard.ser";
	public static final String DASHBOARDS_FILE_PATH = "/home/ec2-user/naveen_bajaj/bda/dashboard.ser";
	
	//public static final String DATASOURCE_FILE_PATH = "/Users/naveen/Naveen_Bajaj/containers/datasource.ser";
	public static final String DATASOURCE_FILE_PATH = "/home/ec2-user/naveen_bajaj/bda/datasource.ser";
	
	public static final String CHARTS_FILE_PATH = "/Users/naveen/Naveen_Bajaj/containers/chart.ser";;

	public static final String APPLICATION_PROP_FILE_PATH = "/properties/dbConfig.properties";	

	public static final String DB_CONFIG_PROP_FILE_PATH = "/properties/db.properties";

	public static final String DS_ALREADY_EXISTS = "DataSource Already Exists";

	public static final String BEANS_XML_FILE_PATH = "Beans.xml";

	public static final String NONE = "none";

	public static final String NULL_INDICATOR = "&null&";
	
	public static final String[] STRING_DATATYPES= {"char","varchar","varchar2","String"};
	
	public static final String[] NUMBER_DATATYPES= {"int","byte","short","number","float","double","long"};
	
	public static final String NUMBER_INDICATOR = "number";
	
	public static final String STRING_INDICATOR = "string";

	public static final String AWS_CREDENTIAL_FILE_PATH = "/properties/AwsCredentials.properties";

}
