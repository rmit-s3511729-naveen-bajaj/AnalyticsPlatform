package com.bda.analyticsplatform.containers;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.bda.analyticsplatform.core.nosql.MongoDBAdapter;
import com.bda.analyticsplatform.models.Chart;
import com.bda.analyticsplatform.models.DSObject;
import com.bda.analyticsplatform.models.DashBoard;
import com.bda.analyticsplatform.models.FileDSObject;
import com.bda.analyticsplatform.models.RDBMSDBObject;
import com.bda.analyticsplatform.utils.BDAConstants;
import com.bda.analyticsplatform.utils.BDAException;
import com.bda.analyticsplatform.utils.PropertiesFileReader;
import com.google.gson.Gson;

@SuppressWarnings("unchecked")
public class DashBoardContainer {

	private static List<DashBoard> dashboards = new ArrayList<DashBoard>();

	static {
		InputStream file = null;
		InputStream buffer = null;
		ObjectInput input = null;
		try {
			//file = DashBoardContainer.class.getClassLoader().getResourceAsStream(BDAConstants.DASHBOARDS_FILE_PATH);
			file = new FileInputStream(BDAConstants.DASHBOARDS_FILE_PATH);
			buffer = new BufferedInputStream(file);
			input = new ObjectInputStream(buffer);
			dashboards = (List<DashBoard>) input.readObject();
			
		} catch (Exception e) {
			e.printStackTrace();
			dashboards = new ArrayList<DashBoard>();
		} finally {
			try {
				if (file != null)
					file.close();
				if (input != null)
					input.close();
			} catch (IOException e) {
				e.printStackTrace();
				dashboards = new ArrayList<DashBoard>();
			}
		}
	}

	public static List<DashBoard> getDashboards() {
		return dashboards;
	}

	public static void setDashboards(List<DashBoard> dashboards) {
		DashBoardContainer.dashboards = dashboards;
	}

	public static JSONObject getFirstDashboardDetails() throws Exception, BDAException {

		JSONObject dashboardDetails = new JSONObject();

		if (dashboards.isEmpty()) {
			throw new BDAException(BDAConstants.NO_DASHBOARD_ERROR_MESSAGE);
		} else {
			List<DashBoard> dashList = new ArrayList<DashBoard>(dashboards);
			DashBoard.sortDashboards(dashList);
			dashboardDetails.put("dashDetails", new Gson().toJson(dashList.get(0)));
			List<String> dashNames = new ArrayList<String>();
			for (DashBoard dashBoard : dashList) {
				dashNames.add(dashBoard.getDashboardName());
			}
			dashboardDetails.put("dashList", dashNames);
			dashboardDetails.put("dsList", DataSourceContainer.getAllDSNames());
			PropertiesFileReader.loadProperties(BDAConstants.DB_CONFIG_PROP_FILE_PATH);
			dashboardDetails.put("supportedDB", PropertiesFileReader.getProperty("SUPPORTED_DB"));
			return dashboardDetails;
		}

	}

	public static JSONObject getDashboardDetails(String dashboardName) throws BDAException, Exception {

		JSONObject dashboardDetails = new JSONObject();

		if (dashboards.isEmpty()) {
			//throw new BDAException(BDAConstants.NO_DASHBOARD_ERROR_MESSAGE);
			saveNewDashboard("dashboard1");
			dashboardDetails.put("dashDetails", new Gson().toJson(findDashboard("dashboards")));
			return dashboardDetails; 
		} else {
			List<DashBoard> dashList = new ArrayList<DashBoard>(dashboards);
			
			DashBoard.sortDashboards(dashList);
			List<String> dashNames = new ArrayList<String>();
			for (DashBoard dashBoard : dashList) {
				if (dashBoard.getDashboardName().equals(dashboardName))
					dashboardDetails.put("dashDetails", new Gson().toJson(dashBoard));
				dashNames.add(dashBoard.getDashboardName());
			}
			if (!dashboardDetails.has("dashDetails")) {
				throw new BDAException(BDAConstants.NO_DASHBOARD_ERROR_MESSAGE);
			}
			dashboardDetails.put("dashList", dashNames);
			dashboardDetails.put("dsList", DataSourceContainer.getAllDSNames());
			PropertiesFileReader.loadProperties(BDAConstants.DB_CONFIG_PROP_FILE_PATH);
			dashboardDetails.put("supportedDB", PropertiesFileReader.getProperty("SUPPORTED_DB"));
			return dashboardDetails;
		}

	}

	public static List<String> getAllDashboardNames() {

		List<String> dashboardNames = new ArrayList<String>();
		for (DashBoard dashBoard : dashboards) {
			dashboardNames.add(dashBoard.getDashboardName());
		}
		return dashboardNames;
	}

	public static DashBoard findDashboard(String dashBoardName) {

		for (DashBoard dashBoard : dashboards) {
			if (dashBoard.getDashboardName().equalsIgnoreCase(dashBoardName)) {
				return dashBoard;
			}
		}
		return null;
	}

	public static void saveNewDashboard(String dashboardName) {

		DashBoard dashboard = new DashBoard();

		dashboard.setDashboardName(dashboardName);

		dashboard.setSerialNo(DashBoardContainer.getDashboards().size() + 1);

		dashboards.add(dashboard);

	}
	
	public static void saveChartDetails(JSONObject chartDetailsJson, Integer serialNo) throws Exception {

		DashBoard dashBoard = DashBoardContainer.findDashboard(chartDetailsJson.getString("dashboardName"));

		DSObject ds = DataSourceContainer.findDS(chartDetailsJson.getString("dsName"));
		System.out.println(ds.getType());
		Chart chart = new Chart();
		chart.setChartName(chartDetailsJson.getString("chartName"));
		if(serialNo == null)
			chart.setSerialNo(dashBoard.getCharts().size() + 1);
		else
			chart.setSerialNo(serialNo);
		if(ds.getType().equalsIgnoreCase("RDBMS")){
		
			RDBMSDBObject rdbmsdbObject = (RDBMSDBObject) ds;

		}
		else if(ds.getType().equalsIgnoreCase("FILE")){
			FileDSObject fileDSObject = (FileDSObject) ds;
		}
		else if(ds.getType().equalsIgnoreCase("NoSQL")){
			MongoDBAdapter mongoDSObject = (MongoDBAdapter) ds;
		}
		chart.setDatasourceName(chartDetailsJson.getString("dsName"));
		dashBoard.getCharts().add(chart);

		DashBoardContainer.updateDashboardsToFile();
	}


	public static void updateDashboardsToFile() throws Exception {
		
		//URL resourceUrl = DashBoardContainer.class.getClassLoader().getResource(BDAConstants.DASHBOARDS_FILE_PATH);
		//File file = new File(resourceUrl.toURI());
		OutputStream out = new FileOutputStream(BDAConstants.DASHBOARDS_FILE_PATH);
		
		System.out.println(out);
		ObjectOutputStream oos = new ObjectOutputStream(out);
		oos.writeObject(dashboards);
	}


}
