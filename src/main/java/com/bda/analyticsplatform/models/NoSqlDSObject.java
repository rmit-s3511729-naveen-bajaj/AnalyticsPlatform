package com.bda.analyticsplatform.models;

import java.io.Serializable;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public abstract class NoSqlDSObject extends DSObject implements Serializable{

	
	private static final long serialVersionUID = -7682780482082652281L;
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
	
	public abstract boolean connect() throws UnknownHostException;
	public abstract void updateTables();
	public List<String> getTables() {
		return tables;
	}
	public void setTables(List<String> tables) {
		this.tables = tables;
	}
	public abstract void sum(String collectionName, String columnName);
	public abstract String getChartData(String CollectionName, ChartParams chartParams);
		
	

}
