qpqApp.config(function ($stateProvider) {
    $stateProvider
        .state('docs', {
            url: '/docs',
            parent: 'admin',
            views: {
                'content@': {
                    templateUrl: 'views/docs.html'
                }
            }
        });
});
