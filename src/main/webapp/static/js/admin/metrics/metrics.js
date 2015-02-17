qpqApp.config(function ($stateProvider) {
    $stateProvider.state('metrics', {
        parent: 'admin',
        url: '/metrics',
        data: {
            roles: ['ROLE_ADMIN']
        },
        views: {
            'content@': {
                templateUrl: 'views/metrics.html',
                controller: 'MetricsController as metrics'
            }
        }
    });
});