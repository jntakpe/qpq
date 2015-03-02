qpqApp.config(function ($stateProvider) {
    "use strict";

    $stateProvider
        .state('settings', {
            parent: 'site',
            abstract: 'true',
            data: {
                roles: ['ROLE_USER']
            },
            views: {
                'content@': {
                    templateUrl: 'views/settings.html',
                    controller: 'SettingsController as settings'
                }
            }
        });
});
