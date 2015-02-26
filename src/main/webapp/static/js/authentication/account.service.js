qpqApp.factory('Account', Account);

function Account($resource) {
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
        register: $resource('api/register', {}, {})
    }
}
