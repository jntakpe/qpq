qpqApp.controller('HomeController', HomeController);

function HomeController(Auth) {
    this.test = function () {
        Auth.login({username: 'jntakpe', password: 'password'});
    };
}