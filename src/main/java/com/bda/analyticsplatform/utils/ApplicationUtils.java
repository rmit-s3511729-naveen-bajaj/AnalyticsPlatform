package com.bda.analyticsplatform.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.validation.metadata.ReturnValueDescriptor;

import org.json.JSONObject;

import com.bda.analyticsplatform.containers.DashBoardContainer;
import com.bda.analyticsplatform.containers.DataSourceContainer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ApplicationUtils {
	public static List<String> getDelimitedFileSchema(String fileName,String delimiter) throws IOException{
		File file =new File(BDAConstants.DATA_FILES_PATH + fileName);
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String firstLine = reader.readLine();
		String columnNames[] = firstLine.split(delimiter);
		List<String> attributes = new ArrayList<String>();
		for (String cname : columnNames) {
			attributes.add(cname);
		}
		reader.close();
		return attributes;
	}
	
	public static String getFailureObject(String errorMessage){
		JSONObject errorObject = new JSONObject();
		errorObject.put(BDAConstants.SERVICE_STATUS, BDAConstants.FAILURE_INTEGRATION);
		if(errorMessage != null){
			errorObject.put(BDAConstants.SERVICE_OUTPUT, errorMessage);			
		}
		
		return errorObject.toString();
	}
	
	
	public static String getSuccessObject(String output){
		JSONObject successObject = new JSONObject();
		successObject.put(BDAConstants.SERVICE_STATUS, BDAConstants.SUCCESS_INTEGRATION);
		if(output != null){
			successObject.put(BDAConstants.SERVICE_OUTPUT, output);			
		}
		
		return successObject.toString();
	}
	
	public static void updateContainerObjects() throws Exception{
		
		DashBoardContainer.updateDashboardsToFile();
		DataSourceContainer.updateDataSourcesToFile();
		
	}

	
	public static String convertToJsonString(Object object){

		return new Gson().toJson(object);
	}
	
	public static String getutf8EncodedString(String value)throws Exception{
		
		return URLEncoder.encode(value.replaceAll(" ", "%20"), "UTF-8");
		
	}

	public static String getInstanceType(String type){
		for (String s: BDAConstants.NUMBER_DATATYPES) {
			if(type.toLowerCase().contains(s)){
				return BDAConstants.NUMBER_INDICATOR;
			}
		}
		return BDAConstants.STRING_INDICATOR;
		
	}

	public static boolean isDouble(String value) {
		try{
			Double.parseDouble(value);
		}catch(NumberFormatException e){
			return false;
		}
		return true;
	}

	
}
