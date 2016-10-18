'use strict';
/**
 * @ngdoc function
 * @name bda.controller:DatasourceCtrl
 * @description
 * # DatasourceCtrl
 * Controller of the bda for datasource page
 */

angular.module('bdaApp')
.controller('DatasourceCtrl', function($scope, $state, $http, $cookieStore) {
	
	$scope.supportedDatasources = ["RDBMS","NoSQL","Distributed"];
	$scope.supportedDistributedDatasources = ["Hive"];
	$scope.supportedRDBMSDatasources = ["MySQL","Oracle"];
	$scope.supportedNoSQLDatasources = ["DynamoDB","MongoDB"];
	$scope.addDatasource = {};
	$scope.editDatasourceDetails = {};
	$scope.editDatasourceName = $cookieStore.get('editDatasourceName');
	
	if(angular.isDefined($scope.editDatasourceName)){
		$http.get('/AnalyticsPlatform/api/datasources/' + $scope.editDatasourceName)
		.success(function(response) {
			console.log(response);
			$scope.editDatasourceDetails=response;
		});
	}
	//all datasources
	$scope.allDatasources=[];
	$http.get('/AnalyticsPlatform/api/datasources/')
	.success(function(response) {
		console.log(response.output);
		$scope.allDatasources=JSON.parse(response.output);
	});
	
	

	$scope.addDatasourcePage = function(){
		$state.go('app.addDatasource', null, {reload: true});	
	}
	
	$scope.saveDatasource = function(){
		var saveDatasourceUrl = '';
		if($scope.addDatasource.type == "RDBMS"){
			saveDatasourceUrl = '/AnalyticsPlatform/api/saveRdbmsDS/'
		}
		else if($scope.addDatasource.type == "NoSQL"){
			saveDatasourceUrl = '/AnalyticsPlatform/api/saveNosqlDS/'
		}
		else if($scope.addDatasource.type == "Distributed"){
			saveDatasourceUrl = '/AnalyticsPlatform/api/saveHiveDS/'
		}
		$http.post(saveDatasourceUrl, $scope.addDatasource, {
			headers : {
				"content-type" : "application/json"
			}
		}).success(function(data) {
			
			$state.go('app.datasource', null, {reload: true});
			
		}).error(function(data) {
			$scope.error_message(data);

		})
	}
	
	
	$scope.editDatasources = function(editDsName){
		$cookieStore.put('editDatasourceName', editDsName);
		$state.go('app.editDatasource');	
	}
	
	$scope.updateDatasource = function(){
		var editDatasourceUrl = '';
		if($scope.editDatasourceDetails.type == "RDBMS"){
			editDatasourceUrl = '/AnalyticsPlatform/api/editRdbmsDS/' + $scope.editDatasourceName;
		}
		else if($scope.editDatasourceDetails.type == "NoSQL"){
			editDatasourceUrl = '/AnalyticsPlatform/api/editNosqlDS/' + $scope.editDatasourceName;
		}
		$http.post(editDatasourceUrl, $scope.editDatasourceDetails, {
			headers : {
				"content-type" : "application/json"
			}
		}).success(function(data) {
			
			$state.go('app.datasource', null, {reload: true});
			
		}).error(function(data) {
			//$scope.error_message(data);

		})
	}
	
	$scope.deleteDatasource = function(dsName){
		$http.get('/AnalyticsPlatform/api/deleteDataSource/'+ dsName, {
			headers : {
				"content-type" : "application/json"
			}
		}).success(function(data) {
			
			$state.go('app.datasource', null, {reload: true});
			
		}).error(function(data) {
			$scope.error_message(data);

		})
	}
	
	
})
	