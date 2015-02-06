qpqApp.controller('LoginController', LoginController);

function LoginController($rootScope, $timeout, Auth) {
    var vm = this;
    $timeout(function () {
        angular.element('[ng-model="username"]').focus();
    });
    vm.login = function () {
        Auth.login({
            username: vm.username,
            password: vm.password,
            rememberMe: vm.rememberMe
        }).then(function () {
            vm.authenticationError = false;
            $rootScope.back();
        }).catch(function () {
            vm.authenticationError = true;
        });
    };
}