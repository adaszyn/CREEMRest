/**
 * Created by wojtek on 7/6/15.
 */

app.controller("EnergyMeterCtrl", ['$scope', '$http', 'RESTUrlService', 'ChartFactory', function($scope, $http, RESTUrlService, ChartFactory){
    $scope.title = "Energy statistics";
    $scope.datasets = [];
    $scope.dataLimit = 10;
    $scope.deviceID = "202854434";
    $scope.dateFrom = "";
    $scope.dateTo = "";
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
    $scope.submit = function(){
        var url = RESTUrlService.REST_URL + RESTUrlService.createUrl({
                deviceID: $scope.deviceID,
                limit: $scope.dataLimit,
                dateFrom: $scope.dateFrom,
                dateTo: $scope.dateTo,
                type: $scope.selectedDataset
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
        var chartData = RESTUrlService.getChartData(data);
        $scope.chartData.labels = chartData.labels;
        $scope.chartData.datasets[0].data = chartData.values;
        console.log(chartData.values);
    };

    $scope.getDatasets = function(){
        $http.get(RESTUrlService.REST_URL + "/datasets")
            .success(function (data) {
                for (var i = 0; i < data.length; i++) {
                    $scope.datasets.push(data[i].toLowerCase());
                }
                $scope.selectedDataset = $scope.datasets[0];
            })
    };
    $scope.getDatasets();
}]);