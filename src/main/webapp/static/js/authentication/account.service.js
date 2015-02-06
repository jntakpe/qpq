authApp.factory('Account', Account);

function Account($resource) {
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
        activate: function ($resource) {
            return $resource('api/activate', {}, {
                'get': {method: 'GET', params: {}, isArray: false}
            });
        },
        change: function ($resource) {
            return $resource('api/account/change_password', {}, {});
        },
        register: $resource('api/register', {}, {})
    }
}
