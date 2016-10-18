package com.bda.analyticsplatform.models;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.bda.analyticsplatform.containers.DashBoardContainer;
import com.bda.analyticsplatform.utils.BDAConstants;
import com.google.gson.Gson;

public class DelimiterFileDSObject extends FileDSObject implements Serializable{
	
	private static final long serialVersionUID = -5314915076212545389L;
	private String delimiter;

	
	public String getDelimiter() {
		return delimiter;
	}

	public void setDelimiter(String delimiter) {
		this.delimiter = delimiter;
	}

	public DelimiterFileDSObject(String fileName,String dsType, String dataSourceName, String fileLocation, Map<String,List<String>> fileColumns,String fileType, String dsSubType,
			String delimiter) throws Exception{
		super(fileName,dsType, dataSourceName, fileLocation,fileColumns, fileType, dsSubType);
		this.delimiter = delimiter;	
	}
	
	public DelimiterFileDSObject(String fileName,String dsType, String dataSourceName, MultipartFile fileLocation, String fileType, String dsSubType,
			String delimiter) throws Exception{
		super(fileName,dsType, dataSourceName, fileLocation, fileType, dsSubType);
		ByteArrayInputStream in = new ByteArrayInputStream(fileLocation.getBytes());
		InputStream inputStream = fileLocation.getInputStream();
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
		
		URL resourceUrl = DashBoardContainer.class.getClassLoader().getResource(BDAConstants.DASHBOARDS_FILE_PATH);
		File file = new File(resourceUrl.toURI());
		OutputStream out = new FileOutputStream(file);
		OutputStream outputStream = new FileOutputStream(
				new File(BDAConstants.DATA_FILES_PATH + dataSourceName + "File-1.txt"));
		String line = bufferedReader.readLine();
		while (line != null) {
			outputStream.write(line.getBytes());
			outputStream.write("\n".getBytes());
			line = bufferedReader.readLine();
		}
		setFileLocation(BDAConstants.DATA_FILES_PATH + dataSourceName + "File-1.txt");
		outputStream.close();
		inputStream.close();
		this.delimiter = delimiter;
		
	}
	
	
	
	@Override
	public void populateFileColumns() throws Exception{
		BufferedReader bufferedReader = (BufferedReader)initializeReader();	
		List<String> columns = new ArrayList<String>();
		String line = null;
		while ((line = bufferedReader.readLine()) != null)
		{
		  String[] columnsArray = line.split(delimiter);
		  for (int i = 0; i < columnsArray.length; i++) {
			  columns.add(columnsArray[i]);
		}
		  break;
		}
		getFileColumns().put("File-1", columns);
	}
	
	@Override
	public String[] readRecord(Object reader) throws Exception {
		  String line = ((BufferedReader)reader).readLine();
		  if(line!=null){
		  return line.split(delimiter);
		  }else{
			  return null;
		  }
	}

	@Override
	public void closeReader(Object object) throws Exception {
		  BufferedReader  reader = (BufferedReader)object;
		  if(reader!=null){
			  reader.close();
		  }

	}
	
	
	@Override
	public Object initializeReader() throws Exception{
		InputStream inputStream = new FileInputStream(new File(getFileLocation()));
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
		return bufferedReader;
	}

	

	@Override
	public String toString() {

		return new Gson().toJson(this);
	}

	@Override
	public String getColumns(String tableName) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
