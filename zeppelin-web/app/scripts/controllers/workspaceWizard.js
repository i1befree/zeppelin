/**
 * @ngdoc function
 * @name zeppelinWebApp.controller:WorkspaceWizardCtrl
 * @description # WorkspaceWizardCtrl
 * 
 * @author 
 */
'use strict';

angular.module('zeppelinWebApp').controller('WorkspaceWizardCtrl', function($scope, $route, $routeParams, $location) {

	console.info('$routeParams.workspaceId', $routeParams.workspaceId);
	$scope.workspaceId = $routeParams.workspaceId;
	
	$scope.display = 'info';
	
});
// / @endcond
