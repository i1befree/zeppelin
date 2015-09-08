/**
 * @ngdoc function
 * @name zeppelinWebApp.controller:DatastoreWizardCtrl
 * @description # DatastoreWizardCtrl
 * 
 * @author 
 */
'use strict';

angular.module('zeppelinWebApp').controller('DatastoreWizardCtrl', function($scope, $route, $routeParams, $location) {

	console.info('$routeParams.datastoreId', $routeParams.datastoreId);
	
	$scope.cancel = function() {
		$location.path('/datastore');
	}
});
// / @endcond
