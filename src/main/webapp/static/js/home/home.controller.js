qpqApp.controller('HomeController', HomeController);

function HomeController(Auth) {
    "use strict";

    var vm = this;
    vm.test = function () {
        Auth.login({username: 'jntakpe', password: 'password'});
    };
}