/**
 * @ngdoc function
 * @name zeppelinWebApp.controller:DatasourceWorkspaceCtrl
 * @description # DatasourceWorkspaceCtrl
 * 
 * @author 
 */
'use strict';

angular.module('zeppelinWebApp').controller('DatasourceWorkspaceCtrl', function($scope, $route, $routeParams, $location) {

  $scope.save = function() {
	  $location.path('/datasource');
  }
  $scope.cancel = function() {
	  $location.path('/datasource');
  }
});
// / @endcond
