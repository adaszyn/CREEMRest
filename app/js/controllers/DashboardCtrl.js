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
    $scope.config = {
        zoomEnabled: true,
        animationEnabled: true,
        title:{
            text: "Buildings Chart"
        },
        axisX: {
            title:"densita energia anno",
            valueFormatString: "#0.#",
            maximum: 3000,
            minimum: -100,
            gridThickness: 1,
            tickThickness: 1,
            gridColor: "lightgrey",
            tickColor: "lightgrey",
            lineThickness: 0
        },
        axisY:{
            title: "energia totale anno",
            gridThickness: 1,
            tickThickness: 1,
            gridColor: "lightgrey",
            tickColor: "lightgrey",
            lineThickness: 0,
            valueFormatString:"#,##0k,.",
            maximum: 1200000,
            interval: 100000

        },
        data: [
            {
                type: "bubble",
                toolTipContent: "<span style='\"'color: {color};'\"'><strong>{label}</strong></span><br/> <strong>densita energia anno</strong> {x}<br/> <strong>totale energia anno</strong> {y} KWh<br/> <strong>potenza picco</strong> {z}",
                dataPoints: [
                    { x: 9.14, y: 228513, z:309.34,  label:"US" },
                    { x: 16.37, y: 85292, z:141.92,  label:"Russia" },
                    { x: 9.327, y: 66239, z:1337,  label:"China" },
                    { x: 9.09, y: 58345, z:34.12,  label:"Canada" },
                    { x: 8.45, y: 29817, z:194.94,  label:"Brazil" },
                    { x: 7.68, y: 8615, z:22.29,  label:"Australia" },
                    { x: 2.97, y: 63974, z:1224.61,  label:"India" },
                    { x: 2.73, y: 25023, z:40.41,  label:"Argentina" },
                    { x: 1.94, y: 26704, z:113.42,  label:"Mexico" },
                    { x: 1.21, y: 22051, z:49.99,  label:"SA" },
                    { x: .547, y: 33608, z:65.07,  label:"France" },
                    { x: .241, y: 31471, z:62.23,  label:"U.K" },
                    { x: .348, y: 33708, z:81.77,  label:"Germany" },
                    { x: .364, y: 20035, z:127.45,  label:"Japan" },
                    { x: .995, y: 5195, z:81.12,  label:"Egypt" },
                    { x: .743, y: 5352, z:17.11,  label:"Chile" }


                ]
            }
        ]
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

    $scope.getBuildingChart = function () {
      RESTEnergyService.getBuildingData()
          .then(function (data) {
              console.log(data);
              if (!data.data.length) return;
              $scope.config.data[0].dataPoints = [];
              data.data.forEach(function (element) {
                  $scope.config.data[0].dataPoints.push({
                      x: element.densita_energia_anno,
                      y: element.energia_totale_anno,
                      z: element.potenza_picco,
                      label: element.pod
                  });
              })
          })
    };
    $scope.getBuildingChart();
    $scope.getDaily();
    $scope.refreshDevice();
}]);