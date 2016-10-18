
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.ClasspathPropertiesFileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.TableCollection;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ListTablesResult;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import com.bda.analyticsplatform.models.ChartParams;
import com.bda.analyticsplatform.models.DSObject;
import com.bda.analyticsplatform.models.Query;

public class DynamoDBTest extends DSObject implements Serializable {

	private static final long serialVersionUID = 1L;
	private String accessKey;
	private String secretKey; 
	private String region;
	private static AmazonDynamoDBClient client;
	private static DynamoDB dynamoDB;
	private ArrayList<String> tableNames;

	public DynamoDBTest(String access_key, String secret_key){
		this(access_key,secret_key,"us-west-2");
	}
	public DynamoDBTest(String accessKey, String secretKey, String region){
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
		Properties prop = new Properties();
		OutputStream output = null;
		try{
//			output = new FileOutputStream("/Users/naveen/Naveen_Bajaj/BDA/source/src/main/java/AwsCredentials.properties");
//
//			// set the properties value
//			prop.setProperty("accessKey", accessKey);
//			prop.setProperty("secretKey", secretKey);
//
//			// save properties to project root folder
//			prop.store(output, null);
//			PropertiesFileReader.loadProperties("/Users/naveen/Naveen_Bajaj/BDA/source/src/main/java/AwsCredentials.properties");
//			PropertiesFileReader.setProperty("accessKey", accessKey);
//			PropertiesFileReader.setProperty("secretKey", secretKey);
			AWSCredentialsProvider CREDENTIALS_PROVIDER = new ClasspathPropertiesFileCredentialsProvider("/Users/naveen/Naveen_Bajaj/BDA/source/src/main/java/AwsCredentials.properties");
			Region REGION = Region.getRegion(Regions.fromName(region));
			client = new AmazonDynamoDBClient(CREDENTIALS_PROVIDER);
			client.setRegion(REGION);
			dynamoDB = new DynamoDB(client); 
			 TableCollection<ListTablesResult> tables = dynamoDB.listTables();
		        Iterator<Table> iterator = tables.iterator();

		        System.out.println("Listing table names");

		        while (iterator.hasNext()) {
		            Table table = iterator.next();
		            System.out.println(table.getTableName());
		        }
			return true;
		}
		catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

	//	public void updateTables() {
	//		setTableNames(new ArrayList<String>(client.listTables().getTableNames()));
	//	}

	public void sum(String collectionName, String columnName) {
		// TODO Auto-generated method stub

	}

	public String getChartData(String CollectionName, ChartParams chartParams) {
		// TODO Auto-generated method stub
		return null;
	}

	public String queryForChart(Query query) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getColumns(String tableName) {
		ScanRequest scanRequest = new ScanRequest()
				.withTableName(tableName);

		ScanResult result = client.scan(scanRequest);
		for (Map<String, AttributeValue> item : result.getItems()){
			System.out.println(item);
		}
		return "";
	}

		public static void main(String[] args) {
			DynamoDBTest dbAdapter = new DynamoDBTest("AKIAJKGA5CD3TJKKKFTQ","2RNVLoOHCuSerm98FNzU1DmxY8U33BrUfbzAnYn8","us-west-2");
			dbAdapter.connect();
			
			//DynamoDB dynamoDB = new DynamoDB(client);

	        //Table table = dynamoDB.getTable("baby_names_data");
	        
	        Map<String, AttributeValue> expressionAttributeValues = 
	        	    new HashMap<String, AttributeValue>();
	        	expressionAttributeValues.put(":frequencyVal", new AttributeValue().withN("500")); 
	        	expressionAttributeValues.put(":yearVal", new AttributeValue().withN("2011")); 
	        ScanRequest scanRequest = new ScanRequest()
	        		.withTableName("baby_names_data")
	        	    .withFilterExpression("frequency > :frequencyVal AND yyyy = :yearVal")
	        	    //.withFilterExpression("yyyy = :yearVal")
	        	    .withProjectionExpression("Id,baby_name,frequency,yyyy")
	        	    .withExpressionAttributeValues(expressionAttributeValues);
//	        QuerySpec spec = new QuerySpec()
//	        		.withKeyConditionExpression("Id")
////	        	    .withAttributesToGet("gender, frequency")
//	        	    .withMaxResultSize(5);
	        		ScanResult result = client.scan(scanRequest);
	        for (Map<String, AttributeValue> item : result.getItems()) {
	            System.out.println(item);
	        }
			
			//Table table = dynamoDB.getTable("baby_names_data");
//			ScanRequest scanRequest = new ScanRequest()
//				    .withTableName("baby_names");
//			ScanResult result = client.scan(scanRequest);
//			    System.out.println(result.getItems().get(0).keySet());
			//System.out.println(table.describe());
//			int counter = 1;
//			try {
//				BufferedReader bufferedReader = new BufferedReader(new FileReader("/Users/naveen/Desktop/cloud_computing/lab3/baby_names_lab3.csv"));
//				String line=bufferedReader.readLine();
//				Item item = null;
//				while((line = bufferedReader.readLine()) != null){
//					String[] values = line.split(",");
//					if(!values[3].equalsIgnoreCase("2012")){
//					item = new Item()
//							.withPrimaryKey("Id", counter+"")
//			                .withString("baby_name", values[0])
//			                .withString("gender", values[1])
//			                .withLong("frequency", Long.parseLong(values[2]))
//			                .withLong("yyyy", Long.parseLong(values[3]));
//					
//					table.putItem(item);
//					counter++;
//					if(counter == 5000){
//						break;
//					}
//					}
//				}
//				bufferedReader.close();
//			} catch (FileNotFoundException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
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
}
