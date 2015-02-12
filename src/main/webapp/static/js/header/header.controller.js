qpqApp.controller('HeaderController', HeaderController);

function HeaderController(Auth, Principal, $state) {
    "use strict";

    this.isAuthenticated = Principal.isAuthenticated;
    this.isInRole = Principal.isInRole;
    this.$state = $state;
    this.logout = function () {
        Auth.logout();
        $state.go('home');
    };
}