var homeApp = angular.module('homeApp', []),
    barsApp = angular.module('barsApp', []),
    fastSplitApp = angular.module('fastSplitApp', []),
    qpqApp = angular.module('qpqApp', [
        'ngMessages',
        'ngRoute',
        'ngResource',
        'ngMaterial',
        'homeApp',
        'barsApp',
        'fastSplitApp'
    ]);

qpqApp.config(function ($routeProvider) {
    "use strict";
    $routeProvider
        .when('/fastsplit', {
            controller: 'FastSplitController as fastSplit',
            templateUrl: 'views/fast-split.html',
            title: 'Some title'
        })
        .otherwise({
            controller: 'HomeController as home',
            templateUrl: 'views/home.html',
            title: 'Gérer ces dépenses entre amis'
        })
}).run(['$rootScope', function ($rootScope) {
    "use strict";
    $rootScope.$on('$routeChangeSuccess', function (event, current) {
        console.log(current);
        $rootScope.title = current.$$route.title || 'Gérer ces dépenses entre amis';
    });
}]);

