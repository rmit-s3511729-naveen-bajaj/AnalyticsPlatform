'use strict';
/**
 * @ngdoc overview
 * @name sbAdminApp
 * @description
 * # sbAdminApp
 *
 * Main module of the application.
 */
angular
.module('bdaApp', [
                   'oc.lazyLoad',
                   'ui.router',
                   'ui.bootstrap',
                   'angular-loading-bar',
                   'smart-table',
                   'ngCookies',
                   'ngDraggable',
                   'nvd3'
                   ])
                   .config(['$stateProvider','$urlRouterProvider','$ocLazyLoadProvider',function ($stateProvider,$urlRouterProvider,$ocLazyLoadProvider) {

                	   $ocLazyLoadProvider.config({
                		   debug:false,
                		   events:true,
                	   });

                	   $urlRouterProvider.otherwise('/app/home');

                	   $stateProvider
                	   .state('app', {
                		   url:'/app',
                		   templateUrl: 'resources/views/dashboard/main.html',
                		   resolve: {
                			   loadMyDirectives:function($ocLazyLoad){
                				   return $ocLazyLoad.load(
                						   {
                							   name:'bdaApp',
                							   files:[
                							          'resources/scripts/directives/header/header.js',
                							          'resources/scripts/directives/sidebar/sidebar.js',
                							          ]
                						   }),
                						   $ocLazyLoad.load(
                								   {
                									   name:'toggle-switch',
                									   files:["resources/bower_components/angular-toggle-switch/angular-toggle-switch.min.js",
                									          "resources/bower_components/angular-toggle-switch/angular-toggle-switch.css"
                									          ]
                								   }),
                								   $ocLazyLoad.load(
                										   {
                											   name:'ngAnimate',
                											   files:['resources/bower_components/angular-animate/angular-animate.js']
                										   })
                										   $ocLazyLoad.load(
                												   {
                													   name:'ngCookies',
                													   files:['resources/bower_components/angular-cookies/angular-cookies.js']
                												   })
                												   $ocLazyLoad.load(
                														   {
                															   name:'ngResource',
                															   files:['resources/bower_components/angular-resource/angular-resource.js']
                														   })
                														   $ocLazyLoad.load(
                																   {
                																	   name:'ngSanitize',
                																	   files:['resources/bower_components/angular-sanitize/angular-sanitize.js']
                																   })
                																   $ocLazyLoad.load(
                																		   {
                																			   name:'ngTouch',
                																			   files:['resources/bower_components/angular-touch/angular-touch.js']
                																		   })
                			   }
                		   }
                	   })
                	   .state('app.dashboard',{
                		   url:'/home',
                		   controller: 'DashboardCtrl',
                		   templateUrl:'resources/views/dashboard/home.html',
                		   resolve: {
                			   loadMyFiles:function($ocLazyLoad) {
                				   return $ocLazyLoad.load({
                					   name: 'bdaApp',
                					   files:[
                					          'resources/scripts/controllers/dashboardController.js',
                					          'resources/scripts/directives/dashboard/stats/stats.js',
                					          'resources/scripts/chartOptions.js',
                					          'resources/scripts/directives/dashboard/divResizeDirective.js',
                					          'resources/scripts/directives/dashboard/dirPagination.js'
                					          ]
                				   })
                			   }
                		   }
                	   }).state('app.datasource',{
                		   templateUrl:'resources/views/datasources/home.html',
                		   url:'/datasources',
                		   controller:'DatasourceCtrl',
                		   resolve: {
                			   loadMyFiles: function ($ocLazyLoad) {
                				   return $ocLazyLoad.load({
                					   name: 'bdaApp',
                					   files: [
                					           	'resources/scripts/controllers/datasourceController.js',
                					           	'resources/scripts/directives/pageSelect.js'

                					          ]
                				   })
                			   }
                		   }
                	   }).state("app.addDatasource", {
                	        url: "/addDatasource",
                	        controller: 'DatasourceCtrl',
                	        templateUrl: "resources/views/datasources/addDatasource.html",
                	        resolve: {
                	            loadMyFiles: function ($ocLazyLoad) {
                	                return $ocLazyLoad.load({
                	                    name: 'bdaApp',
                	                    files: [
                	                        'resources/scripts/controllers/datasourceController.js',
                	                    ]
                	                })
                	            }
                	        }
                	    }).state("app.editDatasource", {
                	        url: "/editDatasource",
                	        controller: 'DatasourceCtrl',
                	        templateUrl: "resources/views/datasources/editDatasource.html",
                	        resolve: {
                	            loadMyFiles: function ($ocLazyLoad) {
                	                return $ocLazyLoad.load({
                	                    name: 'bdaApp',
                	                    files: [
                	                        'resources/scripts/controllers/datasourceController.js',
                	                    ]
                	                })
                	            }
                	        }
                	    }).state('app.chart',{
                		   templateUrl:'resources/views/charts/home.html',
                		   url:'/charts',
                		   controller:'ChartCtrl',
                		   resolve: {
                			   loadMyFiles: function ($ocLazyLoad) {
                				   return $ocLazyLoad.load({
                					   name: 'bdaApp',
                					   files: [
                					           	'resources/scripts/controllers/chartController.js',
                					           	'resources/scripts/directives/pageSelect.js'

                					          ]
                				   })
                			   }
                		   }
                	   }).state("app.addChart", {
                	        url: "/addChart",
                	        controller: 'ChartCtrl',
                	        templateUrl: "resources/views/charts/addChart.html",
                	        resolve: {
                	            loadMyFiles: function ($ocLazyLoad) {
                	                return $ocLazyLoad.load({
                	                    name: 'bdaApp',
                	                    files: [
                	                        'resources/scripts/controllers/chartController.js',
                	                        'resources/scripts/chartOptions.js',
                	                    ]
                	                })
                	            }
                	        }
                	    }).state("app.editChart", {
                	        url: "/editChart",
                	        controller: 'ChartCtrl',
                	        templateUrl: "resources/views/charts/editChart.html",
                	        resolve: {
                	            loadMyFiles: function ($ocLazyLoad) {
                	                return $ocLazyLoad.load({
                	                    name: 'bdaApp',
                	                    files: [
                	                        'resources/scripts/controllers/chartController.js',
                	                        'resources/scripts/chartOptions.js',
                	                    ]
                	                })
                	            }
                	        }
                	    })
                   }]);


