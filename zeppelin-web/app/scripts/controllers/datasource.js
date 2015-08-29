/**
 * @ngdoc function
 * @name zeppelinWebApp.controller:DatasourceCtrl
 * @description # DatasourceCtrl
 * 
 * @author 
 */
'use strict';

angular.module('zeppelinWebApp').controller('DatasourceCtrl', function($scope, $route, $routeParams, $location) {

	$scope.datatableContainerHeight = 430;
	$scope.dtOptions = {
    paging: false,
    searching: true,
    scrollY: $scope.datatableContainerHeight - 95,
    sDom: '<f>rt<i>',
  };
	
	$scope.create = function() {
	  $location.path('/datasourceWizard/:');
  }
  $scope.assignWorkspace = function() {
	  $location.path('/datasourceWorkspace/:');
  }
});
// / @endcond
