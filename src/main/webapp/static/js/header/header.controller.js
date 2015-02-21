qpqApp.controller('HeaderController', HeaderController);

function HeaderController(Auth, Principal, $state) {
    "use strict";

    var vm = this;
    vm.isAuthenticated = Principal.isAuthenticated;
    vm.isInRole = Principal.isInRole;
    vm.$state = $state;
    vm.logout = function () {
        Auth.logout();
        $state.go('home');
    };
}