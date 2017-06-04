'use strict';

/**
 * @ngdoc directive
 * @name izzyposWebApp.directive:adminPosHeader
 * @description
 * # adminPosHeader
 */
angular.module('futureApp')
	.directive('headerNotification',function(){
		return {
        templateUrl:'scripts/directives/header/header-notification/header-notification.html',
        restrict: 'E',
        replace: true,
        scope: true,
        controller: function($scope, $rootScope, $http, $timeout, $cookieStore, $state){
    		$scope.currentUser = $cookieStore.get('currentUser');  
    		$scope.appName = $cookieStore.get('applicationName');
			var defaultPage = $cookieStore.get('defaultPage');
    		if(angular.isUndefined($scope.appName)){
    			$scope.appName = "node-agent";
    			$cookieStore.put('applicationName', $scope.appName);
    		}
    		
    		//application list
    		$scope.appList = [];
    		$http.get('/api/app/list')
    		.success(function(response) {
    			$scope.appList = response;
    		});
    		
    		$scope.currentApp = function(selectedApp){
    			$scope.appName=selectedApp;
				$cookieStore.put('applicationName', $scope.appName);
				$timeout(function () {
					$state.go('app.devices', {}, {reload: true});
				});
			};
    		
    	}
    	}
    	
	});


