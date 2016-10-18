package com.bda.analyticsplatform.impl;

import org.json.JSONException;

import com.bda.analyticsplatform.containers.DashBoardContainer;
import com.bda.analyticsplatform.core.nosql.DynamoDBAdapter;
import com.bda.analyticsplatform.utils.BDAException;

public class ChartControllerImpl {

	public static String getAllChartDetails() throws JSONException, BDAException, Exception {
		System.out.println(DashBoardContainer.getDashboardDetails("dashboard1"));
		return DashBoardContainer.findDashboard("dashboard1").getCharts().toString();
	}

	public static String getTableData(String tableName) {
		return DynamoDBAdapter.getTableData(tableName);
	}

}
