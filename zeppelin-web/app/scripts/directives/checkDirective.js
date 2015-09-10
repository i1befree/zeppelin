'use strict';

angular.module('zeppelinWebApp').directive('checkUnique', function() {
  return {
    require : 'ngModel',
    restrict : 'A',
    link : function(scope, elm, attr, ctrl) {
      if (!ctrl)
        return;
      attr.rulePassword = true; // force truthy in case we are on
      // non input element
      var validator = function(value) {
        value = String(value);
        var formData = {
          checkValue : ctrl.$viewValue
        };

        if (angular.isUndefined(value) || value == null || value == '') {
          ctrl.$setValidity('checkUnique', true);
        }

        if (formData.checkValue && scope.mode != '') {
          CheckService.isUnique(formData).then(function(result) {
            if (result == "true") {
              ctrl.$setValidity('checkUnique', true);
            } else {
              ctrl.$setValidity('checkUnique', false);
            }
          });
        }

        return value;
      };

      ctrl.$formatters.push(validator);
      ctrl.$parsers.unshift(validator);

      attr.$observe('checkUnique', function() {
        validator(ctrl.$viewValue);
      });
    }
  };
});
