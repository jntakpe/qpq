var homeApp = angular.module('homeApp', []),
    qpqApp = angular.module('qpqApp', [
        'ngMessages',
        'ngRoute',
        'ngResource',
        'homeApp'
    ]);

qpqApp.config(function ($routeProvider) {
    "use strict";
    $routeProvider
        .when('/someuri', {
            controller: 'SomeController as some',
            templateUrl: 'views/some-page.html'
        })
        .otherwise({
            controller: 'HomeController as home',
            templateUrl: 'views/home.html'
        })
});

