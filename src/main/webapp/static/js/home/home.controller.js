qpqApp.controller('HomeController', ['$http', HomeController]);

function HomeController($http) {
    this.msg = "Hello world !!!";

    this.test = function () {
        var username = 'jntakpe',
            password = 'password',
            data = "username=" + username + "&password="
                + password + "&grant_type=password&scope=read%20write&" +
                "client_secret=mySecretOAuthSecret&client_id=qpqapp";
        $http.post('oauth/token', data, {
            headers: {
                "Content-Type": "application/x-www-form-urlencoded",
                "Accept": "application/json",
                "Authorization": "Basic " + window.btoa("qpqapp" + ':' + "mySecretOAuthSecret")
            }
        }).success(function (response) {
            console.log(response);
        });
    };
}