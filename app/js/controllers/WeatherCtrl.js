/**
 * Created by wojtek on 7/8/15.
 */
app.controller("WeatherCtrl", ['$scope', 'WeatherService', function ($scope, WeatherService) {
    $scope.title = "Weather dashboard";
    $scope.weather = {};
    $scope.testMeters = [
        {id: 123, date: 2222, value: 666},
        {id: 3213, date: 2222, value: 666},
        {id: 763, date: 2222, value: 77},
        {id: 433, date: 23422, value: 5},
        {id: 123, date: 2222, value: 666},
        {id: 123, date: 43222, value: 666},
        {id: 123, date: 2222, value: 16},
        {id: 563, date: 2222, value: 666},
        {id: 123, date: 22345, value: 33},
        {id: 123, date: 2222, value: 64},
        {id: 33, date: 234, value: 666},
        {id: 123, date: 2222, value: 6342},
        {id: 123, date: 2222, value: 666},
    ];
    $scope.sortByID = function () {
        $scope.testMeters.sort(function (a,b) {
            return a.id - b.id;
        })
    };
    $scope.sortByDate = function () {
        $scope.testMeters.sort(function (a,b) {
            return a.date - b.date;
        })
    };
    $scope.sortByValue = function () {
        $scope.testMeters.sort(function (a,b) {
            return a.value - b.value;
        })
    };
    WeatherService.getCurrentTemperature()
        .then(function (data) {
            $scope.weather = data.data;
            $scope.weather.main.temp = Math.floor($scope.weather.main.temp - 273.1);
            $scope.weather.wind.dir = WeatherService.convertDegToDirection($scope.weather.wind.deg);
            console.log($scope.weather);
        });

}]);