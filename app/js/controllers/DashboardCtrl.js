app.controller("DashboardCtrl", ['$scope', '$http', 'RESTEnergyService', 'WeatherService', function($scope, $http, RESTEnergyService, WeatherService) {
    $scope.title = "Your Dashboard";
    $scope.weather = {
        name: "ELO"
    };
    $scope.$on('$viewContentLoaded', function(event){
        setTimeout(function () {
            var icon = document.getElementById("loading-img");
            icon.className = "power-cord";
        }, 500);
    });
    WeatherService.getCurrentTemperature("Genoa")
        .then(function (data) {
            $scope.weather = data.data;
            $scope.weather.main.temp = Math.floor($scope.weather.main.temp - 273.1);
            $scope.weather.wind.dir = WeatherService.convertDegToDirection($scope.weather.wind.deg);
            console.log($scope.weather);
        });
    $scope.cityForecast = function () {
        WeatherService.getCurrentTemperature($scope.weather.name)
            .then(function (data) {
                $scope.weather = data.data;
                $scope.weather.wind.dir = WeatherService.convertDegToDirection($scope.weather.wind.deg);
                $scope.weather.main.temp = Math.floor($scope.weather.main.temp - 273.1);
                console.log($scope.weather);
            });
    };
    $scope.getLatestData = function (id) {
        RESTEnergyService.getLatestMeasures(id)
            .then(function(data){
                console.log(data);
            });
    };
    $scope.getLatestData('202854434');
}]);