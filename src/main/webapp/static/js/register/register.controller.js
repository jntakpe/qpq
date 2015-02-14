qpqApp.controller('RegisterController', RegisterController);

function RegisterController(Auth, $state) {
    var vm = this;
    vm.user = {};
    vm.alert = {
        active: false,
        msg: 'Echec lors de la tentative de création du compte'
    };
    vm.register = function (form) {
        if (form.$valid) {
            Auth.createAccount(vm.user).then(function () {
                $state.go('login', {firstConnexion: vm.user.login});
            }).catch(function () {
                vm.alert.active = true;
            });
        }
    };
    vm.closeAlert = function () {
        vm.alert.active = false;
    };
}