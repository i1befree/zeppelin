/**
 * @ngdoc function
 * @name zeppelinWebApp.controller:DatasourceWizardCtrl
 * @description # DatasourceWizardCtrl
 * 
 * @author 
 */
'use strict';

angular.module('zeppelinWebApp').controller('DatasourceWizardCtrl', function($scope, $route, $routeParams, $location) {

	$scope.wizardStepCount = 4; //위자드 단계의 총수
  $scope.wizardCurrentStep = 1; //위자드의 현재 단계 번호
  
  $scope.datastore = {};
  $scope.datasource = {};
  $scope.datastoreList = [
    {datstoreId: '123', datstoreName:'인터널 ES', datstoreType:'Internal'},                      
    {datstoreId: '124', datstoreName:'데이타베이스', datstoreType:'RDB', datstoreSubtype:'MYSQL'}
  ];
  
  $scope.selectDatastore = function() {
  	angular.forEach($scope.datastoreList, function(item, index) {
  		console.info(item.datstoreId , $scope.datasource.datstoreId);
  		if(item.datstoreId === $scope.datasource.datstoreId) {
  			$scope.datastore = item;
  		}
  	});
  	console.info($scope.datastore);
  };
  
	$scope.cancel = function() {
		$location.path('/datasource');
	};
	
	$scope.previous = function() {
    $scope.wizardCurrentStep = $scope.wizardCurrentStep - 1;
	};
	
	$scope.next = function() {
		console.info('$scope.datastore.datstoreType', $scope.datastore.datstoreType);
		switch($scope.wizardCurrentStep) {
			case 1: 

				break;
		}
		$scope.wizardCurrentStep = $scope.wizardCurrentStep + 1;
	};
	
	$scope.complete = function() {
    if(!confirm('완료 하시겠습니까?')) {
    	return;
    }
    
    $location.path('/datasource');

	};

});
// / @endcond
