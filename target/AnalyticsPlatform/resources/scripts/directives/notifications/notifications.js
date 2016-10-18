'use strict';

/**
 * @ngdoc directive
 * @name izzyposWebApp.directive:adminPosHeader
 * @description
 * # adminPosHeader
 */
angular.module('bdaApp')
	.directive('notifications',function(){
		return {
        templateUrl:'resources/scripts/directives/notifications/notifications.html',
        restrict: 'E',
        replace: true,
    	}
	});


