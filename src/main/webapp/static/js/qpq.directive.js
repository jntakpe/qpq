qpqApp.directive('compareTo', compareTo);

function compareTo() {
    "use strict";

    return {
        require: 'ngModel',
        scope: {
            other: '=compareTo'
        },
        link: function (scope, element, attributes, ngModel) {
            ngModel.$validators.compareTo = function (modelValue) {
                return modelValue === scope.other;
            };

            scope.$watch('other', function () {
                ngModel.$validate();
            });
        }
    };
}