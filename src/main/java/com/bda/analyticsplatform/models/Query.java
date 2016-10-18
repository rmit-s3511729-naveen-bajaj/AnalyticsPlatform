package com.bda.analyticsplatform.models;

public class Query {

	private String queryString;

	public Query(String string) {
		queryString=string;
		}
	public Query(){
		
	}

	public String getQueryString() {
		return queryString;
	}

	public void setQueryString(String queryString) {
		this.queryString = queryString;
	}
	
}
