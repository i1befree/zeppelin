/**
 * @ngdoc function
 * @name zeppelinWebApp.controller:UserUpdateCtrl
 * @description # UserUpdateCtrl 개인정보변경 - 사용자의 개인정보를 변경한다.
 * 
 * @author 박상민
 */
'use strict';

angular.module('zeppelinWebApp').controller('WorkspaceCtrl', function($scope, $route, $routeParams, $location, $rootScope, $http, Authentication, UtilService) {

	var workspaceId = $routeParams.workspaceId;
	$scope.gridOptionsForNotebook = {
		showGridFooter: true,	
		enableRowSelection: true,
		multiSelect : false,
    enableRowHeaderSelection : false,
    onRegisterApi : function(gridApi){
      gridApi.selection.on.rowSelectionChanged($scope, function(row){
      	$scope.goNotebook(row.entity);
      });
    },
		columnDefs : [
		  {name:'noteName'    , displayName: '노트명', enableColumnMenu: false, cellTooltip: function(row, col) {return row.entity[col.name];}},
		  {name:'createUserId', displayName: '생성자', enableColumnMenu: false, cellTooltip: function(row, col) {return row.entity[col.name];}},
		  {name:'updateDate'  , displayName: '최종수정 일시', cellFilter: 'date : "yyyy-MM-dd HH:mm:ss"', enableColumnMenu: false}
		]	
	};	
	$scope.gridOptionsForDatasource = {
		showGridFooter: true,	
		enableRowSelection: true,
		multiSelect : false,
    enableRowHeaderSelection : false,
		columnDefs : [
		  {name:'datsrcName'    , displayName: 'Name'      , enableColumnMenu: false, cellTooltip: function(row, col) {return row.entity[col.name];}},
		  {name:'datstoreType'  , displayName: 'Store Type', enableColumnMenu: false, cellTooltip: function(row, col) {return row.entity[col.name];}}
		]	
	};
		
	function init() {
		getNotebookList(workspaceId);
		getDatasourceList(workspaceId);
	}
	
	$scope.createNewNote = function() {    
    $rootScope.$emit('sendNewEvent', {op: 'NEW_NOTE', data:{userId: Authentication.getId(), workspaceId:workspaceId}});
  };
  
  $scope.$on('setNoteMenu', function(event, notes) {
  	getNotebookList(workspaceId);
  });
  
  function getNotebookList(pWorkspaceId) {
  	UtilService.httpPost('/workspace/getNotebookList', {wrkspcId: pWorkspaceId}).then(function(result) {
  		$scope.gridOptionsForNotebook.data = result;
  	}, function(error) {
  		alert(error);
  	});
  };
  
  function getDatasourceList(pWorkspaceId) {
  	UtilService.httpPost('/workspace/getDatasourceList', {wrkspcId: pWorkspaceId}).then(function(result) {
  		$scope.gridOptionsForDatasource.data = result;
  	}, function(error) {
  		alert(error);
  	});
  }
  
  $scope.manage = function() {
	  $location.path('/workspaceWizard/' + workspaceId);
  };
  
  $scope.goNotebook = function(note) {    
  	$location.path('/notebook/' + note.noteId + '/wrkspcId/' + workspaceId);
  };

  init();
});
// / @endcond
