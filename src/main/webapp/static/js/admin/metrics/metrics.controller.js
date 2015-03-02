qpqApp.controller('MetricsController', MetricsController);

function MetricsController($scope, Metrics) {
    "use strict";

    var vm = this;
    vm.data = {};
    vm.refresh = function () {
        Metrics.findMetrics().then(function (promise) {
            vm.data = promise;
        }, function (promise) {
            vm.data = promise.data;
        });
    };

    $scope.$watch(
        angular.bind(vm, function () {
            return vm.data;
        }),
        function (stats) {
            vm.stats = Metrics.extractStats(stats);
        }
    );

    vm.refresh();
}