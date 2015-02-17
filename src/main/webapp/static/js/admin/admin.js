qpqApp.config(function ($stateProvider) {
    $stateProvider
        .state('admin', {
            abstract: true,
            parent: 'site'
        });
});
