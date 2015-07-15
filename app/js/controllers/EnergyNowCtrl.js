/**
 * Created by wojtek on 7/14/15.
 */
app.controller("EnergyNowCtrl", ['$scope', '$http', 'RESTEnergyService', 'ChartFactory', function($scope, $http, RESTEnergyService, ChartFactory){
    $scope.title = "Energy statistics";
    $scope.datasets = [];
    $scope.dataLimit = 10;
    $scope.deviceId = "1913061376";
    $scope.dateFrom = "";
    $scope.dateTo = "";
    $scope.dateOptions = [
        {name:'Yesterday', dateFrom:-1, dateTo:""},
        {name:'Today', dateFrom:0, dateTo:""},
        {name:'This week', dateFrom:-7, dateTo:0},
        {name:'This month', dateFrom:-30, dateTo:0},
        {name:'Custom data', dateFrom:0, dateTo:0}
    ];
    $scope.dateOption = $scope.dateOptions[1];
    $scope.chartData = ChartFactory.getChartConfiguration({
        domain: [],
        label: "Energy",
        data: []
    });

    $scope.chartOptions = {
        responsive : true
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
    }

    $scope.getDate = function(days) {
        var date = new Date();
        date.setDate(date.getDate() + days);
        return RESTEnergyService.reformatDate(date);
    }

    $scope.submit = function(){
        console.log($scope.dateOption);
        var url = RESTEnergyService.REST_URL + RESTEnergyService.createUrl({
                deviceID: $scope.deviceId,
                dateFrom: $scope.dateFrom,
                dateTo: $scope.dateTo,
                type: $scope.selectedDataset,
                dateOption: $scope.dateOption
            });
        $http.get(url)
            .success(function(data){
                $scope.updateCharts(data);
            })
            .error(function(data){
                console.log("NO");
            })
    };

    $scope.updateCharts = function updateCharts(data){
        var chartData = RESTEnergyService.getChartData(data);
        $scope.chartData.labels = chartData.labels;
        $scope.chartData.datasets[0].data = chartData.values;
        console.log(chartData.values);
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

    $scope.$on('$viewContentLoaded', function(event){
        setTimeout(function () {
            var icon = document.getElementById("loading-img");
            icon.className = "power-cord";
        }, 500);
    });

    $scope.getDatasets();
    $scope.dateFrom = $scope.getDate(0);
}]);