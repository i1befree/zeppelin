/**
 * @ngdoc function
 * @name zeppelinWebApp.controller:DatasourceCtrl
 * @description # DatasourceCtrl
 * 
 * @author 
 */
'use strict';

angular.module('zeppelinWebApp').controller('DatasourceCtrl', function($scope, $route, $routeParams, $location) {

	$scope.datasource = {};
	$scope.gridOptionsForDatasource = {
		showGridFooter: true,	
		enableRowSelection: true,
		multiSelect : false,
    enableRowHeaderSelection : true,
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
	
	$scope.gridOptionsForDatasource.data = [{name:'name1', type:'type1'},
	                                        {name:'name1', type:'type1'},
	                                        {name:'name1', type:'type1'},
	                                        {name:'name1', type:'type1'},
	                                        {name:'name1', type:'type1'},
	                                        {name:'name1', type:'type1'},
	                                        {name:'name1', type:'type1'},
	                                        {name:'name1', type:'type1'}
	                                        ];
	
	$scope.create = function() {
	  $location.path('/datasourceWizard');
  }
  $scope.assignWorkspace = function() {
	  $location.path('/datasourceWorkspace/' + $scope.datasource.datasourceId);
  }
});
// / @endcond
