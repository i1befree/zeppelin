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
	$scope.datatableContainerHeight = 400;
	$scope.dtOptions = {
    paging: false,
    searching: true,
    scrollY: $scope.datatableContainerHeight - 65,
    sDom: 'rt<i>',
  };
	$scope.selected = {};
	$scope.selectAll = false;
	$scope.display = 'info';
	
	function init() {
		$scope.getWorkspaceSummaryInfo();
		//$scope.getWorkspaceMemberList($scope.workspaceId);
	}
	
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
			result.push({userName: 'name'});
			result.push({userName: 'name'});
			result.push({userName: 'name'});
			result.push({userName: 'name'});
			result.push({userName: 'name'});
			result.push({userName: 'name'});
			result.push({userName: 'name'});
			result.push({userName: 'name'});
			result.push({userName: 'name'});
			result.push({userName: 'name'});
			result.push({userName: 'name'});
			result.push({userName: 'name'});
			result.push({userName: 'name'});
			result.push({userName: 'name'});
			result.push({userName: 'name'});
			result.push({userName: 'name'});
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
  
  $scope.toggleAll = function(selectAll, selectedItems) {
  	toggleAll(selectAll, selectedItems);
  };
  
  $scope.toggleOne = function (selectedItems) {
  	toggleOne($scope.selectAll, selectedItems);
  };
  
  function toggleAll(selectAll, selectedItems) {
  	for (var id in selectedItems) {
      if (selectedItems.hasOwnProperty(id)) {
        selectedItems[id] = selectAll;
      }
    }
  }
  
  function toggleOne(selectAll, selectedItems) {
  	for (var id in selectedItems) {
      if (selectedItems.hasOwnProperty(id)) {
        if(!selectedItems[id]) {
        	selectAll = false;
          return;
        }
      }
    }
    selectAll = true;
  }
  
  init();	
});
// / @endcond
