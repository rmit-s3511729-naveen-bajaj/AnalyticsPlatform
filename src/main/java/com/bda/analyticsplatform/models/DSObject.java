package com.bda.analyticsplatform.models;

import java.io.Serializable;

import com.google.gson.Gson;

public abstract class DSObject implements Serializable{

	private static final long serialVersionUID = -1713482902559313399L;

	private Integer serialNo;
	private String name;
	private String type;
	
	public DSObject(){
		
	}

	public DSObject(String dsType,String dataSourceName) {
		this.setType(dsType);
		this.setName(dataSourceName);
	}

	public int getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(int serialNo) {
		this.serialNo = serialNo;
	}

	@Override
	public String toString() {
		return new Gson().toJson(this);
	}

	public abstract String queryForChart(Query query);

	public abstract String getColumns(String tableName);

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}




}
