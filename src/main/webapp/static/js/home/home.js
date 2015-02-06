qpqApp.config(function ($stateProvider) {
    $stateProvider
        .state('home', {
            parent: 'site',
            url: '/',
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