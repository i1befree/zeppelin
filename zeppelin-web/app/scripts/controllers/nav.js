/* global $:false */
/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
'use strict';

/**
 * @ngdoc function
 * @name zeppelinWebApp.controller:NavCtrl
 * @description
 * # NavCtrl
 * Controller of the top navigation, mainly use for the dropdown menu
 *
 * @author anthonycorbacho
 */
angular.module('zeppelinWebApp').controller('NavCtrl', function($scope, $rootScope, $routeParams, $location, Authentication, UtilService) {
  /** Current list of notes (ids) */
  $scope.notes = [];
  $scope.workspaceMenu = [];
  $('#notebook-list').perfectScrollbar({suppressScrollX: true});

  /** Set the new menu */
  $scope.$on('setNoteMenu', function(event, notes) {
      $scope.notes = notes;
  });
  
  $scope.$on('setWorkspaceMenu', function(event, workspaceMenu) {
    $scope.workspaceMenu = workspaceMenu;
    
    $('.menu-tree-icon').on({"click":function(e){
      console.info('dropdown-menu .menu-tree-icon', e);
      e.stopPropagation();
    }});

  });
  
  var loadNotes = function() {
    //$rootScope.$emit('sendNewEvent', {op: 'LIST_NOTES'});
  };
  loadNotes();

  /** Create a new note */
  $scope.createNewNote = function() {
    var workspaceId;
    angular.forEach($scope.workspaceMenu, function(item, index) {
      if(item.type === 'P' && item.pId === 'ROOT') {
        angular.forEach(item.nodes, function(node, i) {
          if(node.name === 'DEFAULT') {
            workspaceId = node.workspaceId;
          }
        });
      }
    });
    console.info('create notebook workspaceId', workspaceId);
    $rootScope.$emit('sendNewEvent', {op: 'NEW_NOTE', data:{userId: Authentication.getId(), workspaceId:workspaceId}});
  };

  /** Check if the note url is equal to the current note */
  $scope.isActive = function(noteId) {
    if ($routeParams.noteId === noteId) {
      return true;
    }
    return false;
  };
  $scope.isActiveWorkspace = function(workspaceId) {
    if ($routeParams.workspaceId === workspaceId) {
      return true;
    }
    return false;
  };

  //로그인처리
  $scope.login = {};
  $scope.loginForm = function(isValid, errElem) {
    if (!isValid) {
      var alertMsg = '아이디와 비밀번호를 입력하세요.';
      switch (errElem.required[0].$name) { // 첫번째 오류만 처리
      case 'id':
        alertMsg = '아이디를 입력하세요.';
        break;
      case 'passwd':
        alertMsg = '비밀번호를 입력하세요.';
        break;
      }
      ;
      alert(alertMsg);
      return;
    }
    $('#loginButton').button('loading');
    var credentials = $scope.login;
    Authentication.login(credentials).then(function(result) {
      if (result.rsCode === 'SUCCESS') {
        $rootScope.$emit('getTreeWorkspace', {});
      } else {
        alert(result.rsMessage);
      }
      $('#loginButton').button('reset');
    }, function(error) {
      console.info(error);
      $('#loginButton').button('reset');
    });    
  };
  
  $scope.logoutForm = function() {
    $('#logoutButton').button('loading');
    Authentication.logout().then(function(result) {
      $('#logoutButton').button('reset');
      $location.path('/');
    }, function(error) {
      console.info(error);
      $('#logoutButton').button('reset');
    });
  };
  
});
