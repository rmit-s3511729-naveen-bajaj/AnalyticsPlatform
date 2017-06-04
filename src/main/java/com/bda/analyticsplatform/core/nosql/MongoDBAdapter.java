package com.bda.analyticsplatform.core.nosql;

import java.io.Serializable;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONObject;
import org.json.simple.JSONArray;

import com.bda.analyticsplatform.models.Chart;
import com.bda.analyticsplatform.models.ChartParams;
import com.bda.analyticsplatform.models.Criteria;
import com.bda.analyticsplatform.models.DSObject;
import com.bda.analyticsplatform.models.Query;
import com.bda.analyticsplatform.utils.BDAConstants;
import com.google.gson.Gson;
import com.mongodb.AggregationOutput;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;

import flexjson.JSONSerializer;

public class MongoDBAdapter extends DSObject implements Serializable {

	private static final long serialVersionUID = 1193318491620985727L;
	private String subType;
	public String getSubType() {
		return subType;
	}

	public void setSubType(String subType) {
		this.subType = subType;
	}

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	private String hostname;
	private String port;
	private String dbName;
	private String username;
	private String password;
	private List<String> tables = new ArrayList<String>();

	transient Mongo mongo;
	transient DB db;
//	{
//	try {
//		if(mongo == null)
//			mongo = new Mongo(getHostname(), Integer.parseInt(getPort()));
//		if(db == null)
//			db = mongo.getDB(getDbName());
//		setAllCollections();
//		//boolean auth = db.authenticate(getUsername(), getPassword().toCharArray());
//	} catch (NumberFormatException | UnknownHostException e) {
//		e.printStackTrace();
//	}
//	}

	public boolean connect() {
		try {
			mongo = new Mongo(getHostname(), Integer.parseInt(getPort()));

			db = mongo.getDB(getDbName());
			setAllCollections();
			//boolean auth = db.authenticate(getUsername(), getPassword().toCharArray());
		} catch (NumberFormatException | UnknownHostException e) {
			return false;
		}
		return true;
	}

	public void setAllCollections() {
		System.out.println(db.getCollectionNames());
		setTables(new ArrayList<String>(db.getCollectionNames()));
		if (getTables().contains("system.indexes"))
			getTables().remove("system.indexes");

	}

	public List<String> getAllCollections() {
		return getTables();
	}

	public void updateTables() {

		setAllCollections();

	}
	public List<String> getTables() {
		return tables;
	}
	public void setTables(List<String> tables) {
		this.tables = tables;
	}

	@Override
	public String queryForChart(Query query) {
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String getColumns(String collectionName) {
		DBCollection table = db.getCollection(collectionName);
		DBObject row = table.findOne();

		//		Chart chart = DashBoardContainer.findDashboard(dashName).findChart(chartName);
		//		Map<String, String> columnType = chart.getTableColumns().get(collectionName);
		//		for (Entry<String, String> e : ((Map<String, String>) row.toMap()).entrySet()) {
		//			columnType.put(e.getKey(), e.getValue().getClass().getName());
		//		}
		//		System.out.println("columnType-----" + columnType);
		Set<String> schema = row.keySet();

		if (schema.contains("_id"))
			schema.remove("_id");
		JSONArray columns = new JSONArray();

		for (String s : schema) {
			columns.add(s);
		}

		return columns.toString();
	}

	@Override
	public String toString() {
		return new Gson().toJson(this);
	}

//	@Override
//	public void sum(String collectionName, String columnName) {
//
//	}

	private List<Map<String, String>> aggregateFn(String collectionName, List<String> dimensions,
			List<String> expressions, List<Criteria> criteria) {
		DBCollection coll = db.getCollection(collectionName);
		BasicDBList filter = new BasicDBList();

		for (Criteria cr : criteria) {

			filter.add(new BasicDBObject(cr.left, cr.right));
		}
		System.out.println("filter--" + filter);
		DBObject match = new BasicDBObject("$match", new BasicDBObject("$and", filter));

		Map<String, Object> dbObjIdMap = new HashMap<String, Object>();
		for (String dim : dimensions)
			dbObjIdMap.put(dim.split("##")[0], "$" + dim.split("##")[1]);
		DBObject groupAuthor = new BasicDBObject("_id", new BasicDBObject(dbObjIdMap));

		for (String exp : expressions) {
			String[] e = exp.split("##");
			switch (e[1]) {
			case "sum":
				groupAuthor.put(e[0], new BasicDBObject("$sum", "$" + e[2]));
				break;
			case "avg":
				groupAuthor.put(e[0], new BasicDBObject("$avg", "$" + e[2]));
				break;
			case "count":
				groupAuthor.put(e[0], new BasicDBObject("$sum", 1));
				break;
			case "max":
				groupAuthor.put(e[0], new BasicDBObject("$max", "$" + e[2]));
				break;
			case "min":
				groupAuthor.put(e[0], new BasicDBObject("$min", "$" + e[2]));
				break;
			default:
				break;
			}
		}
		DBObject group = new BasicDBObject("$group", groupAuthor);
		AggregationOutput output = null;
		if (criteria.size() > 0)
			output = coll.aggregate(match, group);
		else
			output = coll.aggregate(group);

		List<Map<String, String>> queryOutput = new ArrayList<Map<String, String>>();

		for (DBObject result : output.results()) {
			Map<String, String> oneRow = new HashMap<String, String>();
			JSONObject value = new JSONObject(result.get("_id").toString());
			for (String dim : dimensions) {
				oneRow.put(dim.split("##")[0], value.getString(dim.split("##")[0]));
			}
			for (String exp : expressions) {
				String[] e = exp.split("##");
				oneRow.put(e[0], result.get(e[0]).toString());
			}
			queryOutput.add(oneRow);
		}

		System.out.println("queryop----" + queryOutput);

		return queryOutput;
	}

	public String getChartData(String collectionName, Chart chart) {
		String x_axis = chart.getDimensions().get(0);
		String y_axis = chart.getExpressions().get(0).get("expField");
		String y_axis_agg_fn = chart.getExpressions().get(0).get("aggregate");
		String z_axis = null;
		if(chart.getDimensions().size() > 1){
			z_axis = chart.getDimensions().get(1);
		}
		String y1_axis = null;
		String y1_axis_agg_fn = null;
		
		if(chart.getExpressions().size() > 1){
			y1_axis = chart.getExpressions().get(1).get("expField");
			y1_axis_agg_fn = chart.getExpressions().get(1).get("aggregate");
		}
		
		List<String> dimensions = new ArrayList<String>();
		dimensions.add(x_axis + "##" + x_axis);
		if (z_axis != null)
			dimensions.add(x_axis + "##" + x_axis);

		List<String> expressions = new ArrayList<String>();

		expressions.add(y_axis + "##" + y_axis_agg_fn + "##" + y_axis);
		if (y1_axis != null)
			expressions.add(y1_axis + "##" + y1_axis_agg_fn + "##"
					+ y1_axis);

		JSONSerializer s = new JSONSerializer();

		return s.serialize(aggregateFn(collectionName, dimensions, expressions, chart.getFilterConditions()));

	}

}
