/**
 * @ngdoc function
 * @name zeppelinWebApp.controller:DatasourceWorkspaceCtrl
 * @description # DatasourceWorkspaceCtrl
 * 
 * @author 
 */
'use strict';

angular.module('zeppelinWebApp').controller('DatasourceWorkspaceCtrl', function($scope, $route, $routeParams, $location) {

	$scope.datatableContainerHeight = 430;
	$scope.dtOptions = {
    paging: false,
    searching: true,
    scrollY: $scope.datatableContainerHeight - 95,
    sDom: '<f>rt<i>',
  };
	$scope.dtOptions1 = {
    paging: false,
    searching: true,
    scrollY: $scope.datatableContainerHeight - 65,
    sDom: 'rt<i>',
  };
	$scope.selected = {};
	$scope.selectAll = false;
	
		
  $scope.save = function() {
	  $location.path('/datasource');
  }
  $scope.cancel = function() {
	  $location.path('/datasource');
  }

  $scope.toggleAll = function(selectAll, selectedItems) {
  	toggleAll(selectAll, selectedItems);
  };
  
  $scope.toggleOne = function (selectedItems) {
  	toggleOne($scope.selectAll, selectedItems);
  };
  
  function toggleAll(selectAll, selectedItems) {
  	for (var id in selectedItems) {
      if (selectedItems.hasOwnProperty(id)) {
        selectedItems[id] = selectAll;
      }
    }
  }
  
  function toggleOne(selectAll, selectedItems) {
  	for (var id in selectedItems) {
      if (selectedItems.hasOwnProperty(id)) {
        if(!selectedItems[id]) {
        	selectAll = false;
          return;
        }
      }
    }
    selectAll = true;
  }
  
});
// / @endcond
