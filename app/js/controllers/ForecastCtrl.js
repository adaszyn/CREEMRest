app.controller("ForecastCtrl", ['$scope', '$http','WeatherService', 'ChartFactory', function ($scope, $http, WeatherService, ChartFactory) {
    $scope.title = "Forecast";
    $scope.days = 2;
    $scope.forecastOptions = [
        {name: "temperature", value:"1"},
        {name: "humidity", value:"1"},
        {name: "pressure", value:"1"}
    ];
    $scope.forecastOption = $scope.forecastOptions[0];
    $scope.chartData = ChartFactory.getChartConfiguration({
       domain: [],
        label: 'Temperature',
        data: []
    });
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
    $scope.updateForecast = function(data){
        var domain = [];
        console.log(data.list);
        $scope.dataSets.temperature = [];
        $scope.dataSets.domain = [];
        $scope.dataSets.temperature = [];
        $scope.dataSets.humidity = [];
        for (var i = 0; i < data.list.length; i++){
            domain.push(new Date(data.list[i].dt * 1000).toLocaleDateString());
            $scope.dataSets.domain.push(new Date(data.list[i].dt * 1000).toLocaleDateString());
            $scope.dataSets.temperature.push(data.list[i].temp.day);
            $scope.dataSets.humidity.push(data.list[i].humidity);
            $scope.dataSets.pressure.push(data.list[i].pressure);
        }
    };

    $scope.chartOptions = {
        responsive : true
    };

    $scope.chartUpdate = function () {
        $scope.chartData.labels = $scope.dataSets.domain;
        $scope.chartData.datasets[0].data = $scope.dataSets[$scope.forecastOption.name];
        console.log($scope.chartData.datasets[0].data);
    };

    $scope.getForecast = function(){
        WeatherService.getLongForecast($scope.days)
            .then(function (data) {
                $scope.updateForecast(data.data);
                $scope.chartUpdate();
            });
    };
    $scope.getForecast();

}]);