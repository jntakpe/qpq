qpqApp.controller('ProfileController', ProfileController);

function ProfileController(Account) {
    "use strict";

    var vm = this;

    vm.myProfile = Account.account.get(function (profile) {
        vm.initialProfile = angular.copy(profile.data);
    });

    vm.edit = function () {
        Account.account.save(vm.myProfile,
            function (response) {
                vm.myProfile = response;
                vm.alert = {
                    active: true,
                    msg: 'Enregistrement du profil effectué',
                    type: 'success'
                }
            }, function () {
                vm.alert = {
                    active: true,
                    msg: 'Echec lors de l\'enregistrement du profil',
                    type: 'danger'
                };
            });
    };

    vm.reset = function () {
        vm.myProfile = angular.copy(vm.initialProfile);
    };

    vm.password = {};

    vm.editPassword = function () {
        vm.password.id = vm.myProfile.id;
        Account.change.save(vm.password,
            function () {
                vm.alert = {
                    active: true,
                    msg: 'Changement de mot de passe effectué',
                    type: 'success'
                }
            }, function () {
                vm.alert = {
                    active: true,
                    msg: 'Echec lors de la modification du mot de passe',
                    type: 'danger'
                }
            });
    }
}