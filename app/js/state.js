app.config(function($stateProvider, $urlRouterProvider) {
    $urlRouterProvider.otherwise("/home");

    $stateProvider
        .state('home', {
            url: "/home",
            templateUrl: "js/partials/home.html"
        })
        .state('users', {
            url: "/users",
            templateUrl: "js/partials/users.html"
        });
});