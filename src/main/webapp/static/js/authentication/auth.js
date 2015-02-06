var authApp = angular.module('authApp', ['ngResource', 'LocalStorageModule']);

authApp.config(function (localStorageServiceProvider) {
    localStorageServiceProvider.setPrefix('qpqAuth');
});

