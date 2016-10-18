'use strict';
/**
 * @ngdoc function
 * @name bda.controller:DashboardCtrl
 * @description
 * # DashboardCtrl
 * Controller of the bda for dashboard page
 */

angular.module('bdaApp')
.controller('DynamoDBCtrl', function($scope, $filter, $state, NgTableParams, $http, $cookieStore, $timeout) {
	
	//all datasources
	$scope.allDatasources=[];
	$http.get('/AnalyticsPlatform/api/datasources/')
	.success(function(response) {
		console.log(response);
		$scope.allDatasources=JSON.parse(response.output);
	});
	
	$scope.allTables=[];
	$scope.updateDyanmodbTables = function(dsName){
		$http.get("/AnalyticsPlatform/api/getTables/" +  dsName)
		.success(function(response) {
			console.log(response);
			$scope.allTables = response;
		});
	}
	
	//var data = []
	$scope.updateDyanmodbTablesData = function(tableName){
		$http.get("/AnalyticsPlatform/api/getTablesData/" +  tableName)
		.success(function(response) {
			//console.log(response.output);
			//$scope.allTables = response;
			var data = JSON.parse(response.output);
			$scope.tableParams = new NgTableParams({
		        page: 1, // show first page
		        count: 10, // count per page
		        sorting: {
		            name: 'asc' // initial sorting
		        },
		        filter: {
		            name: '' // initial filter
		        }
		    }, {
		        total: data.length, // length of data
		        getData: function ($defer, params) {
		           

		            // use build-in angular filter
		            var orderedData = params.sorting() ? $filter('orderBy')(data, params.orderBy()) : data;

		            //orderedData = $filter('filter')(orderedData, params.filter()) : orderedData;

		            $defer.resolve(orderedData.slice((params.page() - 1) * params.count(), params.page() * params.count()));
		        }
		    });
//			angular.forEach(tableData, function(item){
//                console.log(item); 
//                
//            })
			
		});
	}

	
	//var self = this;
//	var data = [{name: "Moroni1", age: 50},{name: "Moroni2", age: 50},{name: "Moroni3", age: 50},{name: "Moroni4", age: 50},{name: "Moroni5", age: 50}
//	,{name: "Moroni2", age: 50}
//	,{name: "Moroni2", age: 50}
//	,{name: "Moroni2", age: 50}
//	,{name: "Moroni2", age: 50}
//	,{name: "Moroni2", age: 50}
//	,{name: "Moroni2", age: 50}
//	,{name: "Moroni2", age: 50}
//	,{name: "Moroni2", age: 50}
//	,{name: "Moroni2", age: 50}
//	,{name: "Moroni2", age: 50}
//	,{name: "Moroni2", age: 50}
//	,{name: "Moroni2", age: 50}
//	,{name: "Moroni2", age: 50}
//	,{name: "Moroni2", age: 50}
//	,{name: "Moroni2", age: 50}
//	,{name: "Moroni2", age: 50}];
//	$scope.tableParams = new NgTableParams({}, { dataset: data});
//	

})
