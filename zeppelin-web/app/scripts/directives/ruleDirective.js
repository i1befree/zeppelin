'use strict';

angular.module('zeppelinWebApp').directive('rulePassword', function() {
  return {
    require : 'ngModel',
    restrict : 'A',
    link : function(scope, elm, attr, ctrl) {
      if (!ctrl)
        return;
      attr.rulePassword = true; // force truthy in case we are on
      // non input element
      var validator = function(value) {
        if (angular.isUndefined(value) || value == null || value == '') {
          ctrl.$setValidity('rulePassword', true);
          return;
        }
        if (/[0-9]/g.test(value) && /[a-zA-Z]/g.test(value) && /[_\!\@\#\$\%\^\&\*\-\.]/g.test(value)) {
          ctrl.$setValidity('rulePassword', true);
          return value;
        } else {
          ctrl.$setValidity('rulePassword', false);
          return;
        }
      };

      ctrl.$formatters.push(validator);
      ctrl.$parsers.unshift(validator);

      attr.$observe('rulePassword', function() {
        validator(ctrl.$viewValue);
      });
    }
  };
});
