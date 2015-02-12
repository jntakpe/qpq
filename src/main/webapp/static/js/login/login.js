qpqApp.config(function ($stateProvider) {
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
