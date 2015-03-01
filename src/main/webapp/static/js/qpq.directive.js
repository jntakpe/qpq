qpqApp
    .directive('compareToValidator', compareTo)
    .directive('availableValidator', available)
    .directive('autofocus', autofocus);

function compareTo() {
    "use strict";

    return {
        restrict: 'A',
        require: 'ngModel',
        scope: {
            other: '=compareToValidator'
        },
        link: function ($scope, element, attributes, ngModel) {
            ngModel.$validators.compareTo = function (value) {
                return value === $scope.other;
            };

            $scope.$watch('other', function () {
                ngModel.$validate();
            });
        }
    };
}

function available($http) {
    "use strict";

    return {
        restrict: 'A',
        require: 'ngModel',
        scope: {
            url: '@availableValidator',
            id: '=formId'
        },
        link: function ($scope, elements, attributes, ngModel) {
            ngModel.$asyncValidators.available = function (value) {
                var baseUrl = $scope.url + '?value=' + value;
                if ($scope.id) {
                    baseUrl += '&id=' + $scope.id;
                }
                if (value) {
                    return $http.get(baseUrl);
                }
            };
        }
    };
}

function autofocus($timeout) {
    "use strict";

    return {
        restrict: 'A',
        link: function ($scope, element) {
            $timeout(function () {
                element[0].focus();
            });
        }
    };
}