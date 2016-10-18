package com.bda.analyticsplatform.models;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.bda.analyticsplatform.utils.BDAConstants;
import com.google.gson.Gson;

public abstract class FileDSObject extends DSObject {
	public String getFileLocation() {
		return fileLocation;
	}

	public void setFileLocation(String fileLocation) {
		this.fileLocation = fileLocation;
	}

	private static final long serialVersionUID = -7941316568195000249L;
	private String dsSubType;
	private String fileType;
	private String fileLocation;
	private String fileName;

	private Map<String, List<String>> fileColumns;

	public FileDSObject() {

	}

	public FileDSObject(String fileName, String dsType, String dataSourceName, MultipartFile fileLocation,
			String fileType, String dsSubType) throws Exception {
		super(dsType, dataSourceName);
		this.fileColumns = new HashMap<String, List<String>>();
		this.fileType = fileType;
		this.dsSubType = dsSubType;
		this.fileName = fileName;
	

	}

	public FileDSObject(String fileName, String dsType, String dataSourceName, String fileLocation,
			Map<String, List<String>> fileColumns, String fileType, String dsSubType) throws Exception {
		super(dsType, dataSourceName);
		this.fileColumns = fileColumns;
		this.fileLocation = fileLocation;
		this.fileType = fileType;
		this.dsSubType = dsSubType;
	}

	public Map<String, List<String>> getFileColumns() {
		return fileColumns;
	}

	public void setFileColumns(Map<String, List<String>> fileColumns) {
		this.fileColumns = fileColumns;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	@Override
	public String toString() {

		return new Gson().toJson(this);
	}

	@Override
	public String queryForChart(Query query) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getDsSubType() {
		return dsSubType;
	}

	public void setDsSubType(String dsSubType) {
		this.dsSubType = dsSubType;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public abstract void populateFileColumns() throws Exception;

	public abstract String[] readRecord(Object reader) throws Exception;

	public String getField(String columnName, String[] record) {
		return record[getFileColumns().get("File-1").indexOf(columnName)];
	}
	public abstract Object initializeReader() throws Exception;

	public abstract void closeReader(Object reader) throws Exception ;

}
