/**
 * @ngdoc function
 * @name zeppelinWebApp.controller:UserMgrCtrl
 * @description # UserMgrCtrl 계정관리 - 사용자 정보 생성,수정,삭제,조회를 관리한다.
 *
 * @author 박상민
 */
'use strict';

angular.module('zeppelinWebApp').controller('UserMgrCtrl', function($scope, $route, $routeParams, $location, $rootScope, $http, UserService) {

  var initConfig = function() {
    $scope.roles = [ ];

    // 목록 페이징 처리 기본값 설정
    $scope.totalCount = 0; // 데이터 총 수
    $scope.currentPage = 1; // 현재 페이지
    $scope.maxPageSize = 10; // 한번에 보여줄 페이지 수
    $scope.itemsPerPage = 10; // 한페이지에서 보여줄 로우수
    $scope.pageChanged = function() {
      $scope.getList();
    };
    $scope.initPaging = function() {
      $scope.currentPage = 1; // 검색조건이 변경되면 페이지는 1로 수정
    };
    $scope.initItemsPerPage = function() {
      $scope.currentPage = 1; // 검색조건이 변경되면 페이지는 1로 수정
      $scope.getList();
    };
  };

  // 사용자 목록 조회
  $scope.getList = function() {
    var formData = {
      beginRowNum : ($scope.currentPage - 1) * $scope.itemsPerPage,
      rowsPerPage : $scope.itemsPerPage
    };
    UserService.getUserList(formData).then(function(result) {
      $scope.list = result;
      $scope.totalCount = result[0] == undefined ? 0 : result[0].totalCount;
    }, function(error) {
      console.info(error);
    });
  };

  $scope.getRole = function() {
    UserService.getRole().then(function(result) {
      $scope.roles = result;
    }, function(error) {
      console.info(error);
    });
  };

  var userFormDefaultValue = {
    id : "",
    name : "",
    passwd : "",
    passwdConfirm : "",
    email : "",
    tel : "",
    userGrpCd : ""
  };

  // 사용자 생성,수정,삭제의 모달창 제어
  // 모달창을 사용자 생성 모드로 설정
  $scope.createMode = function() {
    $scope.mode = 'C';
    $scope.uri = '/user/create';
    $scope.user = angular.copy(userFormDefaultValue);
    $scope.userForm.$setPristine();
  };

  // 모달창을 사용자 정보 보기(수정모드 공유함) 모드시 설정
  $scope.viewMode = function(viewUserInfo) {
    $scope.mode = 'U';
    $scope.uri = '/user/update';
    $scope.user = angular.copy(viewUserInfo);
    $location.href = '#myModal';
  };
  // 모달창 닫을때 처리
  angular.element('#myModal').on('hidden.bs.modal', function(e) {
    $scope.mode = '';
    $scope.user = angular.copy(userFormDefaultValue);
    $scope.userForm.$setPristine();
  });

  // 사용자 삭제 이벤트 처리
  $scope.deleteAction = function(viewUserInfo) {
    if (!confirm('삭제 하시겠습니까?')) {
      return false;
    }
    $scope.mode = 'D';
    $scope.uri = '/user/delete';
    $scope.save();
  };

  var formData = {};
  var uri;
  // 사용자 생성,수정,삭제 처리
  $scope.save = function() {
    console.log("posting data....");
    formData = $scope.user;
    uri = $scope.uri;

    UserService.save(uri, formData).then(function(result) {
      var nameMap = {
        'C' : '등록',
        'U' : '수정',
        'D' : '삭제'
      };
      if (result.rsCode === 'SUCCESS') {
        alert(nameMap[$scope.mode] + ' 하였습니다.');
        angular.element('#myModal').modal('hide');
        $scope.mode = '';
        $scope.uri = '';
        $scope.user = angular.copy(userFormDefaultValue);
        $scope.userForm.$setPristine();
        $scope.getList();
      } else {
        alert('[' + nameMap[$scope.mode] + '] ' + result.rsMessage);
      }
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
    $scope.getList();
    $scope.getRole();
  };

  init();
});
