qpqApp.config(function ($stateProvider) {
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
