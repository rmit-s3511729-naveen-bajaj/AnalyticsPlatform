package com.bda.analyticsplatform.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.google.gson.Gson;

public class DashBoard implements Serializable{

	private static final long serialVersionUID = -1519409971331008032L;
	private int serialNo;
	private String dashboardName;
	private List<Chart> charts;
	
	public DashBoard(){
		charts = new ArrayList<Chart>();
	}

	public int getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(int serialNo) {
		this.serialNo = serialNo;
	}

	public String getDashboardName() {
		return dashboardName;
	}

	public void setDashboardName(String dashboardName) {
		this.dashboardName = dashboardName;
	}

	public List<Chart> getCharts() {
		return charts;
	}

	public void setCharts(List<Chart> charts) {
		this.charts = charts;
	}
	
	
	public static void sortDashboards(List<DashBoard> dashList){
		Collections.sort(dashList, new Comparator<DashBoard>() {
			public int compare(DashBoard c1, DashBoard c2) {
				if (c1.getSerialNo() > c2.getSerialNo())
					return 1;
				if (c1.getSerialNo() < c2.getSerialNo())
					return -1;
				return 0;
			}
		});
	}
	
	@Override
	public String toString() {
		return new Gson().toJson(this);
	}

	public Chart findChart(String chartName) {
		for (Chart chart : charts) {
			if(chart.getChartName().equalsIgnoreCase(chartName)){
				return chart;
			}
		}
		return null;
	}
	
}


