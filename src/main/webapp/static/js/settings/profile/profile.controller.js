qpqApp.controller('ProfileController', ProfileController);

function ProfileController(Account, profile) {
    "use strict";

    var vm = this;
    vm.myProfile = profile;

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
            })
    };

}