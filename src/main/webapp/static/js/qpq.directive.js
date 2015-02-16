qpqApp
    .directive('compareToValidator', compareTo)
    .directive('availableValidator', available)
    .directive('autofocus', autofocus);

function compareTo() {
    "use strict";

    return {
        require: 'ngModel',
        scope: {
            other: '=compareToValidator'
        },
        link: function ($scope, element, attributes, ngModel) {
            ngModel.$validators.compareTo = function (modelValue) {
                return modelValue === $scope.other;
            };

            $scope.$watch('other', function () {
                ngModel.$validate();
            });
        }
    };
}

function available($http) {
    return {
        require: 'ngModel',
        scope: {
            url: '@availableValidator'
        },
        link: function ($scope, elements, attributes, ngModel) {
            ngModel.$asyncValidators.available = function (value) {
                return $http.get($scope.url + "?value=" + value);
            };
        }
    };
}

function autofocus($timeout) {
    return {
        restrict: 'A',
        link: function ($scope, element) {
            $timeout(function () {
                element[0].focus();
            })
        }
    }
}