qpqApp.controller('RegisterController', RegisterController);

function RegisterController(Auth) {
    var vm = this;
    vm.user = {};

    vm.register = function (form) {
        if (form.$valid) {
            Auth.createAccount(vm.user).then(function () {
                console.log("Acc created");
            }).catch(function () {
                console.log("Error creating acc");
            });
        }
    };
}