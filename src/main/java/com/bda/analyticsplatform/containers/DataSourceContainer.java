package com.bda.analyticsplatform.containers;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import com.bda.analyticsplatform.core.hive.HiveAdaptor;
import com.bda.analyticsplatform.core.nosql.DynamoDBAdapter;
import com.bda.analyticsplatform.core.nosql.MongoDBAdapter;
import com.bda.analyticsplatform.models.Chart;
import com.bda.analyticsplatform.models.DSObject;
import com.bda.analyticsplatform.models.DashBoard;
import com.bda.analyticsplatform.models.DelimiterFileDSObject;
import com.bda.analyticsplatform.models.ExcelFileDSObject;
import com.bda.analyticsplatform.models.FileDSObject;
import com.bda.analyticsplatform.models.RDBMSDBObject;
import com.bda.analyticsplatform.utils.BDAConstants;
import com.bda.analyticsplatform.utils.BDAException;

@SuppressWarnings("unchecked")
public class DataSourceContainer implements Serializable {

	private static final long serialVersionUID = -1827699205256068389L;

	private static ArrayList<DSObject> dsMetadata = new ArrayList<DSObject>();

	static {
		InputStream inputStream = null;
		InputStream buffer = null;
		ObjectInput input = null;
		try {
			//inputStream = DataSourceContainer.class.getClassLoader().getResourceAsStream(BDAConstants.DATASOURCE_FILE_PATH);
			inputStream = new FileInputStream(BDAConstants.DATASOURCE_FILE_PATH);
			buffer = new BufferedInputStream(inputStream);
			input = new ObjectInputStream(buffer);
			dsMetadata = (ArrayList<DSObject>) input.readObject();

			for (int i = 0; i < dsMetadata.size(); i++) {
				DSObject dsObject = dsMetadata.get(i);
				if(dsObject instanceof RDBMSDBObject){
					RDBMSDBObject object = (RDBMSDBObject)dsObject;
					object.connect();
					//dsMetadata.remove(i);
					//dsMetadata.add(i, outObject);
				}
//				else if(dsObject instanceof DynamoDBAdapter){
//					((DynamoDBAdapter) dsObject).connect();
//				}
				else if(dsObject instanceof MongoDBAdapter){
					((MongoDBAdapter) dsObject).connect();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			dsMetadata = new ArrayList<DSObject>();
		} finally {
			try {
				if (inputStream != null)
					inputStream.close();
				if (input != null)
					input.close();
			} catch (IOException e) {
				e.printStackTrace();
				dsMetadata = new ArrayList<DSObject>();
			}
		}
	}

	public static List<DSObject> getDsMetadata() {
		return dsMetadata;
	}

	public static List<String> getAllDSNames() {

		List<String> dsNames = new ArrayList<String>();
		for (DSObject ds : dsMetadata) {
			dsNames.add(ds.getName());
		}
		return dsNames;
	}

	public static void updateDataSourcesToFile() throws Exception {
		//URL resourceUrl = DataSourceContainer.class.getClassLoader().getResource(BDAConstants.DATASOURCE_FILE_PATH);
		//File file = new File(resourceUrl.toURI());
		OutputStream out = new FileOutputStream(BDAConstants.DATASOURCE_FILE_PATH);
		ObjectOutputStream oos = new ObjectOutputStream(out);
		oos.writeObject(dsMetadata);
		oos.close();
	}

	public static DSObject findDS(String ds) {
		if(dsMetadata.size() == 0){
			return null;
		}
		for (DSObject dataSource : dsMetadata) {
			if (dataSource.getName().equalsIgnoreCase(ds)) {
				return dataSource;
			}
		}
		return null;
	}

	public static void saveRDBMSDataSource(JSONObject dsDetailsJson, Integer serialNo)throws Exception {

		RDBMSDBObject object = new RDBMSDBObject();
		object.setType("RDBMS");
		object.setName(dsDetailsJson.getString("name"));
		object.setSubType(dsDetailsJson.getString("subType"));
		object.setDbName(dsDetailsJson.getString("dbName"));
		object.setHostname( dsDetailsJson.getString("hostname"));
		object.setPort( dsDetailsJson.getString("port"));
		object.setUsername( dsDetailsJson.getString("username"));
		object.setPassword( dsDetailsJson.getString("password"));
		object.setDriverClassName("com.mysql.jdbc.Driver");
		String url = "jdbc:mysql://"+object.getHostname()+":"+object.getPort()+"/"+object.getDbName();
				
		object.setJdbcURLPattern(url);
		object.connect();
		if(serialNo == null )
			object.setSerialNo(DataSourceContainer.getDsMetadata().size() + 1);
		else
			object.setSerialNo(serialNo);

		DataSourceContainer.getDsMetadata().add(object);
		DataSourceContainer.updateDataSourcesToFile();

	}
	
	public static void saveHiveDataSource(JSONObject dsDetailsJson,
			Integer serialNo) throws Exception {
			HiveAdaptor object = new HiveAdaptor();
			object.setType("Distributed");
			object.setName(dsDetailsJson.getString("name"));
			object.setSubType(dsDetailsJson.getString("subType"));
			object.setDbName(dsDetailsJson.getString("dbName"));
			object.setHostname( dsDetailsJson.getString("hostname"));
			object.setPort( dsDetailsJson.getString("port"));
			object.setUsername( dsDetailsJson.has("username")?dsDetailsJson.getString("username"):"");
			object.setPassword( dsDetailsJson.has("password")?dsDetailsJson.getString("password"):"");
			object.setDriverClassName("com.cloudera.hive.jdbc41.HS2Driver");
			String url = "jdbc:hive2://"+object.getHostname()+":"+object.getPort()+"/"+object.getDbName();
			object.setJdbcURLPattern(url);
			object.connect();
			if(serialNo == null )
				object.setSerialNo(DataSourceContainer.getDsMetadata().size() + 1);
			else
				object.setSerialNo(serialNo);

			DataSourceContainer.getDsMetadata().add(object);
			DataSourceContainer.updateDataSourcesToFile();

	}
	
	public static void editHiveDataSource(String editDsName, JSONObject dsDetailsJson)throws Exception {

		HiveAdaptor ds = (HiveAdaptor)DataSourceContainer.findDS(editDsName);
		int serialNo = ds.getSerialNo();
		DataSourceContainer.getDsMetadata().remove(ds);
		if(DataSourceContainer.findDS(dsDetailsJson.getString("dsName")) != null){
			throw new BDAException(BDAConstants.DS_ALREADY_EXISTS + " :" + dsDetailsJson.getString("dsName"));
		}else{
			DataSourceContainer.saveHiveDataSource(dsDetailsJson,serialNo);
		}

	}
	


	public static void editRDBMSDataSource(String editDsName, JSONObject dsDetailsJson)throws Exception {

		RDBMSDBObject ds = (RDBMSDBObject)DataSourceContainer.findDS(editDsName);
		int serialNo = ds.getSerialNo();
		DataSourceContainer.getDsMetadata().remove(ds);
		if(DataSourceContainer.findDS(dsDetailsJson.getString("name")) != null){
			throw new BDAException(BDAConstants.DS_ALREADY_EXISTS + " :" + dsDetailsJson.getString("name"));
		}else{
			DataSourceContainer.saveRDBMSDataSource(dsDetailsJson,serialNo);
		}

	}
	
	

	public static void editChartDetails(JSONObject chartDetailsJson, Integer serialNo, Chart chart) throws Exception {

		DashBoard dashBoard = DashBoardContainer.findDashboard(chartDetailsJson.getString("dashboardName"));

		DSObject ds = DataSourceContainer.findDS(chartDetailsJson.getString("dsName"));
		System.out.println(ds.getType());

		chart.setChartName(chartDetailsJson.getString("chartName"));
		if(serialNo == null)
			chart.setSerialNo(dashBoard.getCharts().size() + 1);
		else
			chart.setSerialNo(serialNo);
		if(ds.getType().equalsIgnoreCase("RDBMS")){
		
			RDBMSDBObject rdbmsdbObject = (RDBMSDBObject) ds;

		}
		else if(ds.getType().equalsIgnoreCase("FILE")){
			FileDSObject fileDSObject = (FileDSObject) ds;
		}
		chart.setDatasourceName(chartDetailsJson.getString("dsName"));
		//dashBoard.getCharts().add(chart);

		DashBoardContainer.updateDashboardsToFile();
	}

	public static void saveChartDetails(JSONObject chartDetailsJson, Integer serialNo) throws Exception {

		DashBoard dashBoard = DashBoardContainer.findDashboard(chartDetailsJson.getString("dashboardName"));

		DSObject ds = DataSourceContainer.findDS(chartDetailsJson.getString("dsName"));
		System.out.println(ds.getType());
		Chart chart = new Chart();
		chart.setChartName(chartDetailsJson.getString("chartName"));
		if(serialNo == null)
			chart.setSerialNo(dashBoard.getCharts().size() + 1);
		else
			chart.setSerialNo(serialNo);
		if(ds.getType().equalsIgnoreCase("RDBMS")){
		
			RDBMSDBObject rdbmsdbObject = (RDBMSDBObject) ds;

		}
		else if(ds.getType().equalsIgnoreCase("FILE")){
			FileDSObject fileDSObject = (FileDSObject) ds;
		}
		chart.setDatasourceName(chartDetailsJson.getString("dsName"));
		dashBoard.getCharts().add(chart);

		DashBoardContainer.updateDashboardsToFile();
	}

//	public static RDBMSDBObject connectRDBMSDataBase(RDBMSDBObject object) throws Exception{
//
//		PropertiesFileReader.resetProperties(BDAConstants.DB_CONFIG_PROP_FILE_PATH);
//		Properties properties = new Properties();
//		properties.setProperty("dbName", object.getDbName());
//		properties.setProperty("host", object.getHostname());
//		properties.setProperty("port", object.getPort());
//		properties.setProperty("username", object.getUsername());
//		properties.setProperty("password", object.getPassword());
//		properties.setProperty("url", PropertiesFileReader.getProperty("url"));
//		properties.setProperty("driver", PropertiesFileReader.getProperty("driver"));
//		properties.setProperty("dbType", object.getSubType() );
//		
//		ApplicationContext context = null;
//		context = new ClassPathXmlApplicationContext(BDAConstants.BEANS_XML_FILE_PATH);
//
//		RDBMSDBObject outObject = (RDBMSDBObject) context.getBean("rDBMSDBObject");
//
//		outObject.updateTables();
//
//		outObject.setName(object.getName());
//		outObject.setType("RDBMS");
//
//		return outObject;
//	}
//	
//	
//	public static HiveAdaptor connectHiveDataBase(HiveAdaptor object) throws Exception{
//
//		PropertiesFileReader.resetProperties(BDAConstants.DB_CONFIG_PROP_FILE_PATH);
//		Properties properties = new Properties();
//		properties.setProperty("hive.dbName", object.getDbName());
//		properties.setProperty("hive.host", object.getHostname());
//		properties.setProperty("hive.port", object.getPort());
//		properties.setProperty("hive.username", object.getUsername());
//		properties.setProperty("hive.password", object.getPassword());
//		properties.setProperty("url", PropertiesFileReader.getProperty("hive.url"));
//		properties.setProperty("driver", PropertiesFileReader.getProperty("hive.driver"));
//		properties.setProperty("hive.dbType", object.getSubType() );
//		
//		ApplicationContext context = null;
//		context = new ClassPathXmlApplicationContext(BDAConstants.BEANS_XML_FILE_PATH);
//
//		HiveAdaptor outObject = (HiveAdaptor) context.getBean("hiveDBObject");
//
//		outObject.updateTables();
//
//		outObject.setName(object.getName());
//		outObject.setType("Distributed");
//
//		return outObject;
//	}


	public static void editFileDataSource(String fileName,String oldDSName,String dsName, MultipartFile fileLocation, String dbType,
			String delimiter, String dsType,String dsFileType)throws Exception {

		FileDSObject ds = (FileDSObject)DataSourceContainer.findDS(oldDSName);
		int serialNo = ds.getSerialNo();
		DataSourceContainer.getDsMetadata().remove(ds);
		if(DataSourceContainer.findDS(dsName) != null){
			throw new BDAException(BDAConstants.DS_ALREADY_EXISTS + " :" + dsName);
		}else{
			DataSourceContainer.saveFileDataSource(fileName,dsName,  fileLocation,  dbType,  delimiter,
					dsType,dsFileType,serialNo);
		}

	}

	public static void saveFileDataSource(String fileName,String dsName, MultipartFile fileLocation, String dbType,
			String delimiter, String dsType,String dsFileType, Integer serialNo) throws Exception {


		FileDSObject fileObject = null;

		if(dsFileType.equalsIgnoreCase("delimiter")){

			fileObject = new DelimiterFileDSObject(fileName,dbType,dsName,fileLocation, dsFileType,dsType, delimiter);
		}
		else if(dsFileType.equalsIgnoreCase("excel")){
			fileObject = new ExcelFileDSObject(fileName,dbType,dsName,fileLocation, dsFileType,dsType);
		}

		if(serialNo == null )
			fileObject.setSerialNo(DataSourceContainer.getDsMetadata().size() + 1);
		else
			fileObject.setSerialNo(serialNo);
		fileObject.populateFileColumns();
		System.out.println(fileObject);
		DataSourceContainer.getDsMetadata().add(fileObject);

	}

	public static void editFileDataSource(String oldDSName, String dsName,
			String dbType, String delimiter, String dsType, String dsFileType) throws Exception {
		FileDSObject ds = (FileDSObject)DataSourceContainer.findDS(oldDSName);
		int serialNo = ds.getSerialNo();
		DataSourceContainer.getDsMetadata().remove(ds);
		if(DataSourceContainer.findDS(dsName) != null){
			throw new BDAException(BDAConstants.DS_ALREADY_EXISTS + " :" + dsName);
		}else{
			DataSourceContainer.saveFileDataSource(ds.getFileName(),dsName,ds.getFileLocation(),ds.getFileColumns(),dbType, delimiter,
					dsType,dsFileType,serialNo);
		}


	}

	public static void saveFileDataSource(String fileName,String dsName, String fileLocation,Map<String,List<String>> fileColumns,
			String dbType, String delimiter, String dsType, String dsFileType,
			Integer serialNo) throws Exception {

		FileDSObject fileObject = null;

		if(dsFileType.equalsIgnoreCase("delimiter")){
			fileObject = new DelimiterFileDSObject(fileName,dbType,dsName,fileLocation,fileColumns, dsFileType,dsType, delimiter);

		}
		else if(dsFileType.equalsIgnoreCase("excel")){
			fileObject = new ExcelFileDSObject(fileName,dbType,dsName,fileLocation,fileColumns, dsFileType,dsType);
		}

		if(serialNo == null )
			fileObject.setSerialNo(DataSourceContainer.getDsMetadata().size() + 1);
		else
			fileObject.setSerialNo(serialNo);
		fileObject.populateFileColumns();
		DataSourceContainer.getDsMetadata().add(fileObject);


	}
	

	public static void saveMongoDBDataSource(JSONObject dsDetailsJson,
			Integer serialNo) throws Exception {
			MongoDBAdapter object = new MongoDBAdapter();
			object.setType("NoSQL");
			object.setName(dsDetailsJson.getString("name"));
			object.setSubType(dsDetailsJson.getString("subType"));
			object.setDbName(dsDetailsJson.getString("dbName"));
			object.setHostname( dsDetailsJson.getString("hostname"));
			object.setPort( dsDetailsJson.getString("port"));
			object.setUsername( dsDetailsJson.has("username")?dsDetailsJson.getString("username"):"");
			object.setPassword( dsDetailsJson.has("password")?dsDetailsJson.getString("password"):"");
		if(serialNo == null )
			object.setSerialNo(DataSourceContainer.getDsMetadata().size() + 1);
		else
			object.setSerialNo(serialNo);
		
		object.connect();

		DataSourceContainer.getDsMetadata().add(object);
		DataSourceContainer.updateDataSourcesToFile();

	}
	public static void saveDynamoDBDataSource(JSONObject dsDetailsJson,
			Integer serialNo) throws Exception {
		DynamoDBAdapter object = new DynamoDBAdapter(dsDetailsJson.getString("accessKey"), dsDetailsJson.getString("secretKey"));
		object.setName(dsDetailsJson.getString("name"));
		object.setType("NoSQL");
		object.connect();

		DataSourceContainer.getDsMetadata().add(object);
		DataSourceContainer.updateDataSourcesToFile();


	}

	public static void editDynamodbDataSource(String editDsName, JSONObject dsDetailsJson) throws Exception {
		DynamoDBAdapter ds = (DynamoDBAdapter)DataSourceContainer.findDS(editDsName);
		int serialNo = ds.getSerialNo();
		DataSourceContainer.getDsMetadata().remove(ds);
		if(DataSourceContainer.findDS(dsDetailsJson.getString("name")) != null){
			throw new BDAException(BDAConstants.DS_ALREADY_EXISTS + " :" + dsDetailsJson.getString("name"));
		}else{
			DataSourceContainer.saveDynamoDBDataSource(dsDetailsJson,serialNo);
		}
		
	}

	public static void editMongodbDataSource(String editDsName, JSONObject dsDetailsJson) throws Exception {
		MongoDBAdapter ds = (MongoDBAdapter)DataSourceContainer.findDS(editDsName);
		int serialNo = ds.getSerialNo();
		DataSourceContainer.getDsMetadata().remove(ds);
		if(DataSourceContainer.findDS(dsDetailsJson.getString("name")) != null){
			throw new BDAException(BDAConstants.DS_ALREADY_EXISTS + " :" + dsDetailsJson.getString("name"));
		}else{
			DataSourceContainer.saveMongoDBDataSource(dsDetailsJson,serialNo);
		}
		
		
	}
	
//	public static void editHiveDataSource(String editDsName, JSONObject dsDetailsJson) throws Exception {
//		MongoDBAdapter ds = (MongoDBAdapter)DataSourceContainer.findDS(editDsName);
//		int serialNo = ds.getSerialNo();
//		DataSourceContainer.getDsMetadata().remove(ds);
//		if(DataSourceContainer.findDS(dsDetailsJson.getString("name")) != null){
//			throw new BDAException(BDAConstants.DS_ALREADY_EXISTS + " :" + dsDetailsJson.getString("name"));
//		}else{
//			DataSourceContainer.saveMongoDBDataSource(dsDetailsJson,serialNo);
//		}
//		
//		
//	}
	
//	public static void editNoSQLDataSource(JSONObject dsDetailsJson) throws Exception {
//				
//		 NoSqlDSObject ds = (NoSqlDSObject)DataSourceContainer.findDS(dsDetailsJson.getString("oldDSName"));
//		int serialNo = ds.getSerialNo();
//		DataSourceContainer.getDsMetadata().remove(ds);
//		if(DataSourceContainer.findDS(dsDetailsJson.getString("dsName")) != null){
//			throw new BDAException(BDAConstants.DS_ALREADY_EXISTS + " :" + dsDetailsJson.getString("dsName"));
//		}else{
//			DataSourceContainer.saveNoSqlDataSource(dsDetailsJson,serialNo);
//		}
//
//
//
//	}








}
