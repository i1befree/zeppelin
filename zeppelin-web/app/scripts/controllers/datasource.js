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
      	console.info('row', row);
      });
    },
		columnDefs : [
		  {name:'name'    , displayName: 'Name', enableColumnMenu: false},
		  {name:'type', displayName: 'Type', enableColumnMenu: false}
		]	
	};	
	
	$scope.gridOptionsForDatasource.data = [{name:'name1', type:'Internal'},
	                                        {name:'name2', type:'Internal'},
	                                        {name:'name3', type:'RDB'},
	                                        {name:'name4', type:'Internal'},
	                                        {name:'name5', type:'RDB'},
	                                        {name:'name6', type:'Internal'},
	                                        {name:'name7', type:'RDB'},
	                                        {name:'name8', type:'RDB'}
	                                        ];
	function init() {
		
	}
	
	$scope.create = function() {
	  $location.path('/datasourceWizard');
  }
  $scope.assignWorkspace = function() {
	  $location.path('/datasourceWorkspace/' + $scope.datasource.datasourceId);
  }
      
  init();
});
// / @endcond
