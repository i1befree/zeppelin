/**
 * @ngdoc function
 * @name zeppelinWebApp.controller:DatasourceCtrl
 * @description # DatasourceCtrl
 * 
 * @author 
 */
'use strict';

angular.module('zeppelinWebApp').controller('DatasourceCtrl', function($scope, $route, $routeParams, $location, UtilService) {

	$scope.workspaceObject = {};
	$scope.shareTypeAll = undefined;
	$scope.isEditMode = false;
	$scope.datasource = {};
	$scope.gridOptionsForDatasource = {
		showGridFooter: true,	
		enableRowSelection: true,
		multiSelect : false,
    enableRowHeaderSelection : false,
    onRegisterApi : function(gridApi){
      gridApi.selection.on.rowSelectionChanged($scope, function(row){
      	$scope.shareTypeAll = undefined;
      	if(row.isSelected) {
      		$scope.datasource = row.entity;
      		getWorkspaceObjectInfo();
      	} else {
      		$scope.datasource = {};
      		$scope.workspaceObject = {};
      		$scope.shareType = false;
      		$scope.gridOptionsForAssignedWorkspace.data = [];
      	}	
      	$scope.isEditMode = false;
      });
    },
		columnDefs : [
		  {name:'datsrcName'    , displayName: 'Name'      , enableColumnMenu: false, cellTooltip: function(row, col) {return row.entity[col.name];}},
		  {name:'datastore.type'  , displayName: 'Store Type', enableColumnMenu: false, cellTooltip: function(row, col) {return row.entity[col.name];}}
		]	
	};	
	
	$scope.selectedAssignedWorkspace = {};
	$scope.gridOptionsForAssignedWorkspace = {
		showGridFooter: true,	
		enableRowSelection: false,
		multiSelect : false,
		enableSelectAll: false,
		columnDefs : [
		  {name:'wrkspcName'  , displayName: 'Workspace Name', enableColumnMenu: false, cellTooltip: function(row, col) {return row.entity[col.name];}},
		  {name:'wrkspcType'  , displayName: 'Type'          , enableColumnMenu: false, cellTooltip: function(row, col) {return row.entity[col.name];}}
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
  
  function getWorkspaceObjectInfo() {
  	UtilService.httpPost('/datasource/getDatasourceObjectInfo', {wrkspcObjId : $scope.datasource.wrkspcObjId}).then(function(result) {
  		$scope.workspaceObject = result;
  		$scope.shareTypeAll = $scope.workspaceObject.shareType === 'ALL' ? true : false;
  		if($scope.shareTypeAll) {
  			$scope.gridOptionsForAssignedWorkspace.data = [];
  		} else {
  			$scope.gridOptionsForAssignedWorkspace.data = $scope.workspaceObject.workspaces;
  		}
  	}, function(error) {
  		alert(error);
  	});
  };
  
	$scope.create = function() {
	  $location.path('/datasourceWizard');
  };
  
  $scope.assignWorkspace = function() {
	  $location.path('/datasourceWorkspace/' + $scope.datasource.wrkspcObjId);
  };
  
//  $scope.editMode = function() {
//  	$scope.isEditMode = true;
//  };
  
  $scope.update = function() {
  	$scope.isEditMode = false;
  };
  
  $scope.cancel = function() {
  	$scope.isEditMode = false;
  };
  
  
  init();
});
// / @endcond
