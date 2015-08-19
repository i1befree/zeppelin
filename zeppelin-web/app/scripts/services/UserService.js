'use strict';

angular.module('zeppelinWebApp').factory('UserService', function(UtilService) {
  return {
    // 사용자 목록조회
    getUserList : function(requestData) {
      return UtilService.httpPost('/user/getList', requestData);
    },

    // 사용자 입력,수정,삭제 - 관리자
    save : function(uri, requestData) {
      return UtilService.httpPost(uri, requestData);
    },

    // 사용자 수정 - 본인
    update : function(requestData) {
      return UtilService.httpPost('/user/update', requestData);
    },

    // 사용자 정보 조회
    getUserInfo : function(requestData) {
      return UtilService.httpPost('/user/getInfo', requestData);
    }

  };
});
