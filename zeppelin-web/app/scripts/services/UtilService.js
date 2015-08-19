'use strict';

angular.module('zeppelinWebApp').factory('UtilService', function($http, $q, $filter) {
  var IS_DEBUG = true;
  var LINE_START = "########## START OF REQUEST ##########";
  var LINE_END   = "########## END   OF REQUEST ##########";

  function promiseHttp(method, uri, requestData) {
    var startTime; 
    if (IS_DEBUG) {
      startTime = new Date();
      console.log("");
      console.log(LINE_START);
      console.log("#", method, "Uri : " + getReqBaseUrl() + uri);
      if(requestData) console.log("# RequestData : " + JSON.stringify(requestData));
    }      
    var asyncJob = $q.defer();
    $http({
      url: getReqBaseUrl() + uri,
      method: method,
      data: requestData
    }).success(function(data, status, headers, config) { 
      if (IS_DEBUG) {
        console.log("# SUCCESS status : [", status, "]");
        console.log("# data", data);
        console.log(LINE_END, $filter('number')(new Date() - startTime), " ms");
      }
      asyncJob.resolve(data);
    }).error(function(data, status, headers, config) {
      if (IS_DEBUG) {
        console.log("# ERROR status : [", status, "]");
        console.log(data);
        console.log(LINE_END, $filter('number')(new Date() - startTime), " ms");
      }
      asyncJob.reject(data);
    });
    return asyncJob.promise;
  }
  
  return {
    isUndefined : function(value) {
      return (value == undefined);
    },

    isOrNullUndefined : function(value) {
      return this.isUndefined(value) || (value == null);
    },

    isOrNullUndefinedEmpty : function isOrNullUndefinedEmpty(value) {
      return this.isOrNullUndefined(value) || (value == "");
    },
    
    httpPost : function(uri, requestData) {
      return promiseHttp('POST', uri, requestData);
    },

    httpGet : function(uri) {
      return promiseHttp('GET', uri);
    }

  };

});
