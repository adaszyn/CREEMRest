app.controller("ForecastCtrl", ['$scope', '$http','WeatherService', 'ChartFactory', function ($scope, $http, WeatherService, ChartFactory) {
    $scope.title = "Predictions";
    $scope.days = 2;
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
    $scope.updateForecast = function(data){
        var domain = [];
        var temperatures = [];
        var pressure = [];
        for (var i = 0; i < data.list.length; i++){
            domain.push(new Date(data.list[i].dt * 1000).toLocaleDateString());
            temperatures.push(data.list[i].temp.day);
            pressure.push(data.list[i].pressure);
        }

        $scope.chartData.labels = domain;
        $scope.pressureChart.labels = domain;
        $scope.chartData.datasets[0].data = temperatures;
        $scope.pressureChart.datasets[0].data = pressure;
    };

    $scope.chartOptions = {
        responsive : true
    };
    $scope.getForecast = function(){
        WeatherService.getLongForecast($scope.days)
            .then(function (data) {
                $scope.updateForecast(data.data);
            })
    };
    $scope.getForecast();

}]);