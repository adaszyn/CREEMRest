app.config(function($stateProvider, $urlRouterProvider) {
    $urlRouterProvider.otherwise("/home");
    //$urlRouterProvider.when('/predictions/', '/predictions/weather');
    $stateProvider
        .state('root',{
            url: '/',
            views: {
                'header': {
                    templateUrl: 'js/partials/header.html',
                    controller: 'HeaderCtrl'
                },
                'content': {
                }
            }
        })
        .state('root.home', {
            url: "home",
            views: {
                'content@': {
                    templateUrl: 'js/partials/home.html',
                    controller: 'HomepageCtrl'
                }
            }
        })
        .state('root.dashboard', {
            url: "dashboard",
            views: {
                'content@': {
                    templateUrl: "js/partials/dashboard.html",
                    controller: "DashboardCtrl"
                }
            }

        })
        .state('root.consumed', {
            url: "consumed",
            views: {
                'content@': {
                    templateUrl: "js/partials/consumed.html",
                    controller: "ConsumedCtrl"
                }
            }
        })
        .state('root.produced', {
            url: "produced",
            views: {
                'content@': {
                    templateUrl: "js/partials/produced.html",
                    controller: "ProducedCtrl"
                }
            }
        })
        .state('root.weather', {
            url: "weather",
            views: {
                'content@': {
                    templateUrl: "js/partials/weather.html",
                    controller: "WeatherCtrl"
                }
            }
        })
        .state('root.predictions', {
            url: "predictions",
            views: {
                'content@': {
                    templateUrl: "js/partials/predictions.html",
                    controller: "PredictionsCtrl"
                }
            }
        })
        .state('root.forecast', {
            url: "forecast",
            views: {
                'content@': {
                    templateUrl: "js/partials/forecast.html",
                    controller: "ForecastCtrl"
                }
            }
        });
});