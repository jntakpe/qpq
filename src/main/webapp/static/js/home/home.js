qpqApp.config(function ($stateProvider) {
    "use strict";

    $stateProvider
        .state('home', {
            parent: 'site',
            url: '/?firstConnexion',
            data: {
                roles: []
            },
            views: {
                'content@': {
                    templateUrl: 'views/home.html',
                    controller: 'HomeController as home'
                }
            }
        });
});