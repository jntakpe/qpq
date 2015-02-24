qpqApp.config(function ($stateProvider) {
    $stateProvider.state('profile', {
        parent: 'settings',
        url: '/profile',
        resolve: {
            profile: function (Account) {
                return Account.account.get();
            }
        },
        views: {
            'settings': {
                templateUrl: 'views/profile.html',
                controller: 'ProfileController as profile'
            }
        }
    });
});