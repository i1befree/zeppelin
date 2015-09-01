/**
 * @ngdoc function
 * @name zeppelinWebApp.controller:DatasourceWorkspaceCtrl
 * @description # DatasourceWorkspaceCtrl
 * 
 * @author 
 */
'use strict';

angular.module('zeppelinWebApp').controller('DatasourceWorkspaceCtrl', function($scope, $route, $routeParams, $location, UtilService) {

	$scope.selectedWorkspace = {};
	$scope.gridOptionsForWorkspace = {
		showGridFooter: true,	
		enableRowSelection: true,
		multiSelect : true,
		enableSelectAll: true,
    onRegisterApi : function(gridApi){
    	gridApi.selection.on.rowSelectionChanged($scope,function(row){
    		$scope.selectedWorkspace[row.entity.userId] = row.isSelected;
      });
    	gridApi.selection.on.rowSelectionChangedBatch($scope,function(rows){
    		angular.forEach(rows, function(row, index) {
    			$scope.selectedWorkspace[row.entity.userId] = row.isSelected;
    		});
      });
    },
		columnDefs : [
		  {name:'wrkspcName'  , displayName: 'Workspace Name', enableColumnMenu: false},
		  {name:'wrkspcType'  , displayName: 'Type'  , enableColumnMenu: false}
		]	
	};	
	$scope.selectedAssignedWorkspace = {};
	$scope.gridOptionsForAssignedWorkspace = {
		showGridFooter: true,	
		enableRowSelection: true,
		multiSelect : true,
		enableSelectAll: true,
    onRegisterApi : function(gridApi){
    	gridApi.selection.on.rowSelectionChanged($scope,function(row){
    		$scope.selectedAssignedWorkspace[row.entity.userId] = row.isSelected;
      });
    	gridApi.selection.on.rowSelectionChangedBatch($scope,function(rows){
    		angular.forEach(rows, function(row, index) {
    			$scope.selectedAssignedWorkspace[row.entity.userId] = row.isSelected;
    		});
      });
    },
		columnDefs : [
		  {name:'wrkspcName'  , displayName: 'Workspace Name', enableColumnMenu: false},
		  {name:'wrkspcType'  , displayName: 'Type'  , enableColumnMenu: false}
		]	
	};	
	
	function init() {
		getWorkspaceList();
	}
	
  function getWorkspaceList() {
  	var formData = {
        beginRowNum : 0,
        rowsPerPage : 10000
      };
  	UtilService.httpPost('/datasource/getWorkspaceList', formData).then(function(result) {
  		$scope.gridOptionsForWorkspace.data = result;
  	}, function(error) {
  		alert(error);
  	});
  };
  
	
  $scope.save = function() {
	  $location.path('/datasource');
  }
  $scope.cancel = function() {
	  $location.path('/datasource');
  }

  init();
});
// / @endcond
