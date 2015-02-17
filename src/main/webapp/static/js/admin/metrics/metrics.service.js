qpqApp.factory('Metrics', Metrics);

function Metrics($http) {
    return {
        findMetrics: function () {
            return $http.get('manage/metrics').then(function (response) {
                return response.data;
            });
        },
        checkHealth: function () {
            return $http.get('manage/health').then(function (response) {
                return response.data;
            });
        },
        threadDump: function () {
            return $http.get('manage/dump').then(function (response) {
                return response.data;
            })
        }
    };
}