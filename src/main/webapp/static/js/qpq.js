var homeApp = angular.module('homeApp', []),
    qpqApp = angular.module('qpqApp', ['ngMessages', 'ui.router', 'ngResource', 'homeApp', 'authApp']);

qpqApp.config(function ($stateProvider, $urlRouterProvider, $httpProvider) {
    "use strict";

    $urlRouterProvider.otherwise('/');

    $stateProvider.state('site', {
        abstract: true,
        views: {
            'navbar@': {
                templateUrl: 'views/navbar.html',
                controller: 'NavbarController as navbar'
            }
        },
        resolve: {
            authorize: ['Auth',
                function (Auth) {
                    return Auth.authorize();
                }]
        }
    });

    $httpProvider.interceptors.push('AuthInterceptor');

});

