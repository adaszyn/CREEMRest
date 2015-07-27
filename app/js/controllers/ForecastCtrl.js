app.controller("ForecastCtrl", ['$scope', '$http','WeatherService', 'ChartFactory','$rootScope', function ($scope, $http, WeatherService, ChartFactory, $rootScope) {
    $scope.title = "Weather Forecast";
    $rootScope.isLoggedIn = true;
    $scope.timeOptions = [
        {name: "today", value:1},
        {name: "tomorrow", value:2},
        {name: "next week", value:7},
        {name: "next two weeks", value:14}
    ];
    $scope.timeOption = $scope.timeOptions[0];
    $scope.pressureChart = ChartFactory.getChartConfiguration({
        domain: [],
        label: 'Pressure',
        data: []
    });
    $scope.daysRange = {
        from: 0,
        to: 1
    };
    $scope.dateRange = {
        from: new Date(),
        to: new Date()
    };

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
        data: [
            {
                name: "temperature",
                showInLegend: true,
                type: "line",
                color: "blue",
                dataPoints: []
            },
            {
                name: "temperature Forecast",
                showInLegend: true,
                type: "line",
                color: "red",
                dataPoints: []
            },
            {
                name: "pressure",
                showInLegend: true,
                type: "column",
                color: "blue",
                fillOpacity: 0.3,
                axisYType: "secondary",
                dataPoints: []
            },
            {
                name: "pressureForecast",
                showInLegend: true,
                type: "column",
                color: "red",
                axisYType: "secondary",
                fillOpacity: 0.3,
                dataPoints: []
            }
        ],
        axisY:{
            suffix: "C",
            includeZero: false
        },
        axisY2:{
            suffix: "hPa",
            includeZero: false
        },
        axisX:{
            valueFormatString: "MMM DD"
        }
    };

    function addDays(date, days) {
        var result = new Date(date);
        result.setDate(result.getDate() + days);
        return result;
    }

    $scope.getForecast = function(){
        WeatherService.getLongForecast($scope.timeOption.value)
            .then(function (data) {
                $scope.updateForecast(data.data);
            });
    };

    $scope.$watch('daysRange.from', function () {
        $scope.dateRange.from = addDays(new Date(), $scope.daysRange.from);
        $scope.updateForecast();
    });

    $scope.$watch('daysRange.to', function () {
        $scope.dateRange.to = addDays(new Date(), $scope.daysRange.to);
        $scope.updateForecast();
    });

    $scope.updateForecast = function () {
        var pressureObj;
        WeatherService.getForecastForDates($scope.dateRange.from, $scope.dateRange.to)
            .then(function(data){
                $scope.config.data[0].dataPoints = [];
                $scope.config.data[1].dataPoints = [];
                $scope.config.data[2].dataPoints = [];
                $scope.config.data[3].dataPoints = [];
                for (var i = 0; i < data.length; i++) {
                    if(data[i].type === 'history'){
                        $scope.config.data[0].dataPoints.push(data[i]);
                        pressureObj = {
                            x: data[i].x,
                            y: data[i].pressure
                        };
                        $scope.config.data[2].dataPoints.push(pressureObj);
                    }
                    else if(data[i].type === 'forecast') {
                        $scope.config.data[1].dataPoints.push(data[i]);
                        pressureObj = {
                            x: data[i].x,
                            y: data[i].pressure
                        };
                        $scope.config.data[3].dataPoints.push(pressureObj);
                    }
                }
            });
    };

    $scope.getForecast();
    $scope.testForecast = function () {
        WeatherService.getForecastForDates(new Date('2015-07-16'), new Date('2015-07-18'))
            .then(function(data){});
    };
}]);