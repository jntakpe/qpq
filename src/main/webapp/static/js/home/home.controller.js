qpqApp.controller('HomeController', HomeController);

function HomeController($stateParams) {
    "use strict";
    var vm = this;
    vm.alert = {
        active: false,
        type: 'success'
    };
    if ($stateParams.firstConnexion) {
        vm.alert.active = true,
            vm.alert.msg = 'Création du compte effectuée. Un mail d\'activation vous a été envoyé.'
    }
    vm.closeAlert = function () {
        vm.alert.active = false;
    };
}