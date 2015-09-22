/**
 * @ngdoc function
 * @name zeppelinWebApp.controller:DatasourceWizardCtrl
 * @description # DatasourceWizardCtrl
 * 
 * @author 
 */
'use strict';

angular.module('zeppelinWebApp').controller('DatasourceWizardCtrl', function($scope, $route, $routeParams, $location, $timeout, UtilService) {

	$scope.wizardStepCount = 4; //위자드 단계의 총수
  $scope.wizardCurrentStep = 1; //위자드의 현재 단계 번호
  
  $scope.schema = {};
  $scope.datastore = {};
  $scope.datasource = {datastore : {}};
  $scope.selectedRow = {
  	container: undefined, srcObj: undefined
  };
		
  $scope.gridOptionsForSchema = {
		showGridFooter: true,	
		enableRowSelection: true,
		multiSelect : false,
    enableRowHeaderSelection : false,
    onRegisterApi : function(gridApi){
    	$scope.gridApiContainer = gridApi;
      gridApi.selection.on.rowSelectionChanged($scope, function(row){
      	if(row.isSelected) {
      		if(!UtilService.isUndefined($scope.selectedRow.container) && $scope.selectedRow.container.name !== row.entity.name) {
      			$scope.datasource.srcObjName = undefined;
          	$scope.selectedRow.srcObj = undefined;
      		}
      		$scope.selectedRow.container = row.entity;
        	$scope.gridOptionsForTable.data = row.entity.tables;
        	$scope.datasource.containerName = row.entity.name;
      	} else {
      		$scope.selectedRow.container = undefined;
      		$scope.gridOptionsForTable.data = [];
      		$scope.datasource.containerName = undefined;
      	}
      	$scope.gridOptionsForColumn.data = [];
      });
    },
		columnDefs : [
		  {name:'name'    , displayName: 'Name', enableColumnMenu: false}
		]	
	};
  $scope.gridOptionsForTable = {
		showGridFooter: true,	
		enableRowSelection: true,
		multiSelect : false,
		enableSelectAll: true,
    onRegisterApi : function(gridApi){
    	$scope.gridApiSrcObj = gridApi;
      gridApi.selection.on.rowSelectionChanged($scope, function(row){
      	if(row.isSelected) {
	      	$scope.selectedRow.srcObj = row.entity;
	      	$scope.gridOptionsForColumn.data = row.entity.columns;
	      	$scope.datasource.srcObjName = row.entity.name;
      	} else {
	      	$scope.selectedRow.srcObj = undefined;
      		$scope.gridOptionsForColumn.data = [];
	      	$scope.datasource.srcObjName = undefined;
      	}	
      });
    },
		columnDefs : [
		  {name:'name'    , displayName: 'Name'   , enableColumnMenu: false, width: 200, cellTooltip: function(row, col) {return row.entity[col.name];}},
		  {name:'comment' , displayName: 'Comment', enableColumnMenu: false, cellTooltip: function(row, col) {return row.entity[col.name];}}
		]	
	};
  $scope.gridOptionsForColumn = {
		showGridFooter: true,	
		enableRowSelection: false,
		multiSelect : false,
    enableRowHeaderSelection : false,
		columnDefs : [
		  {name:'name'    , displayName: 'Name'   , enableColumnMenu: false, width: 220, cellTooltip: function(row, col) {return row.entity[col.name];}},
		  {name:'type'    , displayName: 'Type'   , enableColumnMenu: false, width: 120, cellTooltip: function(row, col) {return row.entity[col.name];}},
		  {name:'size'    , displayName: 'Size'   , enableColumnMenu: false, width: 80, cellTooltip: function(row, col) {return row.entity[col.name];}},
		  {name:'comment' , displayName: 'Comment', enableColumnMenu: false, cellTooltip: function(row, col) {return row.entity[col.name];}}
		]	
	};
  
	function init() {
		getDatastoreAllList();
	}
	
	function getDatastoreAllList() {
  	UtilService.httpPost('/datasource/getDatastoreAllList', {}).then(function(result) {
  		$scope.datastoreList = result;
  		if($scope.datastoreList.length > 0) {
  			$scope.datasource.datastore.id = $scope.datastoreList[0].id;
        $scope.getLayoutSchemaList();	
  		}
  	}, function(error) {
  		alert(error);
  	});
  };
	
  $scope.getLayoutSchemaList = function() {
  	$scope.gridOptionsForSchema.data = [];
  	$scope.gridOptionsForTable.data = [];
		$scope.gridOptionsForColumn.data = [];
//  	if($scope.schema[$scope.datasource.datastore.id] !== undefined) {
//  		$scope.gridOptionsForSchema.data = $scope.schema[$scope.datasource.datastore.id];
//  		return;
//  	}
		console.info('$scope.datasource', $scope.datasource);
  	UtilService.httpPost('/datasource/loadDatasourceMetadata', {id: $scope.datasource.datastore.id}).then(function(result) {
  		$scope.schema[$scope.datasource.datastore.id] = result;
  		$scope.gridOptionsForSchema.data = $scope.schema[$scope.datasource.datastore.id];
  	}, function(error) {
  		alert(error);
  	});
  };
  
  $scope.isOrNullUndefinedEmpty = function(value) {
  	return UtilService.isOrNullUndefinedEmpty(value);
  }
  
	$scope.cancel = function() {
		$location.path('/datasource');
	};
	
	$scope.previous = function() {
		switch($scope.wizardCurrentStep) {
			case 1:
				break;
			case 2:
				break;
			case 3:
				//화면 전환시 테이블에서 선택된 로우의 포커스를 읽어버리는 현상 발견 - 강제로 selected row 세팅
				$timeout(function() {
					if(!UtilService.isUndefined($scope.selectedRow.container)) {
						$scope.gridApiContainer.selection.toggleRowSelection($scope.selectedRow.container);
					}
					$timeout(function(){
						if(!UtilService.isUndefined($scope.selectedRow.srcObj)) {
							$scope.gridApiSrcObj.selection.toggleRowSelection($scope.selectedRow.srcObj);
						}
					}, 10);
				}, 10);				
				break;
			case 4:
				break;	
		}
    $scope.wizardCurrentStep = $scope.wizardCurrentStep - 1;
	};
	
	$scope.next = function() {
		console.info('$scope.datastore.datstoreType', $scope.datastore.type);
		switch($scope.wizardCurrentStep) {
			case 1: 
				angular.forEach($scope.datastoreList, function(item, index) {
		  		console.info(item.id , $scope.datasource.datastore.id);
		  		if(item.id === $scope.datasource.datastore.id) {
		  			$scope.datastore = item;
		  		}
		  	});
				$timeout(function() {
					if(!UtilService.isUndefined($scope.selectedRow.container)) {
						$scope.gridApiContainer.selection.toggleRowSelection($scope.selectedRow.container);
					}
					$timeout(function(){
						if(!UtilService.isUndefined($scope.selectedRow.srcObj)) {
							$scope.gridApiSrcObj.selection.toggleRowSelection($scope.selectedRow.srcObj);
						}
					}, 10);
				}, 10);			
				break;
			case 2:
				if(UtilService.isOrNullUndefinedEmpty($scope.datasource.srcObjName)) {
					return;
				}
				break;
			case 3:
				if($scope.form.datsrcName.$invalid
				|| $scope.form.description.$invalid) {
					return;
				}
				break;
			case 4:
				break;	
		}
		$scope.wizardCurrentStep = $scope.wizardCurrentStep + 1;
	};
	
	$scope.complete = function() {
    if(confirm('완료 하시겠습니까?')) {
    	var jsonData = angular.copy($scope.datasource);
    	UtilService.httpPost('/datasource/create', jsonData).then(function(result) {
    		if (result.rsCode === 'SUCCESS') {
    			$location.path('/datasource');
        } else {
          alert(result.rsMessage);
        }
    	}, function(error) {
    		alert(error);
    	});
    }
	};
	
	init();
});
// / @endcond
