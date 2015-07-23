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
                name: "power",
                showInLegend: true,
                type: "column",
                dataPoints: [],
                axisYType: "secondary",
            },
            {
                name: "energy consumed",
                showInLegend: true,
                type: "line",
                dataPoints: []
            }
        ],
        axisY:{
            suffix: "KWh",
            includeZero: false
        },
        axisY2:{
            suffix: "KW",
            includeZero: false
        },
        axisX:{
            valueFormatString: "DD-MMM-Y",
            labelAngle: -50
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
        if (dateRange.from > dateRange.to) {
            window.alert("Impossible date range!");
            return;
        }
        var promise = RESTEnergyService.getEnergyPowerData({
            deviceId: $scope.testedDevice,
            step: $scope.stepOption.value,
            dateTo: dateRange.to,
            dateFrom: dateRange.from
        });
        promise.energy.then(function (data) {
            try {
                var date1 = new Date(data.data[0].timestamp);
                var date2 = new Date(data.data[data.data.length - 1].timestamp);
                var daysDiff = Math.ceil(Math.abs(date2.getTime() - date1.getTime()) / (1000 * 3600 * 24));
                if (daysDiff <= 1) {
                    $scope.config.axisX.valueFormatString = 'HH:mm';
                }
                else {
                    $scope.config.axisX.valueFormatString = "DD-MMM-Y";

                }
                $scope.config.data[1].dataPoints = [];
                for (i = 0; i < data.data.length; i++){
                    $scope.config.data[1].dataPoints.push({
                        x: new Date(data.data[i].timestamp),
                        y: data.data[i].value
                    });
                }
            }
            catch(Exception) {
                window.alert("No data to show");
            }
        });
        promise.power.then(function (data) {
            $scope.config.data[0].dataPoints = [];
            for (i = 0; i < data.data.length; i++){
                $scope.config.data[0].dataPoints.push({
                    x: new Date(data.data[i].timestamp),
                    y: data.data[i].value
                })
            }
        });
    };

    $scope.updateCharts = function updateCharts(data){
        var chartData = RESTEnergyService.getChartData(data);
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