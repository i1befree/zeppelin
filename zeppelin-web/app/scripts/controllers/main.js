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
 * @name zeppelinWebApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the zeppelinWebApp
 *
 * @author anthonycorbacho
 */
angular.module('zeppelinWebApp')
        .controller('MainCtrl', function($scope, WebSocket, $rootScope, $window, UtilService, Authentication) {
  $rootScope.compiledScope = $scope.$new(true, $rootScope);  
  $scope.WebSocketWaitingList = [];
  $scope.connected = false;
  $scope.looknfeel = 'default';

  var init = function() {
    $scope.asIframe = (($window.location.href.indexOf('asIframe') > -1) ? true : false);
  };
  init();

  /**
   * Web socket
   */
  WebSocket.onopen(function() {
    console.log('Websocket created');
    $scope.connected = true;
    if ($scope.WebSocketWaitingList.length > 0) {
      for (var o in $scope.WebSocketWaitingList) {
        WebSocket.send(JSON.stringify($scope.WebSocketWaitingList[o]));
      }
    }
    setInterval(function(){
        $rootScope.$emit('sendNewEvent', {op: 'PING'})
      }
      ,60000);    
  });

  WebSocket.onmessage(function(event) {
    var payload;
    if (event.data) {
      payload = angular.fromJson(event.data);
    }
    console.log('Receive << %o, %o, %o', payload.op, payload, $scope);
    var op = payload.op;
    var data = payload.data;
    if (op === 'NOTE') {
      $scope.$broadcast('setNoteContent', data.note);
    } else if (op === 'NOTES_INFO') {
      $scope.$broadcast('setNoteMenu', data.notes);
    } else if (op === 'PARAGRAPH') {
      $scope.$broadcast('updateParagraph', data);
    } else if (op === 'PROGRESS') {
      $scope.$broadcast('updateProgress', data);
    } else if (op === 'COMPLETION_LIST') {
      $scope.$broadcast('completionList', data);
    } else if (op === 'ANGULAR_OBJECT_UPDATE') {
      $scope.$broadcast('angularObjectUpdate', data);
    }
  });

  WebSocket.onerror(function(event) {
    console.log('error message: ', event);
    $scope.connected = false;
  });

  WebSocket.onclose(function(event) {
    console.log('close message: ', event);
    $scope.connected = false;
  });

  /** Send info to the websocket server */
  var send = function(data) {
    if (WebSocket.currentState() !== 'OPEN') {
      $scope.WebSocketWaitingList.push(data);
    } else {
      console.log('Send >> %o, %o', data.op, data);
      WebSocket.send(JSON.stringify(data));
    }
  };


  /** get the childs event and sebd to the websocket server */
  $rootScope.$on('sendNewEvent', function(event, data) {
    if (!event.defaultPrevented) {
      send(data);
      event.preventDefault();
    }
  });

  $rootScope.$on('setIframe', function(event, data) {
    if (!event.defaultPrevented) {
      $scope.asIframe = data;
      event.preventDefault();
    }
  });

  $rootScope.$on('setLookAndFeel', function(event, data) {
    if (!event.defaultPrevented && data && data !== '') {
      $scope.looknfeel = data;
      event.preventDefault();
    }
  });

  $rootScope.$on('getTreeWorkspace', function(event, data) {
    console.info('getTreeWorkspace', event, data);

    //test
    $scope.mainTreeData = [{"start":null,"end":null,"query":null,"beginRowNum":0,"rowsPerPage":0,"totalCount":0,"workspaceId":"15634a90-0e8f-44e4-98e1-550be090a210","name":"Personal","type":"P","pId":"ROOT","userId":null,"createDate":null,"updateDate":null,"nodes":[{"start":null,"end":null,"query":null,"beginRowNum":0,"rowsPerPage":0,"totalCount":0,"workspaceId":"74f83e34-44c3-11e5-bb39-063b17d52e29","name":"DEFAULT","type":"P","pId":"15634a90-0e8f-44e4-98e1-550be090a210","userId":"bestmenbal","createDate":null,"updateDate":null,"nodes":[]}]},{"start":null,"end":null,"query":null,"beginRowNum":0,"rowsPerPage":0,"totalCount":0,"workspaceId":"f5697b8a-6fe0-4d0e-be26-3472b3c8e2eb","name":"Shared","type":"S","pId":"ROOT","userId":null,"createDate":null,"updateDate":null,"nodes":[]},{"start":null,"end":null,"query":null,"beginRowNum":0,"rowsPerPage":0,"totalCount":0,"workspaceId":"79efbe48-8205-4a1c-91e3-051bb246b468","name":"Global","type":"G","pId":"ROOT","userId":null,"createDate":null,"updateDate":null,"nodes":[]}];
    $scope.$broadcast('setWorkspaceMenu', angular.copy($scope.mainTreeData));
    
    
//    UtilService.httpPost('/workspace/getList', {}).then(function(result) {
//      $scope.mainTreeData = UtilService.unflatten(result);
//      $scope.$broadcast('setWorkspaceMenu', angular.copy($scope.mainTreeData));
//    }, function(error) {
//      alert(error);
//    });
  });

});
