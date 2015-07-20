app.controller("DashboardCtrl", ['$scope', '$http', 'RESTEnergyService', 'WeatherService', '$rootScope', function($scope, $http, RESTEnergyService, WeatherService, $rootScope) {
    $scope.title = "Your Dashboard";
    $scope.weather = {
    };
    $scope.dailyUsage = {};
    $scope.deviceId = "1913061376";
    $scope.deviceUsageRatio = 0;
    $rootScope.isLoggedIn = true;
    $scope.latestData = {
        isLoaded: false
    };
    $scope.deviceUsage = function() {
      isLoaded: false
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
        $scope.getDeviceUsage();
        RESTEnergyService.getLatestMeasures(id)
            .then(function(data){
                if (data.data.length !== 0) {
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
    $scope.getDeviceUsage = function(){
        RESTEnergyService.getDailyForId($scope.deviceId)
            .then(function(data){
                if(data.data.length !== 0) {
                    $scope.deviceUsage.isLoaded = true;
                    var lastMeasured = data.data.filter(function (element, index, array) {
                        return !element.prediction;
                    });
                    var lastPredicted = data.data.filter(function (element, index, array) {
                        return element.prediction;
                    });
                    var lastMeasuredObj = lastMeasured.pop();
                    $scope.deviceUsage.lastUpdate = lastMeasuredObj.timestamp;
                    $scope.deviceUsage.measured = lastMeasuredObj.value - data.data[0].value;
                    $scope.deviceUsage.predicted = lastPredicted.pop().value - data.data[0].value;
                    $scope.deviceUsageRatio = Math.floor(100 * $scope.deviceUsage.measured / $scope.deviceUsage.predicted);
                    //$scope.deviceUsageRatio = 44;
                }
                else{
                    $scope.deviceUsage.isLoaded = false;
                }
            })
            .catch(function () {
                $scope.deviceUsage.isLoaded = false;
            });

    };
    $scope.getDaily = function(){
      RESTEnergyService.getDaily()
          .then(function (data) {
              var sumOfArr = function (arr, key) {
                  var sum = 0;
                  for (var i = 0; i < arr.length; i++) {
                      sum += Number(arr[i].value);
                  }
                  return sum;
              };
              var consumed,
                  produced,
                  power;
              consumed = data.data.filter(function (obj) {
                  return obj.type === "active_energy_consumed";
              });
              produced = data.data.filter(function (obj) {
                  return obj.type === "active_energy_produced";
              });
              power = data.data.filter(function (obj) {
                  return obj.type === "active_power";
              });
              $scope.dailyUsage = {
                  day: new Date(),
                  consumed: sumOfArr(consumed, 'value'),
                  produced: sumOfArr(produced, 'value'),
                  power: sumOfArr(power, 'value')
              };
          });
    };
    $scope.getDaily();
    $scope.refreshDevice();
}]);