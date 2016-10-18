package com.bda.analyticsplatform.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.bda.analyticsplatform.utils.ApplicationUtils;

public class ServiceControllerImpl1 {
	public static void getRemoteFileSchema(String dsName,String dsFile,String dsFileType, String delimiter, String remotefile, String hostname) throws Exception{
		List<String> attributes = new ArrayList<String>();
		attributes = ApplicationUtils.getDelimitedFileSchema(remotefile,delimiter);
		
		Map<String,JSONObject> metadata = new HashMap<String,JSONObject>();
		JSONObject dsJson = new JSONObject();
		JSONArray attrNames = new JSONArray(attributes);
		
		dsJson.put("attributes", attrNames);
		dsJson.put("fileLocation", remotefile);
		dsJson.put("hostname", hostname);
		dsJson.put("dsFile", dsFile);
		dsJson.put("dsFileType", dsFileType);
		dsJson.put("delimiter", delimiter);
		

		metadata.put(dsName, dsJson);
		System.out.println(metadata);
	//	DataSourceContainer.setDsMetadata(metadata);
	}

	public static void getLocalFileSchema(String dsName, String dsFile,
			String dsFileType, String delimiter, String localFile) throws Exception {
		List<String> attributes = new ArrayList<String>();
		attributes = ApplicationUtils.getDelimitedFileSchema(localFile,delimiter);
		
		Map<String,JSONObject> metadata = new HashMap<String,JSONObject>();
		JSONObject dsJson = new JSONObject();
		JSONArray attrNames = new JSONArray(attributes);
		
		dsJson.put("attributes", attrNames);
		dsJson.put("fileLocation", localFile);
		dsJson.put("dsFile", dsFile);
		dsJson.put("dsFileType", dsFileType);
		dsJson.put("delimiter", delimiter);
		
		metadata.put(dsName, dsJson);
		System.out.println(metadata);
	//	DataSourceContainer.setDsMetadata(metadata);
		
	}

}
