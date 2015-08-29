/**
 * @ngdoc function
 * @name zeppelinWebApp.controller:WorkspaceWizardCtrl
 * @description # WorkspaceWizardCtrl
 * 
 * @author 
 */
'use strict';

angular.module('zeppelinWebApp').controller('WorkspaceWizardCtrl', function($scope, $route, $routeParams, $location, UtilService) {

	console.info('$routeParams.workspaceId', $routeParams.workspaceId);
	$scope.workspaceId = $routeParams.workspaceId;
	$scope.workspaceInfo = {};
	$scope.memberList = [];
	$scope.display = 'info';
	
	function init() {
		$scope.getWorkspaceSummaryInfo($scope.workspaceId);
		$scope.getWorkspaceMemberList($scope.workspaceId);
	}
	
	$scope.getWorkspaceSummaryInfo = function(pWorkspaceId) {
		if($scope.workspaceInfo.wrkspcId !== undefined) {
  		return;
  	}
		UtilService.httpPost('/workspace/getWorkspaceSummaryInfo', {wrkspcId: pWorkspaceId}).then(function(result) {
  		$scope.workspaceInfo = result;
  	}, function(error) {
  		alert(error);
  	});
  };
  
  $scope.getWorkspaceMemberList = function(pWorkspaceId) {
  	if($scope.memberList.length > 0) {
  		return;
  	}
		UtilService.httpPost('/workspace/getWorkspaceMemberList', {wrkspcId: pWorkspaceId}).then(function(result) {
  		$scope.memberList = result;
  	}, function(error) {
  		alert(error);
  	});
  };
  
  init();	
});
// / @endcond
