barsApp.controller('BarsController', ['$mdSidenav', BarsController]);

function BarsController($mdSidenav) {

    this.toggleLeft = function () {
        $mdSidenav('left').toggle();
    };

}