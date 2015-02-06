var homeApp = angular.module('homeApp', []),
    qpqApp = angular.module('qpqApp', ['ngMessages', 'ngRoute', 'ngResource', 'homeApp', 'authApp']);

qpqApp.config(function ($routeProvider, $httpProvider) {
    "use strict";
    $httpProvider.interceptors.push('AuthInterceptor');
    $routeProvider
        .when('/some', {
            controller: 'SomeController as some',
            templateUrl: 'views/some-view.html'
        })
        .otherwise({
            controller: 'HomeController as home',
            templateUrl: 'views/home.html'
        });
});

