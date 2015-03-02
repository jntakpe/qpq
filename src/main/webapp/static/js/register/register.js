qpqApp.config(function ($stateProvider) {
    "use strict";

    $stateProvider
        .state('register', {
            parent: 'site',
            url: '/register',
            data: {
                roles: []
            },
            views: {
                'content@': {
                    templateUrl: 'views/register.html',
                    controller: 'RegisterController as register'
                }
            }
        });
});
