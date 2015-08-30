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
	$scope.datatableContainerHeight = 400;
	$scope.dtOptions = {
		paging: false,
    searching: true,
    scrollY: $scope.datatableContainerHeight - 65,
    sDom: 'rt<i>'
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
  	}, function(error) {
  		alert(error);
  	});
  };
  
  function getDatasourceList(pWorkspaceId) {
  	$('#datasourceTable').DataTable($scope.datasourceTableOptions);
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
