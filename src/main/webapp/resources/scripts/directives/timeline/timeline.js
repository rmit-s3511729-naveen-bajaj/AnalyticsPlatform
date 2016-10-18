'use strict';

/**
 * @ngdoc directive
 * @name izzyposWebApp.directive:adminPosHeader
 * @description
 * # adminPosHeader
 */
angular.module('bdaApp')
	.directive('timeline',function() {
    return {
        templateUrl:'resources/scripts/directives/timeline/timeline.html',
        restrict: 'E',
        replace: true,
    }
  });
