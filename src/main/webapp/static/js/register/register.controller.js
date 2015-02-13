qpqApp.controller('RegisterController', RegisterController);

function RegisterController($rootScope, Auth) {
    var vm = this;
    vm.user = {};

    vm.register = function (form) {
        if (form.$valid) {
            console.log("Sending form : " + vm.user);
        } else {
            console.log("Invalid form");

        }
    };
}