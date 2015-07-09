/**
 * Created by root on 7/9/15.
 */
app.controller("predictConsumedCtrl", ['$scope', '$http', 'RESTUrlService', function($scope, $http, RESTUrlService){
    $scope.title = "Consumed energy prediction for x days";
    $scope.deviceID = "1091002370";
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
        var url = RESTUrlService.REST_URL + "predict/" + $scope.deviceID;

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