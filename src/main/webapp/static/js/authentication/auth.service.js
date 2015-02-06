authApp.factory('Auth', Auth);

function Auth($rootScope, $q, Principal, OAuth, Account, localStorageService) {
    return {
        login: function (credentials, callback) {
            var cb = callback || angular.noop, deferred = $q.defer();
            OAuth.login(credentials).then(function (data) {
                Principal.identity(true).then(function (account) {
                });
                deferred.resolve(data);
                return cb();
            }).catch(function (err) {
                this.logout();
                deferred.reject(err);
                return cb(err);
            }.bind(this));
            return deferred.promise;
        },
        logout: function () {
            OAuth.logout();
            Principal.authenticate(null);
        },
        authorize: function () {
            return Principal.identity().then(function () {
                var isAuthenticated = Principal.isAuthenticated();
                //FIXME do smthing
            });
        },
        createAccount: function (account, callback) {
            var cb = callback || angular.noop;
            return Account.register.save(account,
                function () {
                    return cb(account);
                },
                function (err) {
                    this.logout();
                    return cb(err);
                }.bind(this)).$promise;
        },
        updateAccount: function (account, callback) {
            var cb = callback || angular.noop;
            return Account.account.save(account,
                function () {
                    return cb(account);
                },
                function (err) {
                    return cb(err);
                }.bind(this)).$promise;
        },
        activateAccount: function (key, callback) {
            var cb = callback || angular.noop;
            return Account.activate.get(key,
                function (response) {
                    return cb(response);
                },
                function (err) {
                    return cb(err);
                }.bind(this)).$promise;
        },
        changePassword: function (newPassword, callback) {
            var cb = callback || angular.noop;
            return Account.password.save(newPassword, function () {
                return cb();
            }, function (err) {
                return cb(err);
            }).$promise;
        }
    };
}
