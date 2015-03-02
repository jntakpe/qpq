qpqApp.config(function ($stateProvider) {
    "use strict";

    $stateProvider
        .state('admin', {
            abstract: true,
            data: {
                roles: ['ROLE_ADMIN']
            },
            parent: 'site'
        });
});
