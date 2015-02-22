qpqApp.config(function ($stateProvider) {
    $stateProvider
        .state('profile', {
            parent: 'site',
            url: '/profile',
            data: {
                roles: ['ROLE_USER']
            },
            views: {
                'content@': {
                    templateUrl: 'views/profile.html',
                    controller: 'ProfileController as profile'
                }
            }
        });
});
