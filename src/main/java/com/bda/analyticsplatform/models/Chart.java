package com.bda.analyticsplatform.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

public class Chart implements Serializable{
	
	private static final long serialVersionUID = -6250749045636509057L;

	private Integer serialNo;
	
	private String chartName;
	
	private String datasourceName;
	
	private String tableName;
	
	//x-axis
	private List<String> dimensions; 
	//y-axis <name, agg fn>
	private List<Map<String,String>> expressions; 
	
	private String chartType;
	
	private String query;
	
	private Map<String,Map<String,String>> tableColumns ;
	
	private int chartHeight;
	
	private int chartWidth;
	
	private Boolean dashboardIndicator;
	
	private List<Criteria> filterConditions;
	private String noOfRecords;
	private String chartHeaderColor;
	
	public Chart(){
		dimensions = new ArrayList<>();
		expressions = new ArrayList<Map<String,String>>();
		filterConditions = new ArrayList<>();
		setTableColumns(new HashMap<String,Map<String,String>>());
		chartHeight = 400;
		chartWidth = 400;
		dashboardIndicator = false;
	}

	public int getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(int serialNo) {
		this.serialNo = serialNo;
	}

	public String getChartName() {
		return chartName;
	}

	public void setChartName(String chartName) {
		this.chartName = chartName;
	}
	

	public String getDatasourceName() {
		return datasourceName;
	}

	public void setDatasourceName(String datasourceName) {
		this.datasourceName = datasourceName;
	}

	public int getChartHeight() {
		return chartHeight;
	}

	public void setChartHeight(int chartHeight) {
		this.chartHeight = chartHeight;
	}

	public int getChartWidth() {
		return chartWidth;
	}

	public void setChartWidth(int chartWidth) {
		this.chartWidth = chartWidth;
	}
	
	@Override
	public String toString() {
		return new Gson().toJson(this);
	}

	public Boolean getDashboardIndicator() {
		return dashboardIndicator;
	}

	public void setDashboardIndicator(Boolean dashboardIndicator) {
		this.dashboardIndicator = dashboardIndicator;
	}

	public Map<String,Map<String,String>> getTableColumns() {
		
		return tableColumns;
	}

	public void setTableColumns(Map<String,Map<String,String>> tableColumns) {
		this.tableColumns = tableColumns;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public List<String> getDimensions() {
		return dimensions;
	}

	public void setDimensions(List<String> dimensions) {
		this.dimensions = dimensions;
	}

	public String getChartType() {
		return chartType;
	}

	public void setChartType(String chartType) {
		this.chartType = chartType;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public List<Map<String,String>> getExpressions() {
		return expressions;
	}

	public void setExpressions(List<Map<String,String>> expressions) {
		this.expressions = expressions;
	}

	public List<Criteria> getFilterConditions() {
		return filterConditions;
	}

	public void setFilterConditions(List<Criteria> filterConditions) {
		this.filterConditions = filterConditions;
	}

	public String getNoOfRecords() {
		return noOfRecords;
	}

	public void setNoOfRecords(String noOfRecords) {
		this.noOfRecords = noOfRecords;
	}

	public String getChartHeaderColor() {
		return chartHeaderColor;
	}

	public void setChartHeaderColor(String chartHeaderColor) {
		this.chartHeaderColor = chartHeaderColor;
	}

}
