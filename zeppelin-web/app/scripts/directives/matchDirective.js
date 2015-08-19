'use strict';

angular.module('zeppelinWebApp').directive('match', function() {
  return {
    require : 'ngModel',
    restrict : 'A',
    scope : {
      match : '='
    },
    link : function(scope, elem, attrs, ctrl) {
      scope.$watch(function() {
        var returnValue = (ctrl.$pristine && angular.isUndefined(ctrl.$modelValue)) || scope.match === ctrl.$modelValue;

        // 두 필드 모두 값이 없으면 정상
        if ((angular.isUndefined(scope.match) || scope.match == null || scope.match.length == 0) && (angular.isUndefined(ctrl.$modelValue) || ctrl.$modelValue == null || ctrl.$modelValue.length == 0)) {
          returnValue = true;
        }
        // 오리지날 대상에 값이 있는경우 비교
        if (!angular.isUndefined(scope.match) && scope.match != null && scope.match.length > 0) {
          if (angular.isUndefined(ctrl.$modelValue) || ctrl.$modelValue == null || ctrl.$modelValue.length == 0) {
            returnValue = false;
          }
        }
        return returnValue;
      }, function(currentValue) {
        ctrl.$setValidity('match', currentValue);
      });
    }
  };
});
