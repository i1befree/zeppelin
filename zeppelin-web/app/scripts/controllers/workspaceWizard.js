/**
 * @ngdoc function
 * @name zeppelinWebApp.controller:WorkspaceWizardCtrl
 * @description # WorkspaceWizardCtrl
 * 
 * @author 
 */
'use strict';

angular.module('zeppelinWebApp').controller('WorkspaceWizardCtrl', function($scope, $rootScope, $route, $routeParams, $location, Authentication, UtilService) {

	console.info('$routeParams.workspaceId', $routeParams.workspaceId);
	$scope.workspaceId = $routeParams.workspaceId;
	$scope.workspaceInfo = {};
	$scope.memberList = [];
	$scope.datatableContainerHeight = 400;
	$scope.dtOptions = {
		paging: false,
    searching: true,
    scrollY: $scope.datatableContainerHeight - 65,
    sDom: 'rt<i>'
	};
	$scope.selected = {};
	$scope.selectAll = false;
	$scope.display = 'info';
	
	function init() {
		$scope.getWorkspaceSummaryInfo();

		$rootScope.$on('initWorkspaceMemberList', function(event, args) {
			$scope.workspaceInfo = {};
			$scope.memberList = [];
			$scope.selectAll = false;
			$scope.getWorkspaceMemberList();
		});
	}
	
	$scope.getId = function() {
		return $scope.workspaceId;
	};
	
	$scope.getWorkspaceSummaryInfo = function() {
		$scope.display = 'info';
		if($scope.workspaceInfo.wrkspcId !== undefined) {
  		return;
  	}
		UtilService.httpPost('/workspace/getWorkspaceSummaryInfo', {wrkspcId: $scope.workspaceId}).then(function(result) {
  		$scope.workspaceInfo = result;
  	}, function(error) {
  		alert(error);
  	});
  };
  
  $scope.getWorkspaceMemberList = function() {
  	$scope.display = 'member';
  	if($scope.memberList.length > 0) {
  		return;
  	}
		UtilService.httpPost('/workspace/getWorkspaceMemberList', {wrkspcId: $scope.workspaceId}).then(function(result) {
			$scope.memberList = result;
  		angular.forEach($scope.memberList, function(item, index) {
  			$scope.selected[item.userId] = false;
  		});
  	}, function(error) {
  		alert(error);
  	});
  };
  
  $scope.showMemberList = function() {
  	$scope.$emit('initUserSelectView', {});
  }
  
  $scope.removeMembers = function() {
  	var members = getCheckedMembers();
  	console.info('members', members);
  	UtilService.httpPost('/workspace/removeMembers', {members: members, wrkspcId: $scope.workspaceId}).then(function(result) {
  		$scope.$emit('initWorkspaceMemberList', {});
  	}, function(error) {
  		alert(error);
  	});
  };
  
  function getCheckedMembers() {
  	var members = [];
  	for (var id in $scope.selected) {
      if ($scope.selected.hasOwnProperty(id)) {
        if($scope.selected[id] === true) {
        	members.push(id);
        }
      }
    }
  	return members;
  }
  
  $scope.isRowChecked = function() {
  	if(getCheckedMembers().length > 0) {
  		return true;
  	}
  	return false;
  };
  
  $scope.toggleAll = function(selectAll, selectedItems) {
  	console.info('toggleAll $scope.selectAll', $scope.selectAll);
  	UtilService.toggleAll(selectAll, selectedItems, Authentication.getUser().id);
  	//console.info('toggleAll $scope.selectAll', $scope.selectAll);
  };
  
  $scope.toggleOne = function (selectedItems) {
  	UtilService.toggleOne($scope, selectedItems);
  	console.info('toggleOne $scope.selectAll', $scope.selectAll);
  };
  
  
  init();	
});
// / @endcond
