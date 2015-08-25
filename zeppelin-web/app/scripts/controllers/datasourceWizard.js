/**
 * @ngdoc function
 * @name zeppelinWebApp.controller:DatasourceWizardCtrl
 * @description # DatasourceWizardCtrl
 * 
 * @author 
 */
'use strict';

angular.module('zeppelinWebApp').controller('DatasourceWizardCtrl', function($scope, $route, $routeParams, $location) {

	console.info('$routeParams.datasourceId', $routeParams.datasourceId);
	
	$scope.cancel = function() {
		$location.path('/datasource');
	}
});
// / @endcond
