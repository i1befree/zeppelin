/**
 * @ngdoc function
 * @name zeppelinWebApp.controller:UserUpdateCtrl
 * @description # UserUpdateCtrl 개인정보변경 - 사용자의 개인정보를 변경한다.
 * 
 * @author 박상민
 */
'use strict';

angular.module('zeppelinWebApp').controller('WorkspaceCtrl', function($scope, $route, $routeParams, $location, $rootScope, $http, Authentication, UtilService) {

	console.info('$routeParams.workspaceId', $routeParams.workspaceId);
	var workspaceId = $routeParams.workspaceId;
	$scope.notes = [];
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
		  {name:'noteName'    , displayName: '노트명', enableColumnMenu: false},
		  {name:'createUserId', displayName: '생성자', enableColumnMenu: false},
		  {name:'updateDate'  , displayName: '최종수정 일시', cellFilter: 'date : "yyyy-MM-dd HH:mm:ss"', enableColumnMenu: false}
		]	
	};	
	$scope.gridOptionsForDatasource = {
		showGridFooter: true,	
		enableRowSelection: true,
		multiSelect : false,
    enableRowHeaderSelection : false,
		columnDefs : [
		  {name:'name'    , displayName: 'Datasource', enableColumnMenu: false}
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
  	//$scope.notes = notes;
  	getNotebookList(workspaceId);
  });
  
  function getNotebookList(pWorkspaceId) {
  	UtilService.httpPost('/workspace/getNotebookList', {wrkspcId: pWorkspaceId}).then(function(result) {
  		$scope.notes = result;
  		$scope.gridOptionsForNotebook.data = $scope.notes;
  	}, function(error) {
  		alert(error);
  	});
  };
  
  function getDatasourceList(pWorkspaceId) {
  	var datasources = [
  	  {name: 'Datasource 1'},
  	  {name: 'Datasource 2'},
  	  {name: 'Datasource 3'}
  	];
		$scope.gridOptionsForDatasource.data = datasources;
  }
  
  $scope.manage = function() {
	  $location.path('/workspaceWizard/' + workspaceId);
  };
  
  $scope.goNotebook = function(note) {    
  	$location.path('/notebook/' + note.noteId);
  };

  init();
});
// / @endcond
