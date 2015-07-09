/**
 * Created by wojtek on 7/6/15.
 */

app.controller("EnergyMeterCtrl", ['$scope', '$http', 'RESTUrlService', function($scope, $http, RESTUrlService){
    $scope.title = "Produced energy statistics";
    $scope.dataLimit = 10;
    $scope.deviceID = "152522786";
    $scope.dateFrom = "";
    $scope.dateTo = "";
    $scope.devices = ['12212', 'aaaab','ccccc'];
    $scope.chartData = {
        labels: [],
        datasets: [
            {
                label: 'Main dataset',
                fillColor: 'rgba(220,220,220,0.2)',
                strokeColor: 'rgba(220,220,220,1)',
                pointColor: 'rgba(220,220,220,1)',
                pointStrokeColor: '#fff',
                pointHighlightFill: '#fff',
                pointHighlightStroke: 'rgba(220,220,220,1)',
                data: []
            }
        ]
    };

    $scope.chartOptions = {
        responsive : true
    };

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
    };

    $scope.getDatasets = function(){
        $http.get(RESTUrlService.REST_URL + "/datasets")
            .success(function (data) {
                $scope.datasets = data;
                $scope.selectedDataset = $scope.datasets[0];
                console.log(data);
            })
    };
    $scope.getDatasets();
}]);