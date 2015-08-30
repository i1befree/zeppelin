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
	$scope.datatableContainerHeight = 360;
	$scope.dtOptions = {
    paging: false,
    searching: true,
    scrollY: $scope.datatableContainerHeight - 95,
    sDom: '<f>rt<i>'
  };
	$scope.selected = {};
	$scope.selectAll = false;
	$scope.display = 'info';
	$scope.parentItemIdsObject = $scope.$parent.selected;
	$scope.parentId = $scope.$parent.getId();
	
	$rootScope.$on('initUserSelectView', function(event, args) {
		console.info('initUserSelectView');
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
      	if(!$scope.parentItemIdsObject.hasOwnProperty(item.id)) {
    			this.push(item);
    		}
    	}, notAddedMembers);
      $scope.memberList = notAddedMembers;
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
  	UtilService.httpPost('/workspace/addMembers', {members: members, wrkspcId: $scope.parentId}).then(function(result) {
  		$scope.$emit('initWorkspaceMemberList', {});
    	$scope.close();
  	}, function(error) {
  		alert(error);
  	});
  	
  };
  
  $scope.close = function() {
  	$scope.selectAll = false;
  	angular.element('#memberModal').modal('hide');
  };
  
  $scope.toggleAll = function(selectAll, selectedItems) {
  	UtilService.toggleAll(selectAll, selectedItems);
  };
  
  $scope.toggleOne = function (selectedItems) {
  	UtilService.toggleOne($scope, selectedItems);
  };
  
  
});
// / @endcond
