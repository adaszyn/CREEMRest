app.config(function($stateProvider, $urlRouterProvider) {
    $urlRouterProvider.otherwise("/home");

    $stateProvider
        .state('home', {
            url: "/home",
            templateUrl: "js/partials/home.html"
        })
        .state('power', {
            url: "/energydata/power",
            templateUrl: "js/partials/power.html",
            controller: "PowerCtrl"
        })
        .state('consumed', {
            url: "/energydata/consumed",
            templateUrl: "js/partials/consumed.html",
            controller: "ConsumedCtrl"
        })
        .state('produced', {
            url: "/energydata/produced",
            templateUrl: "js/partials/produced.html",
            controller: "ProducedCtrl"
        });
});