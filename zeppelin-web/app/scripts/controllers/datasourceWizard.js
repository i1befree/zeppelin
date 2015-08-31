/**
 * @ngdoc function
 * @name zeppelinWebApp.controller:DatasourceWizardCtrl
 * @description # DatasourceWizardCtrl
 * 
 * @author 
 */
'use strict';

angular.module('zeppelinWebApp').controller('DatasourceWizardCtrl', function($scope, $route, $routeParams, $location, UtilService) {

	$scope.wizardStepCount = 4; //위자드 단계의 총수
  $scope.wizardCurrentStep = 1; //위자드의 현재 단계 번호
  
  $scope.datastore = {};
  $scope.datasource = {};
  $scope.datastoreList = [
    {datstoreId: '123', datstoreName:'인터널 ES', datstoreType:'Internal'},                      
    {datstoreId: '124', datstoreName:'데이타베이스', datstoreType:'RDB', datstoreSubtype:'MYSQL'}
  ];
  $scope.gridOptionsForSchema = {
		showGridFooter: true,	
		enableRowSelection: true,
		multiSelect : false,
    enableRowHeaderSelection : false,
    onRegisterApi : function(gridApi){
      gridApi.selection.on.rowSelectionChanged($scope, function(row){
      	$scope.gridOptionsForTable.data = row.entity.tables;
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
      gridApi.selection.on.rowSelectionChanged($scope, function(row){
      	$scope.gridOptionsForColumn.data = row.entity.columns;
      });
    },
		columnDefs : [
		  {name:'name'    , displayName: 'Name', enableColumnMenu: false},
		  {name:'comment' , displayName: 'Comment', enableColumnMenu: false}
		]	
	};
  $scope.gridOptionsForColumn = {
		showGridFooter: true,	
		enableRowSelection: false,
		multiSelect : false,
    enableRowHeaderSelection : false,
		columnDefs : [
		  {name:'name'    , displayName: 'Name', enableColumnMenu: false},
		  {name:'type'    , displayName: 'Type', enableColumnMenu: false},
		  {name:'size'    , displayName: 'Size', enableColumnMenu: false},
		  {name:'comment' , displayName: 'Comment', enableColumnMenu: false}
		]	
	};
  
  $scope.selectDatastore = function() {
  	angular.forEach($scope.datastoreList, function(item, index) {
  		console.info(item.datstoreId , $scope.datasource.datstoreId);
  		if(item.datstoreId === $scope.datasource.datstoreId) {
  			$scope.datastore = item;
  		}
  	});
  	console.info($scope.datastore);
  };

	function init() {
		getLayoutSchemaList();
	}
	
  function getLayoutSchemaList() {
  	UtilService.httpPost('/datasoruce/loadDatasourceMetadata', {}).then(function(result) {
  		$scope.schema = result;
  		$scope.gridOptionsForSchema.data = $scope.schema;
  	}, function(error) {
  		alert(error);
  	});
  };
  
	$scope.cancel = function() {
		$location.path('/datasource');
	};
	
	$scope.previous = function() {
    $scope.wizardCurrentStep = $scope.wizardCurrentStep - 1;
	};
	
	$scope.next = function() {
		console.info('$scope.datastore.datstoreType', $scope.datastore.datstoreType);
		switch($scope.wizardCurrentStep) {
			case 1: 

				break;
		}
		$scope.wizardCurrentStep = $scope.wizardCurrentStep + 1;
	};
	
	$scope.complete = function() {
    if(!confirm('완료 하시겠습니까?')) {
    	return;
    }
    
    $location.path('/datasource');

	};
	
	init();
});
// / @endcond
