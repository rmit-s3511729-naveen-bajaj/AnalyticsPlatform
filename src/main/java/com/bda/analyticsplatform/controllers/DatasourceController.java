package com.bda.analyticsplatform.controllers;

import org.json.JSONArray;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bda.analyticsplatform.impl.DatasourceControllerImpl;
import com.bda.analyticsplatform.utils.ApplicationUtils;
import com.bda.analyticsplatform.utils.BDAException;

@RestController
@RequestMapping(value = "/api")
public class DatasourceController {
	@RequestMapping(value = "/datasources", method = RequestMethod.GET)
	public String getAlldatasources() {
		
		try{
			
			JSONArray dsDetails = new JSONArray(DatasourceControllerImpl.getAllDatasourceDetails());
			return ApplicationUtils.getSuccessObject(dsDetails.toString());
		}catch(Exception e){
			e.printStackTrace();
			return ApplicationUtils.getFailureObject(e.getMessage());
		}

	}
	
	@RequestMapping(value = "/datasources/{dsName}", method = RequestMethod.GET)
	public String getDatasource(@PathVariable String dsName) {
		return DatasourceControllerImpl.getDatasource(dsName);
	}
	
	@RequestMapping(value = "/saveRdbmsDS", method = RequestMethod.POST)
	public String saveRdbmsDS(@RequestBody String dsDetails) {
		
		try{
			DatasourceControllerImpl.saveNewDataSource(dsDetails);
			return ApplicationUtils.getSuccessObject(null);
		}catch(BDAException e){
			return ApplicationUtils.getFailureObject(e.getMessage());
		}catch(Exception e){
			e.printStackTrace();
			return ApplicationUtils.getFailureObject(e.getMessage());
		}

	}
	
	@RequestMapping(value = "/saveHiveDS", method = RequestMethod.POST)
	public String saveHiveDS(@RequestBody String dsDetails) {
		
		try{
			DatasourceControllerImpl.saveNewDataSource(dsDetails);
			return ApplicationUtils.getSuccessObject(null);
		}catch(BDAException e){
			return ApplicationUtils.getFailureObject(e.getMessage());
		}catch(Exception e){
			e.printStackTrace();
			return ApplicationUtils.getFailureObject(e.getMessage());
		}

	}
	
	@RequestMapping(value = "/saveNosqlDS", method = RequestMethod.POST)
	public String saveNosqlDS(@RequestBody String dsDetails) {
		
		try{
			DatasourceControllerImpl.saveNewDataSource(dsDetails);
			return ApplicationUtils.getSuccessObject(null);
		}catch(BDAException e){
			return ApplicationUtils.getFailureObject(e.getMessage());
		}catch(Exception e){
			e.printStackTrace();
			return ApplicationUtils.getFailureObject(e.getMessage());
		}

	}
	
	@RequestMapping(value = "/editRdbmsDS/{editDsName}", method = RequestMethod.POST)
	public String editRdbmsDS(@PathVariable String editDsName, @RequestBody String dsDetails) {
		
		try{
			DatasourceControllerImpl.editRDBMSDataSource(editDsName,dsDetails);
			return ApplicationUtils.getSuccessObject(null);
		}catch(BDAException e){
			return ApplicationUtils.getFailureObject(e.getMessage());
		}catch(Exception e){
			e.printStackTrace();
			return ApplicationUtils.getFailureObject(e.getMessage());
		}

	}
	
	@RequestMapping(value = "/editDistributedDS/{editDsName}", method = RequestMethod.POST)
	public String editHiveDS(@PathVariable String editDsName, @RequestBody String dsDetails) {
		
		try{
			DatasourceControllerImpl.editHiveDataSource(editDsName,dsDetails);
			return ApplicationUtils.getSuccessObject(null);
		}catch(BDAException e){
			return ApplicationUtils.getFailureObject(e.getMessage());
		}catch(Exception e){
			e.printStackTrace();
			return ApplicationUtils.getFailureObject(e.getMessage());
		}

	}
	

	@RequestMapping(value = "/editNosqlDS/{editDsName}", method = RequestMethod.POST)
	public String editNosqlDS(@PathVariable String editDsName, @RequestBody String dsDetails) {
		
		try{
			DatasourceControllerImpl.editNoSQLDataSource(editDsName,dsDetails);
			return ApplicationUtils.getSuccessObject(null);
		}catch(BDAException e){
			return ApplicationUtils.getFailureObject(e.getMessage());
		}catch(Exception e){
			e.printStackTrace();
			return ApplicationUtils.getFailureObject(e.getMessage());
		}

	}
	
	/*
	 * This method delete the datasource.
	 */

	@RequestMapping(value = "/deleteDataSource/{ds}", method = RequestMethod.GET)
	public String deleteDataSource(@PathVariable String ds) {
		
		try{
			DatasourceControllerImpl.deleteDataSource(ds);
			return ApplicationUtils.getSuccessObject(null);
		}catch(Exception e){
			e.printStackTrace();
			return ApplicationUtils.getFailureObject(e.getMessage());
		}

	}
	
	@RequestMapping(value = "/getTables/{datasourceName}", method = RequestMethod.GET)
	public String getDatasourceTables(@PathVariable String datasourceName) {
		return DatasourceControllerImpl.getDatasourceTables(datasourceName).toString();
	}

}
