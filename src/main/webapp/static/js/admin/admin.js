qpqApp.config(function ($stateProvider) {
    $stateProvider
        .state('admin', {
            abstract: true,
            data: {
                roles: ['ROLE_ADMIN']
            },
            parent: 'site'
        });
});
