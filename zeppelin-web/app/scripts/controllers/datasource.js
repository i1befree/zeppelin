/**
 * @ngdoc function
 * @name zeppelinWebApp.controller:DatasourceCtrl
 * @description # DatasourceCtrl
 * 
 * @author 
 */
'use strict';

angular.module('zeppelinWebApp').controller('DatasourceCtrl', function($scope, $route, $routeParams, $location, UtilService) {

	$scope.isEditMode = false;
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
      	$scope.isEditMode = false;
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
  };
  
  $scope.assignWorkspace = function() {
	  $location.path('/datasourceWorkspace/' + $scope.datasource.datasourceId);
  };
  
  $scope.editMode = function() {
  	$scope.isEditMode = true;
  };
  
  $scope.update = function() {
  	$scope.isEditMode = false;
  };
  
  $scope.cancel = function() {
  	$scope.isEditMode = false;
  };
  
  
  init();
});
// / @endcond
