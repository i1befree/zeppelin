/**
 * @ngdoc function
 * @name zeppelinWebApp.controller:WorkspaceWizardCtrl
 * @description # WorkspaceWizardCtrl
 * 
 * @author 
 */
'use strict';

angular.module('zeppelinWebApp').controller('WorkspaceWizardCtrl', function($scope, $rootScope, $route, $routeParams, $location, UtilService) {

	console.info('$routeParams.workspaceId', $routeParams.workspaceId);
	$scope.workspaceId = $routeParams.workspaceId;
	$scope.workspaceInfo = {};
	$scope.memberList = [];
	$scope.selected = {};
	$scope.gridOptionsForMember = {
		showGridFooter: true,	
		enableRowSelection: true,
		multiSelect : true,
		enableSelectAll: true,
    onRegisterApi : function(gridApi){
    	gridApi.selection.on.rowSelectionChanged($scope,function(row){
    		$scope.selected[row.entity.userId] = row.isSelected;
      });
    	gridApi.selection.on.rowSelectionChangedBatch($scope,function(rows){
    		angular.forEach(rows, function(row, index) {
    			$scope.selected[row.entity.userId] = row.isSelected;
    		});
      });
    },
		columnDefs : [
		  {name:'userName'  , displayName: 'Name', enableColumnMenu: false},
		  {name:'userId'    , displayName: 'ID'  , enableColumnMenu: false},
		  {name:'roleName'  , displayName: 'Role', enableColumnMenu: false}
		]	
	};	
	$scope.display = 'info';
	
	function init() {
		$scope.getWorkspaceSummaryInfo();

		$rootScope.$on('initWorkspaceMemberList', function(event, args) {
			$scope.workspaceInfo = {};
			$scope.memberList = [];
			$scope.selected = {};
			$scope.getWorkspaceMemberList();
		});
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
			$scope.memberList = result;
			$scope.gridOptionsForMember.data = $scope.memberList;
			angular.forEach($scope.memberList, function(item, index) {
  			$scope.selected[item.userId] = false;
  		});
  	}, function(error) {
  		alert(error);
  	});
  };
  
  $scope.showMemberList = function() {
  	$scope.$emit('initUserSelectView', {parentMemberIdsObject: $scope.selected, wrkspcId: $scope.workspaceId});
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
  
  init();	
});
// / @endcond
