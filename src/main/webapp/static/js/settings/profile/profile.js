qpqApp.config(function ($stateProvider) {
    "use strict";

    $stateProvider.state('profile', {
        parent: 'settings',
        url: '/profile',
        views: {
            'settings': {
                templateUrl: 'views/profile.html',
                controller: 'ProfileController as profile'
            }
        }
    });
});