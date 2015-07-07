/**
 * Created by wojtek on 7/6/15.
 */

app.controller("ProducedCtrl", ['$scope', '$http', 'RESTUrlService', function($scope, $http, RESTUrlService){
    $scope.title = "Produced energy statistics";
    $scope.dataLimit = 10;
    $scope.deviceID = "152522786";
    $scope.date = "";
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
                date: $scope.date,
                type: "produced"
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

}]);