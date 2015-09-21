/**
 * @ngdoc function
 * @name zeppelinWebApp.controller:DatastoreCtrl
 * @description # DatastoreCtrl
 * 
 * @author 
 */
'use strict';

angular.module('zeppelinWebApp').controller('DatastoreCtrl', function($scope, $route, $routeParams, $location, UtilService) {

	$scope.isEditMode = false;
	$scope.datastore = {};
	$scope.gridOptionsForDatastore = {
		showGridFooter: true,	
		enableRowSelection: true,
		multiSelect : false,
    enableRowHeaderSelection : false,
    onRegisterApi : function(gridApi){
      gridApi.selection.on.rowSelectionChanged($scope, function(row){
      	if(row.isSelected) {
      		$scope.datastore = row.entity;
      		getWorkspaceObjectInfo();
      	} else {
      		$scope.datastore = {};
      	}	
      	$scope.isEditMode = false;
      });
    },
		columnDefs : [
		  {name:'name'    , displayName: 'Name'      , enableColumnMenu: false, cellTooltip: function(row, col) {return row.entity[col.name];}},
		  {name:'type'    , displayName: 'Store Type', enableColumnMenu: false, cellTooltip: function(row, col) {return row.entity[col.name];}}
		]	
	};	
	
	function init() {
		getDatastoreList();
	}
	
  function getDatastoreList() {
  	UtilService.httpPost('/datastore/getList', {}).then(function(result) {
  		$scope.gridOptionsForDatastore.data = result;
  	}, function(error) {
  		alert(error);
  	});
  };
  
  function getWorkspaceObjectInfo() {
  	

  };
  
	$scope.create = function() {
	  $location.path('/datastoreWizard');
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
