
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.auth.*;
import com.amazonaws.services.dynamodbv2.*;
import com.amazonaws.services.dynamodbv2.model.*;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;


public class Student 
{
	public static final AWSCredentialsProvider CREDENTIALS_PROVIDER = new ClasspathPropertiesFileCredentialsProvider();
	public static final Region REGION = Region.getRegion(Regions.US_WEST_2);
	public static String TABLE_NAME;
	public static AmazonDynamoDBClient DB_CLIENT;
	
	public Student(AmazonDynamoDBClient client, String tableName)
	{
		DB_CLIENT = client;
		TABLE_NAME = tableName;
		//Set the region of the client 
		client.setRegion(REGION);
		
		//Check is table exists if it does not, create it
		if(!doesTableExist(client, tableName))
		{
			client.createTable(new CreateTableRequest()
            .withTableName(tableName)
            //Define tables key schema (Primary key)
            .withKeySchema(new KeySchemaElement("username", KeyType.HASH))
            .withAttributeDefinitions(new AttributeDefinition("username", ScalarAttributeType.S))
            .withProvisionedThroughput(new ProvisionedThroughput(50l, 50l)));
	        System.out.println("Created Table: " + client.describeTable(tableName));
		}
	}
	
	//Function to insert a student into the database
	//Returns 0 - If student already exists - insertion fails
	//Returns 1 - Insertion was successful
	//Returns 2 - An unknown error occurred, insertion failed
	
	public int insertToDB(AmazonDynamoDBClient client, String tableName, String username, String firstName, String lastName)
	{
		//Formulate a get item request to check for existence of the user to be inserted
        GetItemRequest itemRequest = new GetItemRequest();
        itemRequest.setTableName(tableName);
        itemRequest.addKeyEntry("username", new AttributeValue(username));
        
        //Store result of request for success/failure
        GetItemResult itemResult = new GetItemResult();
        itemResult = client.getItem(itemRequest);
        
        //If item is not a duplicate continue
        if(itemResult.getItem() == null)
        {
        	System.out.println("Item not found");
        	//Create user Map
        	Map<String, AttributeValue> item = newItem(username, firstName, lastName);
    		
        	//Formulate request to insert data into database
            PutItemRequest putItemRequest = new PutItemRequest(tableName, item);
            PutItemResult putItemResult = new PutItemResult();
            /*Fix here to insert content in putItemRequest */
           
            
            //Sanity call to .toString() to remove unused
            putItemResult.toString();
            
            //Double check whether item was inserted by second get request
            itemRequest = new GetItemRequest();
            itemRequest.setTableName(tableName);
            itemRequest.addKeyEntry("username", item.get("username"));
            
            itemResult = new GetItemResult();
            itemResult = client.getItem(itemRequest);
            
            //If request returns null - insertion successful
            if(itemResult.getItem() != null)
            {
            	System.out.println("Item: " + itemResult.getItem().get("username").getS() + " : " + itemResult.getItem().get("firstname").getS() + " inserted.");
            	return 1;
            }
            //If request is not null, an unknown error occurred - insertion fails
            else
            {
            	System.out.println("Item not inserted");
            	return 2;
            }
        }
        //If item is found return 0 to show that user already exists
        else
        {
        	System.out.println("Item:" + itemResult.getItem().get("username").getS() + " already exists.");
        	return 0;
        }
	}
	
	//Function to remove a user from the database
	//Returns 0 - User does not exist - can't delete
	//Returns 1 - User found and deleted - delete successful
	//Returns 2 - User found but not deleted - delete unsuccessful
	public int removeFromDB(AmazonDynamoDBClient client, String tableName, String username)
	{
		//Formulate get item request to see if user exists
		GetItemRequest itemRequest = new GetItemRequest();
        itemRequest.setTableName(tableName);
        itemRequest.addKeyEntry("username", new AttributeValue(username));
        
        //Send request
        GetItemResult itemResult = new GetItemResult();
        itemResult = client.getItem(itemRequest);
        
        /*Fix here to implement delete functionality like insertion with proper return value
         * You need to use DeleteItemRequest and DeleteItemResult here */
        return 2;
	}
	
	//Function for querying a specific attribute of the database
	//Returns a List of user maps
	//Selected attribute is passed in to query with value
	public List<Map<String, AttributeValue>> getByAttribute(AmazonDynamoDBClient client, String tableName, String attribute,String attributeVal)
	{
		//Setup scan filters
		Condition scanFilterCondition = new Condition()
	    	.withComparisonOperator(ComparisonOperator.EQ.toString())
	    	.withAttributeValueList(new AttributeValue().withS(attributeVal));
		//Setup map for scan conditions
		Map<String, Condition> conditions = new HashMap<String, Condition>();
		//Apply scan conditions
		conditions.put(attribute, scanFilterCondition);
		//Formulate scan request
		ScanRequest scanRequest = new ScanRequest()
	    	.withTableName(tableName)
	    	.withScanFilter(conditions);
		//Scan database for presence of query values
		ScanResult result = client.scan(scanRequest);
		
		System.out.println(result.getCount());
		//Print results to console for diagnostics
		for(int i = 0; i < result.getCount(); i++)
		{
			System.out.println(result.getItems().get(i).get("username").getS());
			
		}
		//Return a list of results
		return result.getItems();
	}
	
	
	/*Function for generating a user map for storage in the database
	* Essentially generates what attributes will exist for each entry
	 */
	public static Map<String, AttributeValue> newItem(String username, String firstName, String lastName) 
	{
        Map<String, AttributeValue> item = new HashMap<String, AttributeValue>();
        item.put("username", new AttributeValue(username));
        item.put("firstname", new AttributeValue(firstName));
        item.put("lastname", new AttributeValue(lastName));  
        item.put("common", new AttributeValue("all")); 
        return item;
    }
	
	/* This function reads a CSV file from your S3 bucket and insert all 
	 * student data inside that file in dynamodb table
	 * Fix this function so it can insert all data in CSV file to your dynamodb*/
	public void insertAllStudents(AmazonDynamoDBClient client,String tableName)
	{
		String bucketName = "my-bucket-name";
		String key = "student.csv";
		String line;
		
		AmazonS3 s3Client = new AmazonS3Client(CREDENTIALS_PROVIDER);        
		S3Object object = s3Client.getObject(
		                  new GetObjectRequest(bucketName, key));
		try{
			InputStream objectData = object.getObjectContent();
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(objectData));
			while ((line = reader.readLine()) != null) {
				String[] item = line.split(",");
				// implement your functionality here to insert student data in item 
				// array into your dynamodb table
			}
			reader.close();
			objectData.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		System.out.println("All students are insereted in from S3 bucket");
		
	}
	
	 private boolean doesTableExist(AmazonDynamoDBClient dynamo, String tableName) {
        try {
        	DescribeTableResult table = dynamo.describeTable(tableName);
            return TableStatus.ACTIVE.toString().equals(table.getTable().getTableStatus());
        } catch (ResourceNotFoundException rnfe) {
            return false;
        }
	}
	    
	
}
