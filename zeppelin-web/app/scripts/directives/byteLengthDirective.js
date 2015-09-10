'use strict';

angular.module('zeppelinWebApp').directive('byteMaxlength', function() {
  return {
    require : 'ngModel',
    restrict : 'A',
    scope : {
      byteMaxlength : '='
    },
    link : function(scope, elem, attrs, ctrl) {
      scope.$watch(function() {
        var returnValue = (ctrl.$pristine && angular.isUndefined(ctrl.$modelValue));
        var value = ctrl.$modelValue;
        if (angular.isUndefined(value) || value == null || value == '' || typeof (value) != 'string') {
          return true;
        }
        // 두 필드 모두 값이 없으면 정상
        var byteLength = value.replace(/[^\x00-\xff]/g, 'xx').length;
        if (byteLength > scope.byteMaxlength) {
          returnValue = false;
        } else {
          returnValue = true;
        }
        return returnValue;
      }, function(currentValue) {
        ctrl.$setValidity('byteMaxlength', currentValue);
      });
    }
  };
});
