'use strict';
/**
 * @ngdoc function
 * @name sbAdminApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the sbAdminApp
 */
angular.module('bdaApp')
.controller('ChartCtrl', function($scope, $state, $http, $cookieStore, $timeout, $window) {

	$scope.editChartName = $cookieStore.get('editChartName');

	$scope.addChart={};
	$scope.editChart={};
	$scope.tabColObject = [];
	$scope.tableColumns = [];

	//all charts
	$scope.allCharts=[];
	$http.get('/AnalyticsPlatform/api/charts/')
	.success(function(response) {
		//console.log(response.output);
		$scope.allCharts=JSON.parse(response.output);
	});

	//all datasources
	$scope.allDatasources=[];
	$http.get('/AnalyticsPlatform/api/datasources/')
	.success(function(response) {
		//console.log(response.output);
		$scope.allDatasources=JSON.parse(response.output);
	});

	// call add chart page
	$scope.addChartPage = function(){
		$state.go('app.addChart', null, {reload: true});	
	}

	// call add chart page
	$scope.editChartPage = function(editChartName){
		$cookieStore.put('editChartName', editChartName);
		$state.go('app.editChart');	
	}

	if(angular.isDefined($scope.editChartName)){
		var queryJson = {}
		queryJson["dashboardName"]="dashboard1";
		queryJson["chartName"]=$scope.editChartName;

		var query = JSON.stringify(queryJson);
		$http.get("/AnalyticsPlatform/services/getChartObject/" +  query)
		.success(function(response) {
			//console.log(response);
			$scope.editChart = JSON.parse(response.output);
			$scope.updateTables($scope.editChart.datasourceName)
		});
	}

	// on select of datasource from drop down list
	$scope.updateTables = function(dsName){
		$http.get("/AnalyticsPlatform/api/getTables/" +  dsName)
		.success(function(response) {
			console.log(response);
			for(var i in response){
				$scope.tableColumns[i] = {};
				$scope.tableColumns[i].title = response[i];
				$scope.tableColumns[i].columns = [];
			}

		});

	}
	$scope.editGetColumns = function(tableName){
		$http.get("/AnalyticsPlatform/services/getColumns/" + $scope.editChart.datasourceName + "/" + tableName )
		.success(function(response) {
			console.log(response.output)
			for(var i in $scope.tableColumns){
				if($scope.tableColumns[i].title == tableName){
					$scope.tableColumns[i].columns = JSON.parse(response.output);
				}
			}
//			$(".leftchartpanel").niceScroll();
		}
		);
	}
	// on select of table -- get column names
	$scope.getColumns = function(tableName){
		$http.get("/AnalyticsPlatform/services/getColumns/" + $scope.addChart.dsName + "/" + tableName )
		.success(function(response) {
			console.log(response.output)
			for(var i in $scope.tableColumns){
				if($scope.tableColumns[i].title == tableName){
					$scope.tableColumns[i].columns = JSON.parse(response.output);
				}
			}
//			$(".leftchartpanel").niceScroll();
		}
		);
	}
//	$scope.tabColObject = {};
	$scope.tableData = {};
	$scope.dimensions = [];
	$scope.expressions = [];
	$scope.xAxisValue = "";
	$scope.yAxisValue = "";
	$scope.zAxisValue = "";
	$scope.aggregateMethods = ["sum","avg","min","max","count","none"];
	$scope.aggregate = [];
	$scope.usedTables = [];
	$scope.noOfRecords = "24";
	$scope.orderBy = "x-axis";



	$scope.onDragStart = function(item){
		console.log(item);
	}

	$scope.onYDropComplete=function(data,evt){
		var json = {"expField":data,"aggregate":"sum"}
		$scope.expressions.push(json);
	}

	$scope.onXDropComplete=function(data,evt){
		$scope.dimensions.push(data);
	}

	function replaceAll(find, replace, str) {
		return str.replace(new RegExp(find, 'g'), replace);
	}

	$scope.plotChart = function(){
		$scope.xAxisLabel = $("[id='"+$scope.dimensions[0]+"']").val();
		$scope.yAxisLabel = $("[id='"+$scope.expressions[0].expField+"']").val();
		$scope.zAxisLabel = $("[id='"+$scope.dimensions[1]+"']").val();
		$scope.chartType = $('.chartTypesImage').find('.active').attr('id');

		var postJson = {};
		postJson["dashboardName"] = "dashboard1";
		postJson["dsName"] = $scope.addChart.dsName;
		postJson["chartName"] = $scope.addChart.name;
		postJson["query"] = checkValue($('#querypane').val());
		postJson["xAxis"] = checkValue($scope.dimensions[0]);
		postJson["xAxisLabel"] = checkValue($("[id='"+$scope.dimensions[0]+"']").val());
		postJson["yAxis"] = checkValue($scope.expressions[0].expField);
		postJson["yAxisLabel"] = checkValue($("[id='"+$scope.expressions[0].expField+"']").val());
		postJson["zAxis"] = checkValue($scope.dimensions[1]);
		postJson["zAxisLabel"] = checkValue($("[id='"+$scope.dimensions[1]+"']").val());
		postJson["aggregateFn"] = checkValue($scope.expressions[0].aggregate);
		postJson["chartType"] = $('.chartTypesImage').find('.active').attr('id');
		postJson["chartAxisType"] = $('.chartImage').find('.active').attr('id');
		postJson["noOfRecords"] = checkValue($scope.noOfRecords);
		var orderColumn = $scope.orderBy;
		if(orderColumn == "x-axis")
			postJson["orderBy"] = checkValue($scope.xAxisValue);
		else if(orderColumn == "y-axis")
			postJson["orderBy"] = checkValue($scope.yAxisValue);
		else
			postJson["orderBy"] = orderColumn;

		postJson["tableNames"] = checkValue($scope.usedTables);

		console.log(postJson);
		var chartPromise = $timeout(function() {
			$http.post("/AnalyticsPlatform/services/queryForChart/",postJson).
			then(function(response) {
				response = response.data;
				console.log(response);
				if(response.status == "&success&"){
					$scope.errorIndicator = "false";
					$scope.tableData = JSON.parse(response.output);
					console.log($scope.tableData);
					$scope.typesSelected($('.chartTypesImage tr td .active').attr('id'));
				}else{
					$scope.errorIndicator = "true";
					$scope.error_Message = response.output;
				}
			}, function(response) {
				alert("Unexpected Error");
			});
		}, 50);
		window.dispatchEvent(new Event('resize'));
		$scope.stats = "chart";
		window.dispatchEvent(new Event('resize'));
	}

	$scope.editPlotChart = function(){
		$scope.xAxisLabel = $("[id='"+$scope.dimensions[0]+"']").val();
		$scope.yAxisLabel = $("[id='"+$scope.expressions[0].expField+"']").val();
		$scope.zAxisLabel = $("[id='"+$scope.dimensions[1]+"']").val();
		$scope.chartType = $('.chartTypesImage').find('.active').attr('id');

		var postJson = {};
		postJson["dashboardName"] = "dashboard1";
		postJson["dsName"] = $scope.editChart.datasourceName;
		postJson["chartName"] = $scope.editChart.chartName;
		postJson["query"] = checkValue($('#querypane').val());
		postJson["xAxis"] = checkValue($scope.dimensions[0]);
		postJson["xAxisLabel"] = checkValue($("[id='"+$scope.dimensions[0]+"']").val());
		postJson["yAxis"] = checkValue($scope.expressions[0].expField);
		postJson["yAxisLabel"] = checkValue($("[id='"+$scope.expressions[0].expField+"']").val());
		postJson["zAxis"] = checkValue($scope.dimensions[1]);
		postJson["zAxisLabel"] = checkValue($("[id='"+$scope.dimensions[1]+"']").val());
		postJson["aggregateFn"] = checkValue($scope.expressions[0].aggregate);
		postJson["chartType"] = $('.chartTypesImage').find('.active').attr('id');
		postJson["chartAxisType"] = $('.chartImage').find('.active').attr('id');
		postJson["noOfRecords"] = checkValue($scope.noOfRecords);
		var orderColumn = $scope.orderBy;
		if(orderColumn == "x-axis")
			postJson["orderBy"] = checkValue($scope.xAxisValue);
		else if(orderColumn == "y-axis")
			postJson["orderBy"] = checkValue($scope.yAxisValue);
		else
			postJson["orderBy"] = orderColumn;

		postJson["tableNames"] = checkValue($scope.usedTables);

		console.log(postJson)
		var chartPromise = $timeout(function() {
			$http.post("/AnalyticsPlatform/services/queryForChart/",postJson).
			then(function(response) {
				response = response.data;
				console.log(response);
				if(response.status == "&success&"){
					$scope.errorIndicator = "false";
					$scope.tableData = JSON.parse(response.output);
					console.log($scope.tableData);
					$scope.typesSelected($('.chartTypesImage tr td .active').attr('id'));
				}else{
					$scope.errorIndicator = "true";
					$scope.error_Message = response.output;
				}
			}, function(response) {
				alert("Unexpected Error");
			});
		}, 50);
		window.dispatchEvent(new Event('resize'));
		$scope.stats = "chart";
		window.dispatchEvent(new Event('resize'));
	}

	$scope.addToDashboard = function(){

		var queryJson = {};
		queryJson["dashboardName"]="dashboard1";
		queryJson["chartName"]=$scope.addChart.name;

		var query = JSON.stringify(queryJson);
		$http.get("/AnalyticsPlatform/services/addChartToDashboard/" +  query)
		.success(function(response) {
			$state.go('app.dashboard', null, {reload: true});
		});


	}

	$scope.editAddToDashboard = function(){

		var queryJson = {};
		queryJson["dashboardName"]="dashboard1";
		queryJson["chartName"]=$scope.editChart.chartName;

		var query = JSON.stringify(queryJson);
		$http.get("/AnalyticsPlatform/services/addChartToDashboard/" +  query)
		.success(function(response) {
			$state.go('app.dashboard', null, {reload: true});
		});


	}


	$scope.typesSelected = function(elementId){
		$scope.chartType = elementId;

		$('.chartTypesImage tr td .active').removeClass('active');
		$('#' + elementId).addClass('active');
		var chartOptions = getChartDetails(elementId,$scope.tableData,$scope.xAxisLabel,
				$scope.yAxisLabel,$scope.zAxisLabel,"geo_div");

		$scope.options = chartOptions.options;
		$scope.data = chartOptions.data;
		$timeout(function () {
		    window.dispatchEvent(new Event('resize'));
		}, 0);
		//window.dispatchEvent(new Event('resize'));
	}

	$scope.getdragData = function(table,column){
		$scope.usedTables=[];
		$scope.usedTables.push(table);
		return column;
	}

	$scope.removeDimension = function(value){
		$scope.dimensions.splice($scope.dimensions.indexOf(value),1);
	}
	$scope.removeExpression = function(value){
		$scope.expressions.splice($scope.expressions.indexOf(value),1);
	}

$scope.deleteChart = function(chartName){
	
	var queryJson = {};
	queryJson["dashboardName"]="dashboard1";
	queryJson["chartName"]=chartName;

	query = JSON.stringify(queryJson);
	$http.get('/AnalyticsPlatform/services/deleteChart/' + query)
	.success(function(response) {
		//$scope.allCharts=JSON.parse(response.output);
		if(response.status == "&success&"){
			$state.go('app.chart', null, {reload: true});
		}else{
			$scope.error_Message = response.output;
			$('.errorMessage').show();
		}
	});
}

});

function checkValue(value){
	if(value == null || (typeof value == "string" && value.trim().length == 0) )
		return "&null&";
	else
		return value;

}

