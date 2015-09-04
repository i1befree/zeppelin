'use strict';

angular.module('zeppelinWebApp').factory('Authentication', function Authentication(UtilService, $q, $http, $timeout) {

  var authenticatedUser = null;

  return {
    requestUser : function() {
      var uri = '/getUserSession';
      var promise = UtilService.httpGet(uri).then(function(data) {
        $timeout(function() {
          // Check if user is defined first
          if (data == undefined || data == null || data == '') {
            authenticatedUser = null;            
          } else {
            authenticatedUser = data;
          }
        }, 10);
        return data;
      }, function(error) {
        authenticatedUser = null;
        return error;
      });
      return promise;
    },

    getUser : function() {
      return authenticatedUser;
    },

    exists : function() {
      return authenticatedUser != null;
    },

    login : function(credentials) {
      var uri = '/login';
      var promise = UtilService.httpPost(uri, credentials).then(function(data) {
        if (data.rsCode === 'SUCCESS') {
          authenticatedUser = data.object;
        }
        return data;
      });
      return promise;
    },
    
    logout : function() {
      var uri = '/logout';
      var promise = UtilService.httpPost(uri).then(function(data) {
        if (data.rsCode === 'SUCCESS') {
          authenticatedUser = null;
        }
        return data;
      });
      return promise;
    },

    isAdmin : function() {
      return this.exists() && authenticatedUser.userGrpCd === '1';
    },
    
    isWorkspaceAdmin : function() {
      return this.exists() && authenticatedUser.userGrpCd === '2';
    },
    
    
    getPropertyValue : function(property) {
      var value = null;
      if(this.exists()) {
        value = authenticatedUser[property];
      }
      return value;
    },
    getId : function() {
      return this.getPropertyValue('id');
    },
    getName : function() {
      return this.getPropertyValue('name');
    },
    getGrpCd : function() {
      return this.getPropertyValue('userGrpCd');
    }
  }
});

