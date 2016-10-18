package com.bda.analyticsplatform.containers;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.bda.analyticsplatform.models.Chart;
import com.bda.analyticsplatform.models.DashBoard;
import com.bda.analyticsplatform.utils.BDAConstants;
import com.bda.analyticsplatform.utils.BDAException;
import com.bda.analyticsplatform.utils.PropertiesFileReader;
import com.google.gson.Gson;

@SuppressWarnings("unchecked")
public class ChartContainer {

		private static List<Chart> charts = new ArrayList<Chart>();

		static {
			InputStream file = null;
			InputStream buffer = null;
			ObjectInput input = null;
			try {
				file = ChartContainer.class.getClassLoader().getResourceAsStream(BDAConstants.CHARTS_FILE_PATH);
				buffer = new BufferedInputStream(file);
				input = new ObjectInputStream(buffer);
				charts = (List<Chart>) input.readObject();
				
			} catch (Exception e) {
				e.printStackTrace();
				charts = new ArrayList<Chart>();
			} finally {
				try {
					if (file != null)
						file.close();
					if (input != null)
						input.close();
				} catch (IOException e) {
					e.printStackTrace();
					charts = new ArrayList<Chart>();
				}
			}
		}

		public static List<Chart> getCharts() {
			return charts;
		}

		public static void setCharts(List<Chart> charts) {
			ChartContainer.charts = charts;
		}

//		public static JSONObject getFirstDashboardDetails() throws Exception, BDAException {
//
//			JSONObject dashboardDetails = new JSONObject();
//
//			if (dashboards.isEmpty()) {
//				throw new BDAException(BDAConstants.NO_DASHBOARD_ERROR_MESSAGE);
//			} else {
//				List<DashBoard> dashList = new ArrayList<DashBoard>(dashboards);
//				DashBoard.sortDashboards(dashList);
//				dashboardDetails.put("dashDetails", new Gson().toJson(dashList.get(0)));
//				List<String> dashNames = new ArrayList<String>();
//				for (DashBoard dashBoard : dashList) {
//					dashNames.add(dashBoard.getDashboardName());
//				}
//				dashboardDetails.put("dashList", dashNames);
//				dashboardDetails.put("dsList", DataSourceContainer.getAllDSNames());
//				PropertiesFileReader.loadProperties(BDAConstants.DB_CONFIG_PROP_FILE_PATH);
//				dashboardDetails.put("supportedDB", PropertiesFileReader.getProperty("SUPPORTED_DB"));
//				return dashboardDetails;
//			}
//
//		}

//		public static JSONObject getChartDetails(String chartName) throws BDAException, Exception {
//
//			JSONObject dashboardDetails = new JSONObject();
//
//			if (charts.isEmpty()) {
//				throw new BDAException(BDAConstants.NO_CHART_ERROR_MESSAGE);
//			} else {
//				List<Chart> chartList = new ArrayList<Chart>(charts);
//				
//				
//			}
//
//		}


		public static Chart findChart(String chartName) {

			for (Chart chart : charts) {
				if (chart.getChartName().equalsIgnoreCase(chartName)) {
					return chart;
				}
			}
			return null;
		}

//		public static void saveNewDashboard(String dashboardName) {
//
//			DashBoard dashboard = new DashBoard();
//
//			dashboard.setDashboardName(dashboardName);
//
//			dashboard.setSerialNo(DashBoardContainer.getDashboards().size() + 1);
//
//			dashboards.add(dashboard);
//
//		}

//		public static void updateDashboardsToFile() throws Exception {
//			
//			URL resourceUrl = DashBoardContainer.class.getClassLoader().getResource(BDAConstants.DASHBOARDS_FILE_PATH);
//			File file = new File(resourceUrl.toURI());
//			OutputStream out = new FileOutputStream(file);
//			
//			System.out.println(out);
//			ObjectOutputStream oos = new ObjectOutputStream(out);
//			oos.writeObject(dashboards);
//		}

}
