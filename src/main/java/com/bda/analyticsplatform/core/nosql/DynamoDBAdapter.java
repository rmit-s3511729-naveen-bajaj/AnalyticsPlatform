package com.bda.analyticsplatform.core.nosql;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.ClasspathPropertiesFileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import com.bda.analyticsplatform.models.ChartParams;
import com.bda.analyticsplatform.models.Criteria;
import com.bda.analyticsplatform.models.DSObject;
import com.bda.analyticsplatform.models.Query;
import com.bda.analyticsplatform.utils.BDAConstants;
import com.bda.analyticsplatform.utils.PropertiesFileReader;
import com.mongodb.AggregationOutput;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

import flexjson.JSONSerializer;

public class DynamoDBAdapter extends DSObject implements Serializable {

	private static final long serialVersionUID = 1L;
	private String accessKey;
	private String secretKey; 
	private String region;
	private static AmazonDynamoDBClient client;
	private static DynamoDB dynamoDB;
	private ArrayList<String> tableNames;

	public DynamoDBAdapter(String access_key, String secret_key){
		this(access_key,secret_key,"us-west-2");
	}
	public DynamoDBAdapter(String accessKey, String secretKey, String region){
		try {
			this.accessKey = accessKey;
			this.secretKey = secretKey;
			this.region = region;
			setTableNames(new ArrayList<String>());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean connect() {
		try{
			PropertiesFileReader.loadProperties(BDAConstants.AWS_CREDENTIAL_FILE_PATH);
			PropertiesFileReader.setProperty("accessKey", accessKey);
			PropertiesFileReader.setProperty("secretKey", secretKey);
			AWSCredentialsProvider CREDENTIALS_PROVIDER = new ClasspathPropertiesFileCredentialsProvider(BDAConstants.AWS_CREDENTIAL_FILE_PATH);
			Region REGION = Region.getRegion(Regions.fromName(region));
			client = new AmazonDynamoDBClient(CREDENTIALS_PROVIDER);
			client.setRegion(REGION);
			dynamoDB = new DynamoDB(client); 
			return true;
		}
		catch(Exception e){
			return false;
		}
	}

	//	public void updateTables() {
	//		setTableNames(new ArrayList<String>(client.listTables().getTableNames()));
	//	}

	public void sum(String collectionName, String columnName) {
		// TODO Auto-generated method stub

	}
	
	private List<Map<String, String>> aggregateFn(String collectionName, List<String> dimensions,
			List<String> expressions, List<Criteria> criteria) {


		List<Map<String, String>> queryOutput = new ArrayList<Map<String, String>>();

		
		return queryOutput;
	}

	public String getChartData(String collectionName, ChartParams chartParams) {
		List<String> dimensions = new ArrayList<String>();
		dimensions.add(chartParams.getxAxisLabel() + "##" + chartParams.getxAxis());
		if (chartParams.getzAxis() != null && !chartParams.getzAxis().equalsIgnoreCase(BDAConstants.NULL_INDICATOR))
			dimensions.add(chartParams.getzAxisLabel() + "##" + chartParams.getzAxis());

		List<String> expressions = new ArrayList<String>();

		expressions
				.add(chartParams.getyAxisLabel() + "##" + chartParams.getAggregateFn() + "##" + chartParams.getyAxis());
		if (chartParams.getY1Axis() != null && !chartParams.getY1Axis().equalsIgnoreCase(BDAConstants.NULL_INDICATOR))
			expressions.add(chartParams.getyAxisLabel() + "##" + chartParams.getY1Axis() + "##"
					+ chartParams.getAggregateFnY1());

		JSONSerializer s = new JSONSerializer();

		return s.serialize(aggregateFn(collectionName, dimensions, expressions, chartParams.getFilterConditions()));
	}

	public String queryForChart(Query query) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getColumns(String tableName) {
		ScanRequest scanRequest = new ScanRequest()
			    .withTableName(tableName);
		ScanResult result = client.scan(scanRequest);
		System.out.println(result.getItems().get(0).keySet());
		JSONArray columnNames = new JSONArray(result.getItems().get(0).keySet());
		return columnNames.toString();
	}

	public ArrayList<String> getTableNames() {
		if(client == null){
			connect();
		}
		return new ArrayList<String>(client.listTables().getTableNames());
	}
	public void setTableNames(ArrayList<String> tableNames) {
		this.tableNames = tableNames;
	}
	public static String getTableData(String tableName) {
		JSONArray resultArray = new JSONArray();
		Table table = new Table(client, tableName);
		//table.geti
		 ScanRequest scanRequest = new ScanRequest()
	        		.withTableName(tableName);
		 ScanResult result = client.scan(scanRequest);
		 for (Map<String, AttributeValue> item : result.getItems()) {
			 JSONObject obj = new JSONObject();
			 for (Map.Entry<String, AttributeValue> entry : item.entrySet()) {
				 obj.put(entry.getKey(), entry.getValue());
			 }
			 resultArray.put(obj);
		}
		 System.out.println(resultArray);
		return  resultArray.toString();
	}
}
