package com.bda.analyticsplatform.models;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;

import com.google.gson.Gson;

import flexjson.JSONSerializer;
public class RDBMSDBObject extends DSObject implements Serializable{

	private static final long serialVersionUID = -4089606423119395093L;


	//	@Autowired
	//	private CustomJDBCTemplate jdbcTemplate;
	//	private DataSource dataSource;
	private String subType;
	private String hostname;
	private String port;
	private String driverClassName;
	private String jdbcURLPattern;
	private String dbName;
	private String username;
	private String password;
	private List<String> tables;

	private static Connection connection = null;

	public RDBMSDBObject(){
		setTables(new ArrayList<String>());
	}

	public Connection connect(){
		if (connection != null) {
			return connection;
		}
		else{
			try {
				Class.forName(driverClassName);
				connection = DriverManager.getConnection(jdbcURLPattern,username,password);
				updateTables();
			}
			catch (ClassNotFoundException e) {
				e.printStackTrace();
				return connection;
			} catch (SQLException e) {
				e.printStackTrace();
				return connection;
			}
			return connection;
		}
	}


	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getDriverClassName() {
		return driverClassName;
	}
	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}
	public String getJdbcURLPattern() {
		return jdbcURLPattern;
	}
	public void setJdbcURLPattern(String jdbcURLPattern) {
		this.jdbcURLPattern = jdbcURLPattern;
	}
	public String getDbName() {
		return dbName;
	}
	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	@Override
	public String toString() {
		return new Gson().toJson(this);
	}

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public List<String> getTables() {
		return tables;
	}

	public void setTables(List<String> tables) {
		this.tables = tables;
	}

	public void updateTables(){
		setTables(new ArrayList<String>());

		PreparedStatement statement;
		try {
			statement = connect().prepareStatement("show tables");

			ResultSet resultSet = statement.executeQuery();
			while(resultSet.next()){
				getTables().add(resultSet.getString(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String queryForChart(Query query) {
		PreparedStatement statement;
		try {
			statement = connect().prepareStatement(query.getQueryString());
			ResultSet resultSet = statement.executeQuery();
			List<Map<String, Object>> l = convertResultSetToList(resultSet);
			JSONSerializer s = new JSONSerializer();
			return s.serialize(l);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "&failure&";

		
	}
	
	public List<Map<String,Object>> convertResultSetToList(ResultSet rs) throws SQLException {
	    ResultSetMetaData md = rs.getMetaData();
	    int columns = md.getColumnCount();
	    List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();

	    while (rs.next()) {
	        HashMap<String,Object> row = new HashMap<String, Object>(columns);
	        for(int i=1; i<=columns; ++i) {
	            row.put(md.getColumnName(i),rs.getObject(i));
	        }
	        list.add(row);
	    }

	    return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String getColumns(String tableName) {
		JSONArray columns = new JSONArray();
		PreparedStatement statement;
		try {
			statement = connect().prepareStatement("desc " + tableName);

			ResultSet resultSet = statement.executeQuery();
			List<Map<String, Object>> columnsNames = convertResultSetToList(resultSet);
			for (Map<String, Object> column : columnsNames) {
				System.out.println(column);
				columns.add(column.get("COLUMN_NAME").toString());
			}
			System.out.println("columnType-----"+columns);
			return columns.toString();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "&failure&";
	}



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



	public String getUsername() {
		return username;
	}



	public void setUsername(String username) {
		this.username = username;
	}

}
