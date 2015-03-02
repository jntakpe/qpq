qpqApp.controller('HomeController', HomeController);

function HomeController($stateParams) {
    "use strict";
    var vm = this;
    vm.alert = {};
    if ($stateParams.firstConnexion) {
        vm.alert = {
            active: true,
            msg: 'Création du compte effectuée. Un mail d\'activation vous a été envoyé.'
        };
    } else if ($stateParams.error) {
        vm.alert = {
            active: true,
            msg: 'Accès à la ressource interdit',
            type: 'danger'
        };
    }
}