var authApp = angular.module('authApp', ['ngResource', 'LocalStorageModule']);

authApp.config(function (localStorageServiceProvider) {
    "use strict";

    localStorageServiceProvider.setPrefix('qpqAuth');
});

