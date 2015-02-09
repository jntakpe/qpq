qpqApp.config(function ($stateProvider) {
    $stateProvider
        .state('docs', {
            url: '/docs',
            data: {
                roles: ['ROLE_ADMIN']
            },
            views: {
                'content@': {
                    templateUrl: 'views/docs.html'
                }
            }
        });
});
