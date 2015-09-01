/**
 * @ngdoc function
 * @name zeppelinWebApp.controller:DatasourceCtrl
 * @description # DatasourceCtrl
 * 
 * @author 
 */
'use strict';

angular.module('zeppelinWebApp').controller('DatasourceCtrl', function($scope, $route, $routeParams, $location, UtilService) {

	$scope.datasource = {};
	$scope.gridOptionsForDatasource = {
		showGridFooter: true,	
		enableRowSelection: true,
		multiSelect : false,
    enableRowHeaderSelection : false,
    onRegisterApi : function(gridApi){
      gridApi.selection.on.rowSelectionChanged($scope, function(row){
      	if(row.isSelected) {
      		$scope.datasource = row.entity;
      	} else {
      		$scope.datasource = {};
      	}	
      });
    },
		columnDefs : [
		  {name:'datsrcName'    , displayName: 'Name', enableColumnMenu: false},
		  {name:'datstoreType'  , displayName: 'Store Type', enableColumnMenu: false}
		]	
	};	
	
	function init() {
		getDatasourceList();
	}
	
  function getDatasourceList() {
  	UtilService.httpPost('/datasource/getList', {}).then(function(result) {
  		$scope.gridOptionsForDatasource.data = result;
  	}, function(error) {
  		alert(error);
  	});
  };
  
	$scope.create = function() {
	  $location.path('/datasourceWizard');
  }
  $scope.assignWorkspace = function() {
	  $location.path('/datasourceWorkspace/' + $scope.datasource.datasourceId);
  }
      
  init();
});
// / @endcond
