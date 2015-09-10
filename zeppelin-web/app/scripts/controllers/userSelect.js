/**
 * @ngdoc function
 * @name zeppelinWebApp.controller:UserSelectCtrl
 * @description # UserSelectCtrl
 * 
 * @author 
 */
'use strict';

angular.module('zeppelinWebApp').controller('UserSelectCtrl', function($scope, $rootScope, $route, $routeParams, $location, UserService, UtilService) {

	$scope.memberList = [];
	$scope.selected = {};
	$scope.gridOptionsForUser = {
		showGridFooter: true,	
		enableRowSelection: true,
		multiSelect : true,
		enableSelectAll: true,
    onRegisterApi : function(gridApi){
    	$scope.gridApi = gridApi;
    	gridApi.selection.on.rowSelectionChanged($scope,function(row){
    		$scope.selected[row.entity.id] = row.isSelected;
      });
    	gridApi.selection.on.rowSelectionChangedBatch($scope,function(rows){
    		angular.forEach(rows, function(row, index) {
    			$scope.selected[row.entity.id] = row.isSelected;
    		});
      });
    },
		columnDefs : [
		  {name:'name'  		, displayName: 'Name', enableColumnMenu: false, cellTooltip: function(row, col) {return row.entity[col.name];}},
		  {name:'id'    		, displayName: 'ID'  , enableColumnMenu: false, cellTooltip: function(row, col) {return row.entity[col.name];}},
		  {name:'userGrpNm' , displayName: 'Role', enableColumnMenu: false, cellTooltip: function(row, col) {return row.entity[col.name];}}
		]	
	};
  
	$scope.display = 'info';
	$scope.parentMemberIdsObject = {};
	$scope.wrkspcId = undefined;

	$rootScope.$on('initUserSelectView', function(event, args) {
		$scope.parentMemberIdsObject = args.parentMemberIdsObject;
		$scope.wrkspcId = args.wrkspcId;
		init();
	});
	
	function init() {
		$scope.getMemberList();
	}
	
  $scope.getMemberList = function() {
  	var formData = {
      beginRowNum : 0,
      rowsPerPage : 10000
    };
    UserService.getUserList(formData).then(function(result) {
    	var notAddedMembers = [];
    	//추가된 user 는 빼고 목록에 추가한다.
    	angular.forEach(result, function(item, index) {
      	if(!$scope.parentMemberIdsObject.hasOwnProperty(item.id)) {
    			this.push(item);
    		}
    	}, notAddedMembers);
      $scope.memberList = notAddedMembers;
      $scope.gridOptionsForUser.data = $scope.memberList;
    	angular.forEach($scope.memberList, function(item, index) {
      	$scope.selected[item.id] = false;
  		});
    }, function(error) {
    	alert(error);
    });
  };
  
  $scope.addMembers = function() {
  	var members = [];
  	for (var id in $scope.selected) {
      if ($scope.selected.hasOwnProperty(id)) {
        if($scope.selected[id] === true) {
        	members.push(id);
        }
      }
    }
  	console.info('members', members);
  	UtilService.httpPost('/workspace/addMembers', {members: members, wrkspcId: $scope.wrkspcId}).then(function(result) {
  		$scope.$emit('initWorkspaceMemberList', {});
    	$scope.close();
  	}, function(error) {
  		alert(error);
  	});
  	
  };
  
  $scope.close = function() {
  	$scope.selectAll = false;
  	$scope.parentMemberIdsObject = {};
		$scope.wrkspcId = undefined;
  	angular.element('#memberModal').modal('hide');
  };
  
  
});
// / @endcond
