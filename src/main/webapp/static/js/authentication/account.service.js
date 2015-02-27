qpqApp.factory('Account', Account);

function Account($resource, $http) {
    "use strict";

    return {
        account: $resource('api/account', {}, {
            get: {
                method: 'GET', params: {}, isArray: false, interceptor: {
                    response: function (response) {
                        return response;
                    }
                }
            }
        }),
        activate: function () {
            return $resource('api/activate', {}, {
                'get': {method: 'GET', params: {}, isArray: false}
            });
        },
        change: $resource('api/account/change_password', {}, {}),
        valid: function (password) {
            return $http.get('api/account/valid_password', {params: {password: password}});
        },
        register: $resource('api/register', {}, {})
    };
}
