app.controller("ForecastCtrl", ['$scope', '$http','WeatherService', 'ChartFactory','$rootScope', function ($scope, $http, WeatherService, ChartFactory, $rootScope) {
    $scope.title = "Forecast";
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
        title: {
            text: "Forecast"
        },
        data: [
            {
                name: "temperature",
                showInLegend: true,
                type: "line",
                color: "blue",
                dataPoints: [],
            },
            {
                name: "temperature Forecast",
                showInLegend: true,
                type: "line",
                color: "red",
                dataPoints: [],
            },
            {
                name: "pressure",
                showInLegend: true,
                type: "column",
                color: "blue",
                fillOpacity: 0.3,
                axisYType: "secondary",
                dataPoints: [],
            },
            {
                name: "pressureForecast",
                showInLegend: true,
                type: "column",
                color: "red",
                axisYType: "secondary",
                fillOpacity: 0.3,
                dataPoints: [],
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
    };

    $scope.getForecast = function(){
        WeatherService.getLongForecast($scope.timeOption.value)
            .then(function (data) {
                $scope.updateForecast(data.data);
            });
    };

    //$scope.rangeChanged = function () {
    //    console.log($scope.dateRange);
    //    $scope.dateRange.fromDate = $scope.dateRange.setDate(Date.now().getDate() + $scope.dateRange.from);
    //    $scope.dateRange.toDate = $scope.dateRange.setDate(Date.now().getDate() + $scope.dateRange.to);
    //};
    $scope.$watch('daysRange.from', function (value) {
        $scope.dateRange.from.setDate((new Date()).getDate() + $scope.daysRange.from);
        $scope.updateForecast2();
    });

    $scope.$watch('daysRange.to', function (value) {
        $scope.dateRange.to.setDate((new Date()).getDate() + $scope.daysRange.to);
        $scope.updateForecast2();
    });

    $scope.updateForecast2 = function (date1, date2) {
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
            .then(function(data){
                console.log(data);
            });
    };
}]);