'use strict';

/**
 * @ngdoc directive
 * @name izzyposWebApp.directive:adminPosHeader
 * @description
 * # adminPosHeader
 */
angular.module('bdaApp')
	.directive('header',function(){
		return {
        templateUrl:'resources/scripts/directives/header/header.html',
        restrict: 'E',
        replace: true,
    	}
	});


