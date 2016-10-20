package com.bda.analyticsplatform.impl;

import org.json.JSONArray;
import org.json.JSONObject;

import com.bda.analyticsplatform.containers.DataSourceContainer;
import com.bda.analyticsplatform.core.hive.HiveAdaptor;
import com.bda.analyticsplatform.core.nosql.DynamoDBAdapter;
import com.bda.analyticsplatform.core.nosql.MongoDBAdapter;
import com.bda.analyticsplatform.models.DSObject;
import com.bda.analyticsplatform.models.RDBMSDBObject;
import com.bda.analyticsplatform.utils.BDAConstants;
import com.bda.analyticsplatform.utils.BDAException;

public class DatasourceControllerImpl {

	public static String getAllDatasourceDetails() {
		return DataSourceContainer.getDsMetadata().toString();
	}

	public static void saveNewDataSource(String dsDetails) throws BDAException, Exception {

		JSONObject dsDetailsJson = new JSONObject(dsDetails);
		if (DataSourceContainer.findDS(dsDetailsJson.getString("name")) != null) {
			throw new BDAException(BDAConstants.DS_ALREADY_EXISTS + " :" + dsDetailsJson.getString("name"));
		} else {
			if (dsDetailsJson.getString("type").equalsIgnoreCase("RDBMS")) {
				DataSourceContainer.saveRDBMSDataSource(dsDetailsJson, null);
			}else if (dsDetailsJson.getString("type").equalsIgnoreCase("Distributed")) {
				DataSourceContainer.saveHiveDataSource(dsDetailsJson, null);
			}
			else if (dsDetailsJson.getString("type").equalsIgnoreCase("NoSql")) {
				if(dsDetailsJson.getString("subType").equalsIgnoreCase("MongoDB"))
					DataSourceContainer.saveMongoDBDataSource(dsDetailsJson, null);
				else if(dsDetailsJson.getString("subType").equalsIgnoreCase("DynamoDB"))
					DataSourceContainer.saveDynamoDBDataSource(dsDetailsJson, null);
			}
			DataSourceContainer.updateDataSourcesToFile();
		}

	}
	
	public static void editHiveDataSource(String editDsName, String dsDetails) throws BDAException, Exception {
	JSONObject dsDetailsJson = new JSONObject(dsDetails);
	if (dsDetailsJson.getString("dbType").equalsIgnoreCase("Distributed"))
		DataSourceContainer.editHiveDataSource(editDsName, dsDetailsJson);
//	else if (dsDetailsJson.getString("dbType").equalsIgnoreCase("NoSql"))
//		DataSourceContainer.editNoSqlDataSource(dsDetailsJson);
	DataSourceContainer.updateDataSourcesToFile();

}

		public static void editRDBMSDataSource(String editDsName, String dsDetails) throws BDAException, Exception {
		JSONObject dsDetailsJson = new JSONObject(dsDetails);
		if (dsDetailsJson.getString("dbType").equalsIgnoreCase("RDBMS"))
			DataSourceContainer.editRDBMSDataSource(editDsName, dsDetailsJson);
//		else if (dsDetailsJson.getString("dbType").equalsIgnoreCase("NoSql"))
//			DataSourceContainer.editNoSqlDataSource(dsDetailsJson);
		DataSourceContainer.updateDataSourcesToFile();

	}
		public static void editNoSQLDataSource(String editDsName, String dsDetails) throws BDAException, Exception {
			JSONObject dsDetailsJson = new JSONObject(dsDetails);
			if (dsDetailsJson.getString("subType").equalsIgnoreCase("dynamoDB"))
				DataSourceContainer.editDynamodbDataSource(editDsName, dsDetailsJson);
			else if (dsDetailsJson.getString("subType").equalsIgnoreCase("mongoDB"))
				DataSourceContainer.editMongodbDataSource(editDsName, dsDetailsJson);
			DataSourceContainer.updateDataSourcesToFile();

		}
		
		public static void deleteDataSource(String ds) throws Exception {

			DataSourceContainer.getDsMetadata().remove(DataSourceContainer.findDS(ds));
			DataSourceContainer.updateDataSourcesToFile();

		}

		public static String getDatasource(String dsName) {
			return DataSourceContainer.findDS(dsName).toString();
		}

		public static JSONArray getDatasourceTables(String dsName) {
			DSObject dsObject = DataSourceContainer.findDS(dsName);
			if(dsObject instanceof RDBMSDBObject){
				return new JSONArray(((RDBMSDBObject) dsObject).getTables());
			}
			else if(dsObject instanceof DynamoDBAdapter){
				return new JSONArray(((DynamoDBAdapter) dsObject).getTableNames());
			}
			else if(dsObject instanceof MongoDBAdapter){
				return new JSONArray(((MongoDBAdapter) dsObject).getTables());
			}else if(dsObject instanceof HiveAdaptor){
				return new JSONArray(((HiveAdaptor) dsObject).getTables());
			}
			return null;
		}

}
