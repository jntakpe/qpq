qpqApp.controller('NavbarController', NavbarController);

function NavbarController(Auth, Principal, $state) {
    "use strict";

    this.isAuthenticated = Principal.isAuthenticated;
    this.isInRole = Principal.isInRole;
    this.$state = $state;
    this.logout = function () {
        Auth.logout();
        $state.go('home');
    };
}