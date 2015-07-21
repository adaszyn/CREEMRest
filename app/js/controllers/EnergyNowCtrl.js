/**
 * Created by wojtek on 7/14/15.
 */
app.controller("EnergyNowCtrl", ['$scope', '$http', 'RESTEnergyService', 'ChartFactory', '$rootScope', function($scope, $http, RESTEnergyService, ChartFactory, $rootScope){
    var i;
    $scope.testedDevice = '1913061376';
    $scope.title = "Energy statistics";
    $scope.datasets = [];
    $scope.dataLimit = 10;
    $scope.deviceId = "1913061376";
    $rootScope.isLoggedIn = true;
    $scope.customDateFrom = new Date();
    $scope.customDateTo = new Date();
    $scope.dateOptions = [
        {name:'Yesterday', daysFrom: -1},
        {name:'Today', daysFrom: 0},
        {name:'This week', daysFrom: -7},
        {name:'This month', daysFrom: -30},
        {name:'Custom data', daysFrom: undefined}
    ];
    $scope.dateOption = $scope.dateOptions[1];
    $scope.stepOptions = [
        {name: "hour", value: '3600000'},
        {name: "day", value: '86400000'}
    ];
    $scope.stepOption = $scope.stepOptions[0];
        $scope.chartData = ChartFactory.getChartConfiguration({
        domain: [],
        label: "Energy",
        data: []
    });

    $scope.config = {
        title: {
            text: "EnergyNow"
        },
        data: [
            {
                name: "temperature",
                showInLegend: true,
                type: "line",
                dataPoints: []
            },
            {
                name: "predicted",
                showInLegend: true,
                type: "line",
                color: "red",
                markersize: 0,
                dataPoints: []
            }
        ],
        axisY:{
            suffix: "C",
            includeZero: false
        },
        axisX:{
            valueFormatString: "MMM DD"
        }
    };

    $scope.$on('$viewContentLoaded', function(event){
        setTimeout(function () {
            var icon = document.getElementById("loading-img");
            icon.className = "power-cord";
        }, 500);
    });

    var getDateFromDays = function getDateFromDelta(daysFrom) {
        if (daysFrom === undefined) {
            return {
                from: $scope.customDateFrom,
                to: $scope.customDateTo
            }
        }
        else {
            var date = new Date();
            date.setDate(date.getDate() + daysFrom);
            return {
                from: date,
                to: undefined
            }
        }
    };

    $scope.submit = function(){
        var dateRange = getDateFromDays($scope.dateOption.daysFrom);
        if (dateRange.to === undefined) {
            dateRange.to = new Date();
        }
        if (dateRange.from.getDate() > dateRange.to.getDate()) {
            window.alert("Impossible daterange!");
            return;
        }
        RESTEnergyService.getEnergyPowerData({
            deviceId: $scope.testedDevice,
            step: $scope.stepOption.value,
            dateTo: dateRange.to,
            dateFrom: dateRange.from
        })
            .then(function (data) {
               //parsing data to chart
            });
    };

    $scope.updateCharts = function updateCharts(data){
        var chartData = RESTEnergyService.getChartData(data);
        console.log(data);
        $scope.config.data[0].dataPoints = [];
        for (i = 0; i < data.length; i++) {
            var point = {x: new Date(data[i].timestamp * 1000), y: data[i].value};
            if (data[i].prediction === false) {
                $scope.config.data[0].dataPoints.push(point);
            }
            else {
                $scope.config.data[1].dataPoints.push(point);
            }
        }
        $scope.chartData.labels = chartData.labels;
        $scope.chartData.datasets[0].data = chartData.values;
    };

    $scope.getDatasets = function(){
        $http.get(RESTEnergyService.REST_URL + "datasets")
            .success(function (data) {
                for (var i = 0; i < data.length; i++) {
                    $scope.datasets.push(data[i].toLowerCase());
                }
                $scope.selectedDataset = $scope.datasets[0];
            })
    };

    $scope.getDatasets();
}]);