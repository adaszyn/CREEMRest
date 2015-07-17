app.controller("ForecastCtrl", ['$scope', '$http','WeatherService', 'ChartFactory', function ($scope, $http, WeatherService, ChartFactory) {
    $scope.title = "Forecast";
    $scope.days = 2;
    $scope.forecastOptions = [
        {name: "temperature", value:"1"},
        {name: "humidity", value:"1"},
        {name: "pressure", value:"1"}
    ];
    $scope.forecastOption = $scope.forecastOptions[0];
    $scope.pressureChart = ChartFactory.getChartConfiguration({
        domain: [],
        label: 'Pressure',
        data: []
    });
    $scope.$on('$viewContentLoaded', function(event){
        setTimeout(function () {
            var icon = document.getElementById("loading-img");
            icon.className = "power-cord";
        }, 500);
    });
    $scope.dataSets = {
      domain: [],
      temperature: [],
      humidity: [],
      pressure: []
    };
    $scope.config = {
        title: {
            text: "Fruits sold in First Quarter"
        },
        data: [
            {
                type: "line",
                dataPoints: []
            },
            {
                type: "line",
                axisYType: "secondary",
                dataPoints: []
            }
        ]
    };

    $scope.updateForecast = function(data){
        $scope.config.data[0].dataPoints = [];
        $scope.config.data[1].dataPoints = [];

        for (var i = 0; i < data.list.length; i++){
            $scope.config.data[0].dataPoints.push({
                x: new Date(data.list[i].dt * 1000),
                y: data.list[i].temp.day
            });
            $scope.config.data[1].dataPoints.push({
                x: new Date(data.list[i].dt * 1000),
                y: data.list[i].pressure
            });
        }
        console.log($scope.config.data);
    };

    $scope.getForecast = function(){
        WeatherService.getLongForecast($scope.days)
            .then(function (data) {
                $scope.updateForecast(data.data);
            });
    };
    $scope.getForecast();
}]);