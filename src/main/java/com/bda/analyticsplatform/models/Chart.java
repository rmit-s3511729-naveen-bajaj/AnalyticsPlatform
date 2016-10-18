package com.bda.analyticsplatform.models;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

public class Chart implements Serializable{
	
	private static final long serialVersionUID = -6250749045636509057L;

	private Integer serialNo;
	
	private String chartName;
	
	private String datasourceName;
	
	private Map<String,Map<String,String>> tableColumns ;
	
	private int chartHeight;
	
	private int chartWidth;
	
	private Boolean dashboardIndicator;
	
	private ChartParams chartParams;
	
	public Chart(){
		chartParams = new ChartParams();
		setTableColumns(new HashMap<String,Map<String,String>>());
		chartHeight = 400;
		chartWidth = 40;
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

	public ChartParams getChartParams() {
		return chartParams;
	}

	public void setChartParams(ChartParams chartParams) {
		this.chartParams = chartParams;
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


}
