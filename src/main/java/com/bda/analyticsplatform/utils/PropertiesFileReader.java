package com.bda.analyticsplatform.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesFileReader {
	
	private static Properties props = null;
	
	static {
		
		    props = new Properties();


		try {
		    InputStream inputStream = PropertiesFileReader.class.getClassLoader().getResourceAsStream(BDAConstants.APPLICATION_PROP_FILE_PATH);
		    props.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public static void loadProperties(String propFilePath){

		try {
		    InputStream inputStream = PropertiesFileReader.class.getClassLoader().getResourceAsStream(propFilePath);

			props.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void resetProperties() throws Exception{
		
		
	    InputStream inputStream = PropertiesFileReader.class.getClassLoader().getResourceAsStream(BDAConstants.APPLICATION_PROP_FILE_PATH);
		props.load(inputStream);
		
	}
	
	
	public static void resetProperties(String propFilePath) throws Exception{
		
	    InputStream inputStream = PropertiesFileReader.class.getClassLoader().getResourceAsStream(propFilePath);
		props.load(inputStream);
		
	}
	
	
	public static String getProperty(String propName){
		
		String propValue = props.getProperty(propName);
		if(propValue == null){
			//log error
		}
		
		return propValue;
		
	}
public static void setProperty(String propName, String propValue){
		props.setProperty(propName,propValue);
	}
}