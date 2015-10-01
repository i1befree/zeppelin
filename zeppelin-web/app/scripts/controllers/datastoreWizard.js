/**
 * @ngdoc function
 * @name zeppelinWebApp.controller:DatastoreWizardCtrl
 * @description # DatastoreWizardCtrl
 * 
 * @author 
 */
'use strict';

angular.module('zeppelinWebApp').controller('DatastoreWizardCtrl', function($scope, $route, $routeParams, $location, UtilService) {

	$scope.wizardStepCount = 4; //위자드 단계의 총수
  $scope.wizardCurrentStep = 1; //위자드의 현재 단계 번호
  $scope.datastoreTypeList = ['INTERNAL', 'DATABASE'];
  $scope.datastoreSubTypeList = [{db: 'MYSQL'  , driver:'com.mysql.jdbc.Driver'}, 
                                 {db: 'MSSQL'  , driver:'com.microsoft.jdbc.sqlserver.SQLServerDriver'},
                                 {db: 'ORACLE' , driver:'oracle.jdbc.driver.OracleDriver'}];
  
  $scope.schema = {};
  $scope.datastore = {properties:{}};
  $scope.isOkTestConnection = false;
  $scope.messageTestConnection = '접속 테스트 중입니다.';
  
	function init() {
		$scope.datastore.type = $scope.datastoreTypeList[0];
		$scope.datastore.subType = $scope.datastoreSubTypeList[0].db;
	}
		
	$scope.cancel = function() {
		$location.path('/datastore');
	};
	
	function testConnection() {
		$scope.isOkTestConnection = false;
		$scope.messageTestConnection = '접속 테스트 중입니다.';
	  UtilService.httpPost('/datastore/testConnection', $scope.datastore).then(function(result) {
  		$scope.isOkTestConnection = true;
  		$scope.messageTestConnection = '접속 테스트를 성공하였습니다.';
  	  
  	}, function(error) {
  		$scope.messageTestConnection = '접속 테스트를 실패하였습니다. 접속 정보를 확인해주세요.';
  	  alert(error.rsMessage);
  	});
  };
  
	
	$scope.previous = function() {
		switch($scope.wizardCurrentStep) {
			case 1:
				break;
			case 2:
				break;
			case 3:
				$scope.isOkTestConnection = false;
				break;
			case 4:
				break;	
		}
    $scope.wizardCurrentStep = $scope.wizardCurrentStep - 1;
	};
	
	$scope.next = function() {
		switch($scope.wizardCurrentStep) {
			case 1: 
				if($scope.datastore.type === 'INTERNAL') {
					delete $scope.datastore.properties.DRIVER_CLASS;
					delete $scope.datastore.properties.URL;
					$scope.datastore.properties.CLUSTER_NAME = {value : 'cep'};
				} else if($scope.datastore.type === 'DATABASE') {
					delete $scope.datastore.properties.CLUSTER_NAME;
				}
				break;
			case 2:
				if($scope.form.name.$invalid
				|| $scope.form.hostName.$invalid
				|| $scope.form.portNum.$invalid
				|| $scope.form.description.$invalid) {
					return;
				}
				if($scope.datastore.type === 'INTERNAL') {
					
				} else if($scope.datastore.type === 'DATABASE') {
					if($scope.form.username.$invalid
					|| $scope.form.password.$invalid
					|| $scope.form.subtype.$invalid) {
						return;
					}
				} else {
					return;
				}
				testConnection();
				break;
			case 3:
				if(!$scope.isOkTestConnection) {
					return;
				}
				break;
			case 4:
				break;	
		}
		$scope.wizardCurrentStep = $scope.wizardCurrentStep + 1;
	};
	
	$scope.complete = function() {
    if(confirm('완료 하시겠습니까?')) {
    	var jsonData = angular.copy($scope.datastore);
    	UtilService.httpPost('/datastore/create', jsonData).then(function(result) {
    		if (result.rsCode === 'SUCCESS') {
    			$location.path('/datastore');
        } else {
          alert(result.rsMessage);
        }
    	}, function(error) {
    		alert(error);
    	});
    }
	};
	
	$scope.$watch('datastore', function (newValue, oldValue) {
		if(newValue.type === undefined || newValue.type !== 'DATABASE') {
    	return;
    }
    if(newValue.subType !== undefined) {
    	switch(newValue.subType) {
    	case 'MYSQL' :
    		$scope.datastore.properties.DRIVER_CLASS = {value : $scope.datastoreSubTypeList[0].driver};
    		$scope.datastore.properties.URL = {value : "jdbc:mysql://" + $scope.datastore.hostName + ":" + $scope.datastore.portNum + "/?useInformationSchema=true&useUnicode=true&characterEncoding=utf8"};
    		break;
    	case 'MSSQL' :
    		$scope.datastore.properties.DRIVER_CLASS = {value : $scope.datastoreSubTypeList[1].driver};
    		$scope.datastore.properties.URL = {value : "jdbc:microsoft:sqlserver:" + $scope.datastore.hostName + ":" + $scope.datastore.portNum};
    		break;
    	case 'ORACLE' :
    		$scope.datastore.properties.DRIVER_CLASS = {value : $scope.datastoreSubTypeList[2].driver};
    		$scope.datastore.properties.URL = {value : "jdbc:oracle:thin:@" + $scope.datastore.hostName + ":" + $scope.datastore.portNum};
    		break;
      default:	
    	}
    }
    console.info($scope.datastore.properties.DRIVER_CLASS.value, $scope.datastore.properties.URL.value);
  }, true);

	
	init();
});
// / @endcond
