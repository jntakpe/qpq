qpqApp.factory('Principal', Principal);

function Principal($q, Account) {
    "use strict";

    var identity, authenticated = false;

    return {
        isIdentityResolved: function () {
            return angular.isDefined(identity);
        },
        isAuthenticated: function () {
            return authenticated;
        },
        isInRole: function (role) {
            if (!authenticated || !identity.authorities) {
                return false;
            }
            for (var i = 0; i < identity.authorities.length; i++) {
                if (identity.authorities[i].name === role) {
                    return true;
                }
            }
            return false;
        },
        isInAnyRole: function (roles) {
            if (!authenticated || !identity.authorities) {
                return false;
            }
            for (var i = 0; i < roles.length; i++) {
                if (this.isInRole(roles[i])) {
                    return true;
                }
            }
            return false;
        },
        authenticate: function (ident) {
            identity = ident;
            authenticated = ident !== null;
        },
        identity: function (force) {
            var deferred = $q.defer();
            if (force === true) {
                identity = undefined;
            }
            if (angular.isDefined(identity)) {
                deferred.resolve(identity);
                return deferred.promise;
            }
            Account.account.get().$promise
                .then(function (account) {
                    identity = account.data;
                    authenticated = true;
                    deferred.resolve(identity);
                })
                .catch(function () {
                    identity = null;
                    authenticated = false;
                    deferred.resolve(identity);
                });
            return deferred.promise;
        }
    };
}