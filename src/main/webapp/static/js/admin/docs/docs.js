qpqApp.config(function ($stateProvider) {
    "use strict";

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
