var homeApp = angular.module('homeApp', []),
    qpqApp = angular.module('qpqApp', ['ngMessages', 'ui.router', 'ui.bootstrap', 'ngResource', 'homeApp', 'authApp']);

qpqApp.config(function ($stateProvider, $urlRouterProvider, $httpProvider) {
    "use strict";

    $urlRouterProvider.otherwise('/');

    $stateProvider.state('site', {
        abstract: true,
        views: {
            'header@': {
                templateUrl: 'views/header.html',
                controller: 'HeaderController as header'
            }
        },
        resolve: {
            authorize: ['Auth', function (Auth) {
                return Auth.authorize();
            }]
        }
    });

    qpqApp.config(function ($stateProvider) {
        $stateProvider
            .state('admin', {
                abstract: true,
                parent: 'site'
            });
    });

    $httpProvider.interceptors.push('AuthInterceptor');

});

qpqApp.run(function ($rootScope, $state, Auth, Principal) {
    "use strict";

    $rootScope.$on('$stateChangeStart', function (event, toState, toStateParams) {
        $rootScope.toState = toState;
        $rootScope.toStateParams = toStateParams;
        if (Principal.isIdentityResolved()) {
            Auth.authorize();
        }
    });

    $rootScope.$on('$stateChangeSuccess', function (event, toState, toParams, fromState, fromParams) {
        $rootScope.previousStateName = fromState.name;
        $rootScope.previousStateParams = fromParams;
    });

    $rootScope.back = function () {
        if ($rootScope.previousStateName === 'activate' || $state.get($rootScope.previousStateName) === null) {
            $state.go('home');
        } else {
            $state.go($rootScope.previousStateName, $rootScope.previousStateParams);
        }
    };
});

