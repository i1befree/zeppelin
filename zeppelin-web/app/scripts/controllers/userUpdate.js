/**
 * @ngdoc function
 * @name zeppelinWebApp.controller:UserUpdateCtrl
 * @description # UserUpdateCtrl 개인정보변경 - 사용자의 개인정보를 변경한다.
 * 
 * @author 박상민
 */
'use strict';

angular.module('zeppelinWebApp').controller('UserUpdateCtrl', function($scope, $route, $routeParams, $location, $rootScope, $http, UserService, Authentication) {

  var initConfig = function() {
    $scope.userGroupCodeName = [ {
      code : '4',
      desc : '특별 사용자'
    }, {
      code : '3',
      desc : '일반 사용자'
    }, {
      code : '2',
      desc : '고급 사용자'
    }, {
      code : '1',
      desc : '관리 사용자'
    } ];
  };
  var formData = {};

  $scope.update = function() {
    formData = $scope.user;

    UserService.update(formData).then(function(result) {
      if (result.rsCode === 'SUCCESS') {
        $scope.user.passwd = '';
        $scope.user.passwdConfirm = '';
        alert('수정 하였습니다.');
      } else {
        alert(result.rsMessage);
      }
    }, function(error) {
      alert(error);
    });
  };

  var getInfo = function() {
    formData = {
      id : Authentication.getId()
    };

    UserService.getUserInfo(formData).then(function(result) {
      $scope.user = result;
    }, function(error) {
      alert(error);
    });
  };

  var init = function() {
    // when interpreter page opened after seeing non-default looknfeel note, the
    // css remains unchanged. that's what interpreter page want. Force set
    // default looknfeel.
    $rootScope.$emit('setLookAndFeel', 'default');
    initConfig();
    getInfo();
  };

  init();
});
// / @endcond
