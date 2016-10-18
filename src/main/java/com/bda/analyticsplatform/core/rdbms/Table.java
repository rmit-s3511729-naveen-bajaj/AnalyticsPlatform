package com.bda.analyticsplatform.core.rdbms;

import java.util.ArrayList;
import java.util.List;

import com.bda.analyticsplatform.utils.BDAConstants;

public class Table {
	private final String name;
	private final String alias;
	private List<String> columnsWithAlias = new ArrayList<String>();
	private List<String> groupFunctions = new ArrayList<String>();
	private List<String> orderFunctions = new ArrayList<String>();
	private List<String> whereConditions = new ArrayList<String>();
	private List<String> havingConditions = new ArrayList<String>();

	public Table(String name, String alias) {
		this.name = name;
		this.alias = alias;
	}

	public String getName() {
		return name;
	}

	public String getAlias() {
		return alias;
	}

	public void addColumnsToSelect(String column,String label) {
		columnsWithAlias.add(alias + "." + column + " AS " + label);
	}
	
	public void addColumnsToSelect(String column,String aggFn,String label) {
		if(aggFn.equalsIgnoreCase(BDAConstants.NONE))
			addColumnsToSelect(column,label);
		else
			columnsWithAlias.add(aggFn + "(" + alias + "." + column + ")" + " AS " + label);
	}
	
	
	public void addWhereConditions(String column,String operator,String value,String joinOperator) {
		whereConditions.add(column + operator + value + " " + joinOperator);
	}
	
	public void addHavingConditions(String column,String operator,String value,String joinOperator) {
		havingConditions.add(column + operator + value + " " + joinOperator);
	}
	
	public void addWhereConditions(String column,String operator,String value) {
		whereConditions.add(column + operator + value);
	}
	
	public void addHavingConditions(String column,String operator,String value) {
		havingConditions.add(column + operator + value);
	}

	public List<String> getColumnsWithAlias() {
		return columnsWithAlias;
	}

	public void addGroupFunctions(String groupFunction) {
		groupFunctions.add(groupFunction);
	}

	public List<String> getGroupFunctions() {
		return groupFunctions;
	}
	public void addOrderFunctions(String orderFunction) {
		orderFunctions.add(orderFunction);
	}

	public List<String> getOrderFunctions() {
		return orderFunctions;
	}

	
}
