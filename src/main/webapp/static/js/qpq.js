var homeApp = angular.module('homeApp', []),
    qpqApp = angular.module('qpqApp', ['ngMessages', 'ngRoute', 'ngResource', 'homeApp']);

qpqApp.config(function ($routeProvider) {
    "use strict";
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

