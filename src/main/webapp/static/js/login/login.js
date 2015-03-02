qpqApp.config(function ($stateProvider) {
    "use strict";

    $stateProvider
        .state('login', {
            parent: 'site',
            url: '/login',
            data: {
                roles: []
            },
            views: {
                'content@': {
                    templateUrl: 'views/login.html',
                    controller: 'LoginController as login'
                }
            }
        });
});
