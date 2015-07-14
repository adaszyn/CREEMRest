app.controller("DashboardCtrl", ['$scope', '$http', 'RESTEnergyService', 'WeatherService', function($scope, $http, RESTEnergyService, WeatherService) {
    $scope.title = "Your Dashboard";
    $scope.weather = {
    };
    $scope.deviceId = "202854434";
    $scope.latestData = {
        isLoaded : false
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
        });
    $scope.cityForecast = function () {
        WeatherService.getCurrentTemperature($scope.weather.name)
            .then(function (data) {
                $scope.weather = data.data;
                $scope.weather.wind.dir = WeatherService.convertDegToDirection($scope.weather.wind.deg);
                $scope.weather.main.temp = Math.floor($scope.weather.main.temp - 273.1);
            });
    };
    $scope.getLatestData = function (id) {
        RESTEnergyService.getLatestMeasures(id)
            .then(function(data){
                if (data.data.length !== 0) {
                    console.log(data.data);
                    $scope.latestData.isLoaded = true;
                    $scope.latestData.data = data.data;
                }
                else {
                    $scope.latestData.isLoaded = false;
                }
            })
            .catch(function () {
               $scope.latestData.isLoaded = false;
            });
    };
    $scope.refreshDevice = function () {
        $scope.getLatestData($scope.deviceId);
    };
    $scope.getDailyUsage = function(){
        RESTEnergyService.getDailyUsage($scope)
    };
    $scope.refreshDevice();
}]);