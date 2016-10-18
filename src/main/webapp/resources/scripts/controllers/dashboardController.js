'use strict';
/**
 * @ngdoc function
 * @name bda.controller:DashboardCtrl
 * @description
 * # DashboardCtrl
 * Controller of the bda for dashboard page
 */

angular.module('bdaApp')
.controller('DashboardCtrl', function($scope, $state, $http, $cookieStore, $timeout) {


	var HEADER_COLORS=["#3498db","#ed4b11","#25c43d","#2417d6","#b512c4","#c21345"];
	
	$http
	.get(
			"/AnalyticsPlatform/services/getDashboardDetails/"
			+ "dashboard1")
			.success(
					function(response) {

						if (response.status == "&success&") {
							var output = JSON
							.parse((response.output));
							console.log(output);
							$scope.dashboards = output.dashList;

							$scope.active_dash_name = JSON
							.parse(output.dashDetails).dashboardName;

							console.log("dashname--"+$scope.active_dash_name);

//							if (dashBoardName == "&DASH_FIRST&")
//								location.href = location.href
//								+ "?"
//								+ $scope.active_dash_name;

							$scope.chartObjects = JSON
							.parse(output.dashDetails).charts;
							console.log($scope.chartObjects)
							var chartNames = [];
							var usedColors = [];

							for ( var chartjsonindex in $scope.chartObjects) {
								var chart = $scope.chartObjects[chartjsonindex];
									while(true){
										if(usedColors.length == HEADER_COLORS.length)	usedColors = [];
										var headerColor=HEADER_COLORS[Math.floor(Math.random() * (HEADER_COLORS.length - 0)) + 0];
										if(usedColors.indexOf(headerColor)<0){
											usedColors.push(headerColor);
											chart.chartHeaderColor = headerColor;
											break;
										}else{
											headerColor=HEADER_COLORS[Math.floor(Math.random() * (HEADER_COLORS.length - 0)) + 0];
										}
									}
								
								if (chart.dashboardIndicator) {
									$scope.chartObjects[chartjsonindex].chartName = chart.chartName;
									getNVD3Params(chart);

								}
								chartNames
								.push(chart.chartName);
							}
							$scope.charts = chartNames;
							$scope.dsnames = output.dsList;
							$scope.supportedDB = output.supportedDB;
						} else {
							$scope.dashboards = [];
							$scope.charts = [];
							$scope.chartObjects = [];
							$scope.dsnames = [];
						}

					});
	
	
	$scope.exportToExcel = function(chart){
		var chartName = chart.chartName;
		console.log('exportable_'+chartName);
		
//		alert("123");
//		 $scope.exportData = function () {
//		       alasql('SELECT * INTO XLSX("chart.xlsx",{headers:true}) FROM ?',[chart.tableData]);
//		    };
		alert('exportable_'+chartName)
		var blob = new Blob([document.getElementById('exportable_'+chartName).innerHTML], {
	        type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8"
	    });
	    saveAs(blob, chartName+".xls");
	};
	
	function getNVD3Params(chart) {
		var queryJson = {};
		console.log(chart);
		queryJson["dashboardName"] = "dashboard1";
		queryJson["dsName"] = chart.datasourceName;
		queryJson["chartName"] = chart.chartName;
		queryJson["query"] = chart.chartParams.query;
		var chartPromise = $timeout(function() {

			$http
			.post("/AnalyticsPlatform/services/queryForChart/", queryJson)
			.then(
					function(response) {
						$timeout.cancel(chartPromise);
						console.log(response.data);
						
						var chartOptions = getChartDetails(
								chart.chartParams.chartType,
								JSON.parse(response.data.output),
								chart.chartParams.xAxisLabel,
								chart.chartParams.yAxisLabel,
								chart.chartParams.zAxisLabel,chart.chartName);
						chart["options"] = chartOptions.options;
						chart["data"] = chartOptions.data;
						chart["tableData"] = JSON.parse(response.data.output);
						chart["length"] = chart["tableData"].length+1;
						chart["maxSize"] = 2;
						chart["numPerPage"] = 5;
						chart["currentPage"] = 1;
						chart["tablePageRecords"] = [];
						if(chart.chartParams.chartType != 'geo')
							applyDispatchFunction(chart["options"],chart);
					});
	    }, 20);
		

	}
	
	$scope.tableChart = function(chart){
		
		chart.tableIndicator = true;
	}
	
	
	$scope.backToChart = function(chart){
		
		chart.tableIndicator = false;
	}
	
	$scope.maximizeChart = function(chart){
		var chartName = chart.chartName;
		
		$("#dashcharts").find("[id='" + chartName + "']").find('.expandChart').toggleClass("fa-expand");
		$("#dashcharts").find("[id='" + chartName + "']").find('.expandChart').toggleClass("fa-compress");
		if($("#dashcharts").find("[id='" + chartName + "']").hasClass('maximizedDiv')){
			chart.maximizeIndicator = false;
			chart.tableIndicator = false;
			$("#dashcharts").find("[id='" + chartName + "']").removeClass('maximizedDiv');	
			$("#dashcharts").find("[id='" + chartName + "']").addClass('chartDiv');	
			$("#dashcharts").find("[id='" + chartName + "']").addClass("ui-resizable")
			$(window).trigger('resize');
			$('.overlayDiv').hide();
			$("[id='"+chart.chartName+"']").find('#geo_div').height(chart.chartHeight - 30);
			
		}else{
			chart.maximizeIndicator = true;
			$("#dashcharts").find("[id='" + chartName + "']").addClass('maximizedDiv');
			$("#dashcharts").find("[id='" + chartName + "']").removeClass('chartDiv');	
			$("#dashcharts").find("[id='" + chartName + "']").removeClass("ui-resizable")
			$(window).trigger('resize');
			$('.overlayDiv').show();
			$("[id='"+chart.chartName+"']").find('#geo_div').height('620px');


		}
		if(chart.chartParams.chartType == "geo"){
			$("[id='"+chart.chartName+"']").find('#geo_div').empty();
			
			getGeoOptions(chart.chartName,chart.chartParams.xAxisLabel,chart.chartParams.yAxisLabel,chart.chartParams.xAxis,chart.chartParams.yAxis,chart.tableData,chart.chartName);
			
			
		}
		window.dispatchEvent(new Event('resize'));
	}

});

function applyDispatchFunction(options,chart){
	
	if(options.chart.type == "pieChart"){
		options.chart["pie"] =  {
            dispatch: { 
                elementClick: function(e){ chartClickHandler(e,chart) }
            }
        }
		
	}else if(options.chart.type == "discreteBarChart"){
		
		options.chart["discretebar"] =  {
                dispatch: { 
                    elementClick: function(e){ chartClickHandler(e,chart); }
                }
            }								
	}
	
	return options;
	
}
function chartClickHandler(e,chart){
	
	var chartObjects = $scope.chartObjects;
	for(chartIndex in chartObjects){
		queryJson={};
		for(point in e.point){
			queryJson["filterValue"] = e.point[point];
			break;
		}
		queryJson["dashBoardName"] = "dashboard1";

		queryJson["filterChartName"] = chart.chartName;
		
		queryJson["chartName"] = chartObjects[chartIndex].chartName;
	
		if(chartObjects[chartIndex].datasourceName == chart.datasourceName)
			fireService(queryJson,chartIndex,chartObjects);
		
	}
	
}



function fireService(queryJson,chartIndex,chartObjects){
	$http
	.post("/AnalyticsPlatform/services/getChartDataWithUpdateFilter/", queryJson)
	.then(
			function(response) {
	
			var options = getChartDetails(chartObjects[chartIndex].chartParams.chartType,
					JSON.parse(response.data.output),chartObjects[chartIndex].chartParams.xAxisLabel,
					chartObjects[chartIndex].chartParams.yAxisLabel,chartObjects[chartIndex].chartParams.zAxisLabel,chartObjects[chartIndex].chartName);
			chartObjects[chartIndex].options = options.options;
			chartObjects[chartIndex].data = options.data;
			applyDispatchFunction(chartObjects[chartIndex].options,chartObjects[chartIndex]);
			
});
	
}
