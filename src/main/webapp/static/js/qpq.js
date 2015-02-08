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

