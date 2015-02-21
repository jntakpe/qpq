qpqApp.factory('Metrics', Metrics);

function Metrics($http) {

    function resourceStats(stats) {
        var result = {};
        angular.forEach(stats.timers, function (value, key) {
            if (key.indexOf('com.github.jntakpe.qpq.web') !== -1) {
                result[key] = value;
            }
        });
        return result;
    }

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
        },
        extractStats: function (stats) {
            return {
                resource: resourceStats(stats)
            }
        }
    };
}