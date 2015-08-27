/**
 * @ngdoc function
 * @name zeppelinWebApp.controller:DatasourceCtrl
 * @description # DatasourceCtrl
 * 
 * @author 
 */
'use strict';

angular.module('zeppelinWebApp').controller('DatasourceCtrl', function($scope, $route, $routeParams, $location) {

  $scope.create = function() {
	  $location.path('/datasourceWizard/:');
  }
  $scope.assignWorkspace = function() {
	  $location.path('/datasourceWorkspace/:');
  }
});
// / @endcond
