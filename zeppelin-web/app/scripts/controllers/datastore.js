/**
 * @ngdoc function
 * @name zeppelinWebApp.controller:DatastoreCtrl
 * @description # DatastoreCtrl
 * 
 * @author 
 */
'use strict';

angular.module('zeppelinWebApp').controller('DatastoreCtrl', function($scope, $route, $routeParams, $location) {

  $scope.create = function() {
	  $location.path('/datastoreWizard/:');
  }
});
// / @endcond
