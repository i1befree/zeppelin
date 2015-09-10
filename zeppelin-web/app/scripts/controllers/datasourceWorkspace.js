/**
 * @ngdoc function
 * @name zeppelinWebApp.controller:DatasourceWorkspaceCtrl
 * @description # DatasourceWorkspaceCtrl
 * 
 * @author 
 */
'use strict';

angular.module('zeppelinWebApp').controller('DatasourceWorkspaceCtrl', function($scope, $route, $routeParams, $location, UtilService) {

	console.info('$routeParams.datasourceId', $routeParams.datasourceId);
	$scope.datasourceId = $routeParams.datasourceId;
	$scope.workspaceObject = {};
	
	$scope.selectedWorkspace = {};
	$scope.gridOptionsForWorkspace = {
		showGridFooter: true,	
		enableRowSelection: true,
		multiSelect : true,
		enableSelectAll: true,
    onRegisterApi : function(gridApi){
    	$scope.gridApiWorkspace = gridApi;
    	gridApi.selection.on.rowSelectionChanged($scope,function(row){
    		$scope.selectedWorkspace[row.entity.wrkspcId] = row.isSelected;
      });
    	gridApi.selection.on.rowSelectionChangedBatch($scope,function(rows){
    		angular.forEach(rows, function(row, index) {
    			$scope.selectedWorkspace[row.entity.wrkspcId] = row.isSelected;
    		});
      });
    },
		columnDefs : [
		  {name:'wrkspcName'  , displayName: 'Workspace Name', enableColumnMenu: false, cellTooltip: function(row, col) {return row.entity[col.name];}},
		  {name:'wrkspcType'  , displayName: 'Type'          , enableColumnMenu: false, cellTooltip: function(row, col) {return row.entity[col.name];}}
		]	
	};	
	$scope.selectedAssignedWorkspace = {};
	$scope.gridOptionsForAssignedWorkspace = {
		showGridFooter: true,	
		enableRowSelection: true,
		multiSelect : true,
		enableSelectAll: true,
    onRegisterApi : function(gridApi){
    	$scope.gridApiAssignedWorkspace = gridApi;
    	gridApi.selection.on.rowSelectionChanged($scope,function(row){
    		$scope.selectedAssignedWorkspace[row.entity.wrkspcId] = row.isSelected;
      });
    	gridApi.selection.on.rowSelectionChangedBatch($scope,function(rows){
    		angular.forEach(rows, function(row, index) {
    			$scope.selectedAssignedWorkspace[row.entity.wrkspcId] = row.isSelected;
    		});
      });
    },
		columnDefs : [
		  {name:'wrkspcName'  , displayName: 'Workspace Name', enableColumnMenu: false, cellTooltip: function(row, col) {return row.entity[col.name];}},
		  {name:'wrkspcType'  , displayName: 'Type'          , enableColumnMenu: false, cellTooltip: function(row, col) {return row.entity[col.name];}}
		]	
	};	
	
	function init() {
		getWorkspaceObjectInfo();
		getWorkspaceList();
		getAssignedWorkspaceList();
	}
	
	function getWorkspaceObjectInfo() {
  	UtilService.httpPost('/datasource/getWorkspaceObjectInfo', {wrkspcObjId : $scope.datasourceId}).then(function(result) {
  		$scope.workspaceObject = result;
  		$scope.shareType = $scope.workspaceObject.shareType === 'ALL' ? true : false;
  	}, function(error) {
  		alert(error);
  	});
  };
  
  function getWorkspaceList() {
  	var formData = {
        beginRowNum : 0,
        rowsPerPage : 100000
      };
  	UtilService.httpPost('/datasource/getWorkspaceList', formData).then(function(result) {
  		$scope.gridOptionsForWorkspace.data = result;
  	}, function(error) {
  		alert(error);
  	});
  };
  
  function getAssignedWorkspaceList() {
  	var formData = {
        wrkspcObjId : $scope.datasourceId
      };
  	UtilService.httpPost('/datasource/getAssignedWorkspaceList', formData).then(function(result) {
  		$scope.gridOptionsForAssignedWorkspace.data = result;
  	}, function(error) {
  		alert(error);
  	});
  };
  
  $scope.isRowChecked = function(selectedData) {
		for (var id in selectedData) {
      if (selectedData.hasOwnProperty(id)) {
        if(selectedData[id] === true) {
        	return true;
        }
      }
    }
  	return false;
  }
  
  $scope.addWorkspace = function() {
  	var rows = $scope.gridApiWorkspace.selection.getSelectedGridRows();
  	angular.forEach(rows, function(item, index) {
  		var isContain = false;
  		angular.forEach($scope.gridOptionsForAssignedWorkspace.data, function(info, idx) {
  			if(item.entity.wrkspcId === info.wrkspcId) {
  				isContain = true;
  			}	
  		});
  		if(!isContain) {
  			$scope.gridOptionsForAssignedWorkspace.data.push(item.entity);
  		}
  	});
  };
	
  $scope.removeWorkspace = function() {
  	var rows = $scope.gridApiAssignedWorkspace.selection.getSelectedGridRows();
  	angular.forEach(rows, function(row, i) {
  		var index = $scope.gridOptionsForAssignedWorkspace.data.indexOf(row.entity);
  		$scope.gridOptionsForAssignedWorkspace.data.splice(index, 1);
  		delete $scope.selectedAssignedWorkspace[row.entity.wrkspcId];
  	});
  };
	
  $scope.save = function() {  	
  	var jsonData = {
  			wrkspcObjId : $scope.datasourceId,
  			shareType : $scope.shareType === true ? 'ALL' : 'NONE',
  			workspaceAssigns : $scope.gridOptionsForAssignedWorkspace.data
  	};
  	UtilService.httpPost('/datasource/saveAssignWorkspace', jsonData).then(function(result) {
  		if (result.rsCode === 'SUCCESS') {
  			$location.path('/datasource');
      } else {
        alert(result.rsMessage);
      }
  	}, function(error) {
  		alert(error);
  	});
  }
  $scope.cancel = function() {
	  $location.path('/datasource');
  }

  init();
});
// / @endcond
