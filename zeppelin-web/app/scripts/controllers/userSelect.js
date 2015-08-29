/**
 * @ngdoc function
 * @name zeppelinWebApp.controller:UserSelectCtrl
 * @description # UserSelectCtrl
 * 
 * @author 
 */
'use strict';

angular.module('zeppelinWebApp').controller('UserSelectCtrl', function($scope, $rootScope, $route, $routeParams, $location, UserService) {

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
      $scope.memberList = result;
      angular.forEach($scope.memberList, function(item, index) {
  			$scope.selected[item.userId] = false;
  		});
    }, function(error) {
    	alert(error);
    });
  };
  
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
  
});
// / @endcond
