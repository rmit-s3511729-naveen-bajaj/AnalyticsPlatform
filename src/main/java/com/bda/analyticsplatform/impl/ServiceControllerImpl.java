package com.bda.analyticsplatform.impl;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import com.bda.analyticsplatform.containers.DashBoardContainer;
import com.bda.analyticsplatform.containers.DataSourceContainer;
import com.bda.analyticsplatform.core.FileDataProcessor;
import com.bda.analyticsplatform.core.hive.HiveAdaptor;
import com.bda.analyticsplatform.core.nosql.MongoDBAdapter;
import com.bda.analyticsplatform.core.rdbms.queryGenerators.MySQLQueryGenerator;
import com.bda.analyticsplatform.models.Chart;
import com.bda.analyticsplatform.models.ChartParams;
import com.bda.analyticsplatform.models.Criteria;
import com.bda.analyticsplatform.models.DSObject;
import com.bda.analyticsplatform.models.DashBoard;
import com.bda.analyticsplatform.models.FileDSObject;
import com.bda.analyticsplatform.models.NoSqlDSObject;
import com.bda.analyticsplatform.models.Query;
import com.bda.analyticsplatform.models.RDBMSDBObject;
import com.bda.analyticsplatform.utils.ApplicationUtils;
import com.bda.analyticsplatform.utils.BDAConstants;
import com.bda.analyticsplatform.utils.BDAException;

public class ServiceControllerImpl {

	public static JSONObject getDashboardDetails(String dashboardName) throws BDAException, Exception {

		if (dashboardName.equals(BDAConstants.FIRST_DASH_INDICATOR)) {
			return DashBoardContainer.getFirstDashboardDetails();

		} else {
			return DashBoardContainer.getDashboardDetails(dashboardName);
		}
	}

	public static void saveNewDashboard(String dashboardName) throws BDAException, Exception {

		if (DashBoardContainer.findDashboard(dashboardName) != null) {
			throw new BDAException(BDAConstants.DASH_ALREADY_EXISTS + " :" + dashboardName);
		} else {
			DashBoardContainer.saveNewDashboard(dashboardName);
			DashBoardContainer.updateDashboardsToFile();
		}
	}

	public static void editDashboardDetails(String oldDash, String newDash) throws BDAException, Exception {

		if (DashBoardContainer.findDashboard(newDash) != null) {
			throw new BDAException(BDAConstants.DASH_ALREADY_EXISTS + " :" + newDash);
		} else {
			DashBoardContainer.findDashboard(oldDash).setDashboardName(newDash);
			DashBoardContainer.updateDashboardsToFile();
		}

	}

	public static void deleteDashboardDetails(String dashName) throws BDAException, Exception {

		DashBoardContainer.getDashboards().remove(DashBoardContainer.findDashboard(dashName));
		DashBoardContainer.updateDashboardsToFile();

	}

	public static void deleteDataSource(String ds) throws Exception {

		DataSourceContainer.getDsMetadata().remove(DataSourceContainer.findDS(ds));
		DataSourceContainer.updateDataSourcesToFile();

	}

	public static void saveNewDataSource(String dsDetails) throws BDAException, Exception {

		JSONObject dsDetailsJson = new JSONObject(dsDetails);

		if (DataSourceContainer.findDS(dsDetailsJson.getString("dsName")) != null) {
			throw new BDAException(BDAConstants.DS_ALREADY_EXISTS + " :" + dsDetailsJson.getString("dsName"));
		} else {

			if (dsDetailsJson.getString("dbType").equalsIgnoreCase("RDBMS")) {

				DataSourceContainer.saveRDBMSDataSource(dsDetailsJson, null);
			}
			else if (dsDetailsJson.getString("dbType").equalsIgnoreCase("NoSql")) {

				//DataSourceContainer.saveNoSqlDataSource(dsDetailsJson, null);

			}
			DataSourceContainer.updateDataSourcesToFile();
		}

	}

	public static String getDataSource(String ds) {
		DSObject obj = DataSourceContainer.findDS(ds);
		if (obj instanceof RDBMSDBObject) {
			((RDBMSDBObject) obj).updateTables();
		}
		else if(obj instanceof NoSqlDSObject) {

			((NoSqlDSObject) obj).updateTables();
		}
		//System.out.println("-------"+DataSourceContainer.findDS(ds).toString());
		return DataSourceContainer.findDS(ds).toString();

	}

	//	public static void editDataSource(String dsDetails) throws BDAException, Exception {
	//		JSONObject dsDetailsJson = new JSONObject(dsDetails);
	//		if (dsDetailsJson.getString("dbType").equalsIgnoreCase("RDBMS"))
	//			DataSourceContainer.editRDBMSDataSource(dsDetailsJson);
	//		else if (dsDetailsJson.getString("dbType").equalsIgnoreCase("NoSql"))
	//			DataSourceContainer.editNoSqlDataSource(dsDetailsJson);
	//		DataSourceContainer.updateDataSourcesToFile();
	//
	//	}

	public static void saveNewChart(String chartDetails) throws BDAException, Exception {
		JSONObject chartDetailsJson = new JSONObject(chartDetails);
		if (DataSourceContainer.findDS(chartDetailsJson.getString("chartName")) != null) {
			throw new BDAException(BDAConstants.DS_ALREADY_EXISTS + " :" + chartDetailsJson.getString("chartName"));
		} else {
			DataSourceContainer.saveChartDetails(chartDetailsJson, null);
			DataSourceContainer.updateDataSourcesToFile();
		}

	}

	public static String getChartObject(String chartJson) {
		System.out.println(chartJson);
		JSONObject chartObj = new JSONObject(chartJson);

		return DashBoardContainer.findDashboard(chartObj.getString("dashboardName"))
				.findChart(chartObj.getString("chartName")).toString();
	}

	public static void deleteChart(String chartDetails) throws BDAException, Exception {
		JSONObject chartDetailsJson = new JSONObject(chartDetails);
		DashBoard dashBoard = DashBoardContainer.findDashboard(chartDetailsJson.getString("dashboardName"));
		Chart chart = dashBoard.findChart(chartDetailsJson.getString("chartName"));

		dashBoard.getCharts().remove(chart);
		DashBoardContainer.updateDashboardsToFile();

	}

	public static void editChart(String chartDetails) throws BDAException, Exception {

		JSONObject chartDetailsJson = new JSONObject(chartDetails);
		DashBoard dashBoard = DashBoardContainer.findDashboard(chartDetailsJson.getString("dashboardName"));
		Chart chart = dashBoard.findChart(chartDetailsJson.getString("oldChartName"));

		Integer serialNo = chart.getSerialNo();
		//	dashBoard.getCharts().remove(chart);

		if (DataSourceContainer.findDS(chartDetailsJson.getString("chartName")) != null) {
			throw new BDAException(BDAConstants.DS_ALREADY_EXISTS + " :" + chartDetailsJson.getString("chartName"));
		} else {
			DataSourceContainer.editChartDetails(chartDetailsJson, serialNo, chart);
			DataSourceContainer.updateDataSourcesToFile();
		}

	}

	public static String queryForChart(String chartDetails) throws Exception {
		JSONObject chartDetailsJson = new JSONObject(chartDetails);
		System.out.println(DashBoardContainer.getDashboards());
		System.out.println("dsname---"+chartDetailsJson.getString("dsName"));
		DashBoard dashBoard = DashBoardContainer.findDashboard(chartDetailsJson.getString("dashboardName"));
		System.out.println("dash----"+dashBoard);
		System.out.println(chartDetailsJson);
		if(DashBoardContainer.findDashboard("dashboard1")
		.findChart(chartDetailsJson.getString("chartName")) == null){
			DashBoardContainer.saveChartDetails(chartDetailsJson, null);
		}
		
		Chart chart = dashBoard.findChart(chartDetailsJson.getString("chartName"));
//		Chart chart = new Chart();
//		chart.setChartName(chartDetailsJson.getString("chartName"));
		chart.setDatasourceName(chartDetailsJson.getString("dsName"));
		ChartParams chartParams = chart.getChartParams();
		if (chartDetailsJson.has("xAxis")) {
			for (int i = 0; i < chartDetailsJson.getJSONArray("tableNames").length(); i++) {

				chart.setTableColumns(new HashMap<String, Map<String,String>>());
				chart.getTableColumns().put(chartDetailsJson.getJSONArray("tableNames").getString(i),
						new LinkedHashMap<String,String>());
			}
			chartParams.setAggregateFn(chartDetailsJson.getString("aggregateFn"));
			chartParams.setNoOfRecords(chartDetailsJson.getString("noOfRecords"));
			chartParams.setOrderBy(chartDetailsJson.getString("orderBy"));
			chartParams.setQuery(chartDetailsJson.getString("query"));
			chartParams.setxAxis(chartDetailsJson.getString("xAxis"));
			chartParams.setxAxisLabel(chartDetailsJson.getString("xAxisLabel"));
			chartParams.setyAxis(chartDetailsJson.getString("yAxis"));
			chartParams.setyAxisLabel(chartDetailsJson.getString("yAxisLabel"));
			chartParams.setzAxis(chartDetailsJson.getString("zAxis"));
			chartParams.setzAxisLabel(chartDetailsJson.getString("zAxisLabel"));
			chartParams.setChartType(chartDetailsJson.getString("chartType"));
			//chartParams.setChartAxisType(chartDetailsJson.getString("chartAxisType"));
		}
		return getData(chart);
	}

	public static String getData(Chart chart) throws Exception {
		Query query = null;
		DSObject obj = DataSourceContainer.findDS(chart.getDatasourceName());
		ApplicationUtils.updateContainerObjects();
		if (obj instanceof RDBMSDBObject) {
			RDBMSDBObject ds = (RDBMSDBObject) obj;
			if (ds.getSubType().equalsIgnoreCase("mysql")) {
				query = new MySQLQueryGenerator().generateQuery(chart);
			}

			return ds.queryForChart(query);
		} else if (obj instanceof FileDSObject) {
			FileDSObject ds = (FileDSObject) obj;
			return FileDataProcessor.getChartData(chart.getChartParams(), ds);
		}
		else if(obj instanceof MongoDBAdapter){
			MongoDBAdapter ds = (MongoDBAdapter) obj;
			for(Entry<String,Map<String,String>> table : chart.getTableColumns().entrySet()){
				return ds.getChartData(table.getKey(),chart.getChartParams());
			}

		}else if(obj instanceof HiveAdaptor){
			HiveAdaptor ds = (HiveAdaptor) obj;
			if (ds.getSubType().equalsIgnoreCase("hive")) {
				query = new MySQLQueryGenerator().generateQuery(chart);
			}
			return ds.queryForChart(query);
		}

		return null;
	}

	public static void setChartDimensions(String chartDetails) throws Exception {
		JSONObject chartDetailsJson = new JSONObject(chartDetails);
		DashBoard dashBoard = DashBoardContainer.findDashboard(chartDetailsJson.getString("dashboardName"));
		Chart chart = dashBoard.findChart(chartDetailsJson.getString("chartName"));
		chart.setChartHeight(chartDetailsJson.getInt("height"));
		chart.setChartWidth(chartDetailsJson.getInt("width"));
		ApplicationUtils.updateContainerObjects();
	}

	public static void addChartToDashboard(String chartJson) throws Exception {
		JSONObject chartDetailsJson = new JSONObject(chartJson);
		DashBoard dashBoard = DashBoardContainer.findDashboard(chartDetailsJson.getString("dashboardName"));
		Chart chart = dashBoard.findChart(chartDetailsJson.getString("chartName"));
		if (chartDetailsJson.has("type") && chartDetailsJson.getString("type").equalsIgnoreCase("remove")) {
			chart.setDashboardIndicator(false);
		} else {
			chart.setDashboardIndicator(true);
		}
		ApplicationUtils.updateContainerObjects();
	}

	public static String getColumns(String dsName,String tableName) {

		//		String dsName = columnsDetails.split("&&")[0];
		//		String tableName = columnsDetails.split("&&")[1];
		//String dashName = columnsDetails.split("&&")[2];
		//String chartName = columnsDetails.split("&&")[3];
		return DataSourceContainer.findDS(dsName).getColumns(tableName);

	}

	public static void saveFileDataSource(String fileName, String dsName, MultipartFile fileLocation, String dbType, String delimiter,
			String dsType, String dsFileType) throws Exception {
		if (DataSourceContainer.findDS(dsName) != null) {
			throw new BDAException(BDAConstants.DS_ALREADY_EXISTS + " :" + dsName);
		} else {

			DataSourceContainer.saveFileDataSource(fileName,dsName, fileLocation, dbType, delimiter, dsType, dsFileType, null);
		}

	}

	public static void editFileDataSource(String fileName,String oldDSName, String dsName, MultipartFile fileLocation, String dbType,
			String delimiter, String dsType, String dsFileType) throws Exception {
		DataSourceContainer.editFileDataSource(fileName,oldDSName, dsName, fileLocation, dbType, delimiter, dsType, dsFileType);

	}

	public static String getChartDataWithUpdateFilter(String chartDetails) throws Exception {
		JSONObject chartDetailsJson = new JSONObject(chartDetails);
		DashBoard dashboard = DashBoardContainer.findDashboard(chartDetailsJson.getString("dashBoardName"));
		Chart chart = dashboard.findChart(chartDetailsJson.getString("chartName"));

		if(chartDetailsJson.has("userFilters")){
			String userFilters = chartDetailsJson.getString("userFilters");
			String[] criterias = userFilters.split(";");
			for(String crit : criterias){
				System.out.println("crit--"+crit);
				for(String opr : Criteria.operations){
					String[] criteriaString = crit.split(opr);
					if(criteriaString.length == 2){
						for(String fil : criteriaString[1].split(",")){
							Criteria filter = new Criteria(criteriaString[0],opr, fil);
							chart.getChartParams().getFilterConditions().add(filter);
						}
						break;
					}
				}

			}

		}
		else{

			String filterColumn = dashboard.findChart(chartDetailsJson.getString("filterChartName")).getChartParams()
					.getxAxis();

			Object filterValue = chartDetailsJson.get("filterValue");

			Criteria filter = new Criteria(filterColumn, Criteria.EQUALS, filterValue);

			chart.getChartParams().getFilterConditions().add(filter);

		}
		String data = getData(chart);
		chart.getChartParams().getFilterConditions().clear();
		return data;

	}

	public static void updateChartAxis(String chartJson) throws Exception {
		JSONObject chartDetailsJson = new JSONObject(chartJson);
		DashBoard dashboard = DashBoardContainer.findDashboard(chartDetailsJson.getString("dashBoardName"));
		Chart chart = dashboard.findChart(chartDetailsJson.getString("chartName"));
		chart.getChartParams().setChartAxisType(chartDetailsJson.getString("chartAxis"));
		ApplicationUtils.updateContainerObjects();
	}

	public static void updateChartType(String chartJson) throws Exception {
		JSONObject chartDetailsJson = new JSONObject(chartJson);
		DashBoard dashboard = DashBoardContainer.findDashboard(chartDetailsJson.getString("dashBoardName"));
		Chart chart = dashboard.findChart(chartDetailsJson.getString("chartName"));
		chart.getChartParams().setChartType(chartDetailsJson.getString("chartType"));
		ApplicationUtils.updateContainerObjects();

	}

	public static void editFileDataSource(String oldDSName, String dsName,
			String dbType, String delimiter, String dsType, String dsFileType) throws Exception {
		DataSourceContainer.editFileDataSource(oldDSName, dsName, dbType, delimiter, dsType, dsFileType);

		DataSourceContainer.updateDataSourcesToFile();
	}

}
