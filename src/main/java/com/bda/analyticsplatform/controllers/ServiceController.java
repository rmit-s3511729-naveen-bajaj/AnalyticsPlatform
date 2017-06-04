package com.bda.analyticsplatform.controllers;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;

import org.json.JSONObject;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bda.analyticsplatform.impl.ServiceControllerImpl1;
import com.bda.analyticsplatform.impl.ServiceControllerImpl;
import com.bda.analyticsplatform.utils.ApplicationUtils;
import com.bda.analyticsplatform.utils.BDAConstants;
import com.bda.analyticsplatform.utils.BDAException;

@RestController
@RequestMapping(value = "/services")
public class ServiceController {
	@RequestMapping(value = "/saveRemoteTextMetadata", method = RequestMethod.POST)
	 public @ResponseBody String saveRemoteTextMetadata(
		@RequestParam("dsName") String dsName,
		@RequestParam("delimiter") String delimiter,
	   @RequestParam("dsFile") String dsFile,
	   @RequestParam("dsFileType") String dsFileType,
	   @RequestParam("remotefile") String remoteFile,
	   @RequestParam("hostname") String hostname

	   )
	   {
		try{
			
		ServiceControllerImpl1.getRemoteFileSchema(dsName,dsFile,dsFileType,delimiter,remoteFile,hostname);
		}catch(Exception e){
			e.printStackTrace();
			return BDAConstants.FAILURE_INTEGRATION;
		}
		return BDAConstants.SUCCESS_INTEGRATION;
	}
	
	@RequestMapping(value = "/saveLocalTextMetadata", method = RequestMethod.POST)
	 public @ResponseBody String saveLocalTextMetadata(
		@RequestParam("dsName") String dsName,
		@RequestParam("delimiter") String delimiter,
	   @RequestParam("dsFile") String dsFile,
	   @RequestParam("dsFileType") String dsFileType,
	   @RequestParam("remotefile") MultipartFile localFile
	   ) throws IOException
	   {
		String fileName = localFile.getOriginalFilename();
		
		  if (!localFile.isEmpty()) {

			    byte[] bytes = localFile.getBytes();
			    BufferedOutputStream stream = new BufferedOutputStream(
			      new FileOutputStream(new File(
			        BDAConstants.DATA_FILES_PATH+fileName)));
			    stream.write(bytes);
			    stream.close();

			   }
		  try{
		ServiceControllerImpl1.getLocalFileSchema(dsName,dsFile,dsFileType,delimiter,fileName);
		  }catch(Exception e){
				e.printStackTrace();
			  return BDAConstants.FAILURE_INTEGRATION;
			}
			return BDAConstants.SUCCESS_INTEGRATION;
	}
	
	/*
	 * This method saves the new Dashboard
	 */

	@RequestMapping(value = "/saveNewDashboard/{dashboardName}", method = RequestMethod.GET)
	public String saveNewDashboard(@PathVariable String dashboardName) {
		
		try{
			ServiceControllerImpl.saveNewDashboard(dashboardName);
			return ApplicationUtils.getSuccessObject(null);
		}catch(BDAException e){
			return ApplicationUtils.getFailureObject(e.getMessage());
		}catch(Exception e){
			e.printStackTrace();
			return ApplicationUtils.getFailureObject(e.getMessage());
		}

	}
	
	/*
	 * This method returns all the details to plot the UI for the first time.
	 * The input of this method is Dashboard  Name. If no dashboard name is present then it expects 
	 * string '&DASH_FIRST&' as input parameter . In this case it returns the details of the first dashboard.
	 */

	@RequestMapping(value = "/getDashboardDetails/{dashboardName}", method = RequestMethod.GET)
	public String getDashboardDetails(@PathVariable String dashboardName) {
		
		try{
			JSONObject dashDetails = ServiceControllerImpl.getDashboardDetails(dashboardName);
			return ApplicationUtils.getSuccessObject(dashDetails.toString());
		}catch(BDAException e){
			return ApplicationUtils.getFailureObject(e.getMessage());
		}catch(Exception e){
			e.printStackTrace();
			return ApplicationUtils.getFailureObject(e.getMessage());
		}

	}
	
	/*
	 * This method returns all the details to plot the UI for the first time.
	 * The input of this method is Dashboard  Name. If no dashboard name is present then it expects 
	 * string '&DASH_FIRST&' as input parameter . In this case it returns the details of the first dashboard.
	 */

	@RequestMapping(value = "/editDashboard", method = RequestMethod.POST)
	public String editDashboardDetails(@RequestBody String dashNames) {
		
		try{
			
			JSONObject dash = new JSONObject(dashNames);
			
			ServiceControllerImpl.editDashboardDetails(dash.getString("oldDash"),dash.getString("newDash"));
			return ApplicationUtils.getSuccessObject(null);
		}catch(BDAException e){
			return ApplicationUtils.getFailureObject(e.getMessage());
		}catch(Exception e){
			e.printStackTrace();
			return ApplicationUtils.getFailureObject(e.getMessage());
		}

	}

	/*
	 * This method delete the dashboard.
	 */

	@RequestMapping(value = "/deleteDashboard/{dashName}", method = RequestMethod.GET)
	public String deleteDashboard(@PathVariable String dashName) {
		
		try{
			ServiceControllerImpl.deleteDashboardDetails(dashName);
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
			ServiceControllerImpl.deleteDataSource(ds);
			return ApplicationUtils.getSuccessObject(null);
		}catch(Exception e){
			e.printStackTrace();
			return ApplicationUtils.getFailureObject(e.getMessage());
		}

	}
	
	/*
	 * This method saves the new Dashboard
	 */

	@RequestMapping(value = "/saveRdbmsDS", method = RequestMethod.POST)
	public String saveRdbmsDS(@RequestBody String dsDetails) {
		
		try{
			ServiceControllerImpl.saveNewDataSource(dsDetails);
			return ApplicationUtils.getSuccessObject(null);
		}catch(BDAException e){
			return ApplicationUtils.getFailureObject(e.getMessage());
		}catch(Exception e){
			e.printStackTrace();
			return ApplicationUtils.getFailureObject(e.getMessage());
		}

	}
	
	
	@RequestMapping(value = "/saveNoSqlMongoDBDS", method = RequestMethod.POST)
	public String saveNoSqlMongoDBDS(@RequestBody String dsDetails) {
		
		try{
			ServiceControllerImpl.saveNewDataSource(dsDetails);
			return ApplicationUtils.getSuccessObject(null);
		}catch(BDAException e){
			return ApplicationUtils.getFailureObject(e.getMessage());
		}catch(Exception e){
			e.printStackTrace();
			return ApplicationUtils.getFailureObject(e.getMessage());
		}

	}
	
//	@RequestMapping(value = "/editRdbmsDS", method = RequestMethod.POST)
//	public String editRdbmsDS(@RequestBody String dsDetails) {
//		
//		try{
//			ServiceControllerImpl.editDataSource(dsDetails);
//			return ApplicationUtils.getSuccessObject(null);
//		}catch(BDAException e){
//			return ApplicationUtils.getFailureObject(e.getMessage());
//		}catch(Exception e){
//			e.printStackTrace();
//			return ApplicationUtils.getFailureObject(e.getMessage());
//		}
//
//	}
//	
//	
//	@RequestMapping(value = "/editNoSqlMongoDBDS", method = RequestMethod.POST)
//	public String editNoSqlMongoDBDS(@RequestBody String dsDetails) {
//		
//		try{
//			ServiceControllerImpl.editDataSource(dsDetails);
//			return ApplicationUtils.getSuccessObject(null);
//		}catch(BDAException e){
//			return ApplicationUtils.getFailureObject(e.getMessage());
//		}catch(Exception e){
//			e.printStackTrace();
//			return ApplicationUtils.getFailureObject(e.getMessage());
//		}
//
//	}
	
	@RequestMapping(value = "/getDataSource/{ds}", method = RequestMethod.GET)
	public String getDataSource(@PathVariable String ds) {
		
		try{
			System.out.println("Controllerds-->"+ds);
			return ApplicationUtils.getSuccessObject(ServiceControllerImpl.getDataSource(ds));
		}catch(Exception e){
			e.printStackTrace();
			return ApplicationUtils.getFailureObject(e.getMessage());
		}

	}
	
	@RequestMapping(value = "/getChartObject/{chartJson}", method = RequestMethod.GET)
	public String getChartObject(@PathVariable String chartJson) {
		
		try{
			return ApplicationUtils.getSuccessObject(ServiceControllerImpl.getChartObject(chartJson));
		}catch(Exception e){
			e.printStackTrace();
			return ApplicationUtils.getFailureObject(e.getMessage());
		}

	}
	
	@RequestMapping(value = "/getColumns/{dsName}/{tableName}", method = RequestMethod.GET)
	public String getColumns(@PathVariable("dsName") String dsName,@PathVariable("tableName") String tableName) {
		
		try{
			return ApplicationUtils.getSuccessObject(ServiceControllerImpl.getColumns(dsName,tableName));
		}catch(Exception e){
			e.printStackTrace();
			return ApplicationUtils.getFailureObject(e.getMessage());
		}

	}
	
	/*
	 * This method saves the new Chart
	 */

	@RequestMapping(value = "/saveNewChart", method = RequestMethod.POST)
	public String saveNewChart(@RequestBody String chartDetails) {
		
		try{
			ServiceControllerImpl.saveNewChart(chartDetails);
			return ApplicationUtils.getSuccessObject(null);
		}catch(BDAException e){
			return ApplicationUtils.getFailureObject(e.getMessage());
		}catch(Exception e){
			e.printStackTrace();
			return ApplicationUtils.getFailureObject(e.getMessage());
		}

	}
	

	@RequestMapping(value = "/getChartDataWithUpdateFilter", method = RequestMethod.POST)
	public String getChartDataWithUpdateFilter(@RequestBody String chartDetails) {
		
		try{
			return ApplicationUtils.getSuccessObject(ServiceControllerImpl.getChartDataWithUpdateFilter(chartDetails));
		}catch(Exception e){
			e.printStackTrace();
			return ApplicationUtils.getFailureObject(e.getMessage());
		}

	}
	
	/*
	 * This method edits the chart
	 */

	@RequestMapping(value = "/editChartObject", method = RequestMethod.POST)
	public String editChartObject(@RequestBody String chartDetails) {
		
		try{
			ServiceControllerImpl.editChart(chartDetails);
			return ApplicationUtils.getSuccessObject(null);
		}catch(BDAException e){
			return ApplicationUtils.getFailureObject(e.getMessage());
		}catch(Exception e){
			e.printStackTrace();
			return ApplicationUtils.getFailureObject(e.getMessage());
		}

	}

	
	/*
	 * This method delete the Chart
	 */

	@RequestMapping(value = "/deleteChart/{chartDetails}", method = RequestMethod.GET)
	public String deleteChart(@PathVariable String chartDetails) {
		
		try{
			ServiceControllerImpl.deleteChart(chartDetails);
			return ApplicationUtils.getSuccessObject(null);
		}catch(Exception e){
			e.printStackTrace();
			return ApplicationUtils.getFailureObject(e.getMessage());
		}

	}
	
	/*
	 * This method edits the chart
	 */

	@RequestMapping(value = "/queryForChart", method = RequestMethod.POST)
	public String queryForChart(@RequestBody String chartDetails) {
		
		try{
			String response = ServiceControllerImpl.queryForChart(chartDetails);
			
			return ApplicationUtils.getSuccessObject(response);
		
		}catch(BDAException e){
			return ApplicationUtils.getFailureObject(e.getMessage());
		}catch(Exception e){
			e.printStackTrace();
			return ApplicationUtils.getFailureObject(e.getMessage());
		}

	}
	
	@RequestMapping(value = "/saveChart", method = RequestMethod.POST)
	public String saveChart(@RequestBody String chartDetails) {
		
		try{
			String response = ServiceControllerImpl.saveChart(chartDetails);
			
			return ApplicationUtils.getSuccessObject(response);
		
		}catch(BDAException e){
			return ApplicationUtils.getFailureObject(e.getMessage());
		}catch(Exception e){
			e.printStackTrace();
			return ApplicationUtils.getFailureObject(e.getMessage());
		}

	}
	
	@RequestMapping(value = "/updateChart/{oldChartName}", method = RequestMethod.POST)
	public String updateChart(@PathVariable String oldChartName, @RequestBody String chartDetails) {
		
		try{
			String response = ServiceControllerImpl.updateChart(oldChartName,chartDetails);
			
			return ApplicationUtils.getSuccessObject(response);
		
		}catch(BDAException e){
			return ApplicationUtils.getFailureObject(e.getMessage());
		}catch(Exception e){
			e.printStackTrace();
			return ApplicationUtils.getFailureObject(e.getMessage());
		}

	}
	
	/*
	 * This method edits the chart dimensions
	 */

	@RequestMapping(value = "/setChartDimensions", method = RequestMethod.POST)
	public String setChartDimensions(@RequestBody String chartDetails) {
		
		try{
			ServiceControllerImpl.setChartDimensions(chartDetails);
			
			return ApplicationUtils.getSuccessObject(null);
		
		}catch(BDAException e){
			return ApplicationUtils.getFailureObject(e.getMessage());
		}catch(Exception e){
			e.printStackTrace();
			return ApplicationUtils.getFailureObject(e.getMessage());
		}

	}
	
	
	
	
	@RequestMapping(value = "/addChartToDashboard/{chartJson}", method = RequestMethod.GET)
	public String addChartToDashboard(@PathVariable String chartJson) {
		
		try{
			ServiceControllerImpl.addChartToDashboard(chartJson);
			return ApplicationUtils.getSuccessObject(null);
		}catch(Exception e){
			e.printStackTrace();
			return ApplicationUtils.getFailureObject(e.getMessage());
		}

	}
	
	@RequestMapping(value = "/saveFileDS", method = RequestMethod.POST)
	 public @ResponseBody String saveFileDS(
		@RequestParam("fileName") String fileName,
		@RequestParam("dsName") String dsName,
		@RequestParam("dsType") String dsType,
		@RequestParam("dbType") String dbType,
		@RequestParam("delimiter") String delimiter,
	    @RequestParam("file") MultipartFile fileLocation,
	    @RequestParam("dsFileType") String dsFileType
	   )
	   {
		try{

		ServiceControllerImpl.saveFileDataSource(fileName,dsName,fileLocation,dbType,delimiter,dsType,dsFileType);
		return ApplicationUtils.getSuccessObject(null);
		}catch(BDAException e){
			return ApplicationUtils.getFailureObject(e.getMessage());
		}catch(Exception e){
			e.printStackTrace();
			return ApplicationUtils.getFailureObject(e.getMessage());
		}
	}
	
	@RequestMapping(value = "/editFileDSWithoutUpload", method = RequestMethod.POST)
	 public @ResponseBody String editFileDSWithoutUpload(@RequestBody String dsDetails)

	   {
		System.out.println("caallll");
		
		try{
			JSONObject dsObject = new JSONObject(dsDetails);
		ServiceControllerImpl.editFileDataSource(dsObject.getString("oldDSName"),dsObject.getString("dsName"),dsObject.getString("dbType"),dsObject.getString("delimiter"),dsObject.getString("dsType"),dsObject.getString("dsFileType"));
		return ApplicationUtils.getSuccessObject(null);
		}catch(Exception e){
			e.printStackTrace();
			return ApplicationUtils.getFailureObject(e.getMessage());
		}
	}

	
	
	@RequestMapping(value = "/editFileDS", method = RequestMethod.POST)
	 public @ResponseBody String editFileDS(
		@RequestParam("oldDSName") String oldDSName,
		@RequestParam("fileName") String fileName,
		@RequestParam("dsName") String dsName,
		@RequestParam("dsType") String dsType,
		@RequestParam("dbType") String dbType,
		@RequestParam("delimiter") String delimiter,
	    @RequestParam("file") MultipartFile fileLocation,
	    @RequestParam("dsFileType") String dsFileType
	   )
	   {
		try{
		ServiceControllerImpl.editFileDataSource(fileName,oldDSName,dsName,fileLocation,dbType,delimiter,dsType,dsFileType);
		return ApplicationUtils.getSuccessObject(null);
		}catch(BDAException e){
			return ApplicationUtils.getFailureObject(e.getMessage());
		}catch(Exception e){
			e.printStackTrace();
			return ApplicationUtils.getFailureObject(e.getMessage());
		}
	}

	@RequestMapping(value = "/updateChartAxis/{chartJson}", method = RequestMethod.GET)
	public String updateChartAxis(@PathVariable String chartJson) {
		
		try{
			ServiceControllerImpl.updateChartAxis(chartJson);
			return ApplicationUtils.getSuccessObject(null);
		}catch(Exception e){
			e.printStackTrace();
			return ApplicationUtils.getFailureObject(e.getMessage());
		}

	}
	@RequestMapping(value = "/updateChartType/{chartJson}", method = RequestMethod.GET)
	public String updateChartType(@PathVariable String chartJson) {
		
		try{
			ServiceControllerImpl.updateChartType(chartJson);
			return ApplicationUtils.getSuccessObject(null);
		}catch(Exception e){
			e.printStackTrace();
			return ApplicationUtils.getFailureObject(e.getMessage());
		}

	}

	
}





