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
    $scope.dateOptions = [
        {name:'Yesterday', dateFrom: -1, dateTo: ""},
        {name:'Today', dateFrom:0, dateTo: ""},
        {name:'This week', dateFrom: -7, dateTo: 0},
        {name:'This month', dateFrom: -30, dateTo: 0},
        {name:'Custom data', dateFrom: 0, dateTo: 0}
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

    $scope.dateUpdate = function() {
        $scope.dateFrom = $scope.getDate($scope.dateOption.dateFrom);
        if ($scope.dateOption.dateTo === "") {
            $scope.dateTo = "";
        }
        else {
            $scope.dateTo = $scope.getDate($scope.dateOption.dateTo);
        }
    };

    var getDateFromDelta = function getDateFromDelta(days) {
        var date = new Date();
        date.setDate(date.getDate() + days);
        return date;
    };

    $scope.submit = function(){
        //var url = RESTEnergyService.REST_URL + RESTEnergyService.createUrl({
        //        deviceID: $scope.deviceId,
        //        dateFrom: $scope.dateFrom,
        //        dateTo: $scope.dateTo,
        //        type: $scope.selectedDataset,
        //        dateOption: $scope.dateOption
        //    });
        //var url = RESTEnergyService.REST_URL + 'energy/energypower/' + $scope.testedDevice + "/" + $scope.stepOption.value + $scope.
        //$http.get(url)
        //    .success(function(data){
        //        $scope.updateCharts(data);
        //    })
        //    .error(function(data){
        //        console.log("NO");
        //    })
        console.log(RESTEnergyService.getEnergyPowerData({
            deviceId: $scope.testedDevice,
            step: $scope.stepOption.value,
            dateTo: ($scope.dateOption.dateTo ? getDateFromDelta($scope.dateOption.dateTo) : undefined),
            dateFrom: ($scope.dateOption.dateTo ? getDateFromDelta($scope.dateOption.dateFrom) : undefined),
        }));
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