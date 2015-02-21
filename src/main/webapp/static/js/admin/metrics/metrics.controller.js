qpqApp.controller('MetricsController', MetricsController);

function MetricsController(Metrics) {
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

    vm.refresh();
}