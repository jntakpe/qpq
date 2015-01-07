var homeApp = angular.module('homeApp', []),
    qpqApp = angular.module('qpqApp', ['ngMessages', 'ngRoute', 'ngResource', 'ngMaterial', 'homeApp']);

qpqApp.config(function ($routeProvider) {
    "use strict";
    $routeProvider
        .when('/', {
            controller: 'HomeController as home',
            templateUrl: 'views/home.html'
        })
        .otherwise({
            controller: 'HomeController as home',
            templateUrl: 'views/home.html'
        })
});

