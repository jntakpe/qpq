var homeApp = angular.module('homeApp', []),
    barsApp = angular.module('barsApp', []),
    qpqApp = angular.module('qpqApp', [
        'ngMessages',
        'ngRoute',
        'ngResource',
        'ngMaterial',
        'homeApp',
        'barsApp'
    ]);

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

