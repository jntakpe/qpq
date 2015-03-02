qpqApp.controller('LoginController', LoginController);

function LoginController($rootScope, Auth) {
    "use strict";

    var vm = this;
    vm.alert = {
        msg: 'Identifiants invalides.',
        active: false
    };
    vm.login = function () {
        Auth.login({
            username: vm.username,
            password: vm.password,
            rememberMe: vm.rememberMe
        }).then(function () {
            vm.authenticationError = false;
            $rootScope.back();
        }).catch(function () {
            vm.alert.active = true;
            vm.username = '';
            vm.password = '';
        });
    };
}