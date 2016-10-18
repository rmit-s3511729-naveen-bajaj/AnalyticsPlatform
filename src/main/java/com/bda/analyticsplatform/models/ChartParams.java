package com.bda.analyticsplatform.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

public class ChartParams implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1983018619861501085L;
	
	private String chartType;
	private String chartAxisType;
	private String query;
	private String xAxis;
	private String xAxisLabel;
	private String yAxis;
	private String yAxisLabel;
	private String y1Axis;
	private String y1AxisLabel;
	private String zAxis;
	private String zAxisLabel;
	public String getY1Axis() {
		return y1Axis;
	}

	public void setY1Axis(String y1Axis) {
		this.y1Axis = y1Axis;
	}

	public String getY1AxisLabel() {
		return y1AxisLabel;
	}

	public void setY1AxisLabel(String y1AxisLabel) {
		this.y1AxisLabel = y1AxisLabel;
	}

	private String aggregateFn;
	private String aggregateFnY1;
	private String noOfRecords;
	private String orderBy;
	
	private List<Criteria> filterConditions;
	
	public ChartParams(){
		filterConditions = new ArrayList<Criteria>();

	}

	public String getzAxis() {
		return zAxis;
	}

	public void setzAxis(String zAxis) {
		this.zAxis = zAxis;
	}

	public String getzAxisLabel() {
		return zAxisLabel;
	}

	public void setzAxisLabel(String zAxisLabel) {
		this.zAxisLabel = zAxisLabel;
	}

	public String getAggregateFn() {
		return aggregateFn;
	}

	public void setAggregateFn(String aggregateFn) {
		this.aggregateFn = aggregateFn;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public String getxAxis() {
		return xAxis;
	}

	public void setxAxis(String xAxis) {
		this.xAxis = xAxis;
	}

	public String getxAxisLabel() {
		return xAxisLabel;
	}

	public void setxAxisLabel(String xAxisLabel) {
		this.xAxisLabel = xAxisLabel;
	}

	public String getyAxis() {
		return yAxis;
	}

	public void setyAxis(String yAxis) {
		this.yAxis = yAxis;
	}

	public String getyAxisLabel() {
		return yAxisLabel;
	}

	public void setyAxisLabel(String yAxisLabel) {
		this.yAxisLabel = yAxisLabel;
	}

	public String getChartType() {
		return chartType;
	}

	public void setChartType(String chartType) {
		this.chartType = chartType;
	}

	public String getNoOfRecords() {
		return noOfRecords;
	}

	public void setNoOfRecords(String noOfRecords) {
		this.noOfRecords = noOfRecords;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
	@Override
	public String toString() {
		return new Gson().toJson(this);
	}

	public List<Criteria> getFilterConditions() {
		return filterConditions;
	}

	public void setFilterConditions(List<Criteria> filterConditions) {
		this.filterConditions = filterConditions;
	}

	public String getChartAxisType() {
		return chartAxisType;
	}

	public void setChartAxisType(String chartAxisType) {
		this.chartAxisType = chartAxisType;
	}

	public String getAggregateFnY1() {
		return aggregateFnY1;
	}

	public void setAggregateFnY1(String aggregateFnY1) {
		this.aggregateFnY1 = aggregateFnY1;
	}

}
