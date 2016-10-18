package com.bda.analyticsplatform.controllers;

import org.json.JSONArray;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bda.analyticsplatform.impl.ChartControllerImpl;
import com.bda.analyticsplatform.utils.ApplicationUtils;

@RestController
@RequestMapping(value = "/api")
public class ChartController {
	@RequestMapping(value = "/charts", method = RequestMethod.GET)
	public String getAllCharts() {
		
		try{
			
			JSONArray chartsDetails = new JSONArray(ChartControllerImpl.getAllChartDetails());
			return ApplicationUtils.getSuccessObject(chartsDetails.toString());
		}catch(Exception e){
			e.printStackTrace();
			return ApplicationUtils.getFailureObject(e.getMessage());
		}

	}
	
	@RequestMapping(value = "/getTablesData/{tableName}", method = RequestMethod.GET)
	public String getTableData(@PathVariable String tableName) {
		
		try{
			
			//JSONArray chartsDetails = new JSONArray(ChartControllerImpl.getTableData(tableName));
			return ApplicationUtils.getSuccessObject(ChartControllerImpl.getTableData(tableName));
		}catch(Exception e){
			e.printStackTrace();
			return ApplicationUtils.getFailureObject(e.getMessage());
		}

	}
	
	
}
