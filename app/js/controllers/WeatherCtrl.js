/**
 * Created by wojtek on 7/8/15.
 */
app.controller("WeatherCtrl", ['$scope', 'WeatherService', 'RESTUrlService', function ($scope, WeatherService, RESTUrlService) {
    $scope.title = "Weather dashboard";
    $scope.weather = {};
    $scope.addWeather = {};
    $scope.lastData = [];
    $scope.sortByID = function () {
        $scope.lastData.sort(function(a, b) {
            return a.id >= b.id;
        });
    };
    $scope.sortByDate = function () {
        $scope.lastData.sort(function (a,b) {
            return a.timestamp >= b.timestamp;
        })
    };
    $scope.sortByValue = function () {
        $scope.lastData.sort(function (a,b) {
            return a.value >= b.value;
        })
    };
    WeatherService.getCurrentTemperature("Genoa")
        .then(function (data) {
            $scope.weather = data.data;
            $scope.weather.main.temp = Math.floor($scope.weather.main.temp - 273.1);
            $scope.weather.wind.dir = WeatherService.convertDegToDirection($scope.weather.wind.deg);
            console.log($scope.weather);
        });
    RESTUrlService.getLatestData()
        .then(function(data){
           $scope.lastData = data.data;
            console.log("lastData", $scope.lastData);
        });
    $scope.cityForecast = function () {
        WeatherService.getCurrentTemperature($scope.addWeather.name)
            .then(function (data) {
                $scope.addWeather = data.data;
                $scope.addWeather.wind.dir = WeatherService.convertDegToDirection($scope.addWeather.wind.deg);
                $scope.addWeather.main.temp = Math.floor($scope.addWeather.main.temp - 273.1);
                console.log($scope.addWeather);
            });
    }
}]);