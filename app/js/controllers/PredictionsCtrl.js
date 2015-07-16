app.controller("PredictionsCtrl", ['$scope', '$http','RESTUrlService', 'ChartFactory', function ($scope, $http, RESTUrlService, ChartFactory) {
    $scope.title = "Predictions";
    $scope.days = 7;
    $scope.dataLimit = 10;
    $scope.chartData = ChartFactory.getChartConfiguration({
        domain: [],
        label: "Consumed Energy",
        data: []
    });
    $scope.chartOptions = {
        responsive: true
    };

    $scope.submit = function () {
        var url = RESTUrlService.REST_URL + "/predict/" + $scope.deviceID + "?limit="
            + $scope.dataLimit + "&days="+$scope.days;
        $http.get(url)
            .success(function (data) {
                $scope.updateCharts(data);
            })
            .error(function (data) {
                console.log("NO");
            })
    };

    $scope.updateCharts = function updateCharts(data) {
        var chartData = RESTUrlService.getChartData(data);
        $scope.chartData.labels = chartData.labels;
        $scope.chartData.datasets[0].data = chartData.values;
    };
}])