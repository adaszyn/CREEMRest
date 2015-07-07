/**
 * Created by wojtek on 7/6/15.
 */

app.controller("ConsumedCtrl", ['$scope', '$http', function($scope, $http){
    $scope.title = "Consumed energy statistics";
    $scope.dataLimit = "";
    $scope.deviceID = "";
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
        console.log($scope.date, $scope.deviceID, $scope.dataLimit);
        $http.get("http://localhost:8080/consumed/" + $scope.deviceID + "/" + $scope.date)
            .success(function(data){
                $scope.updateCharts(data);
            })
            .error(function(data){
                console.log("NO");
            })
    };

    $scope.updateCharts = function updateCharts(data){
        var timestamps = [];
        var values = [];
        for (var i = 0; i < data.length; i++){
            if(data[i].hasOwnProperty('timestamp')) {
                timestamps.push(timeStampToDate(data[i]['timestamp']));
            }
            if(data[i].hasOwnProperty('value')) {
                values.push(data[i]['value']);
            }
        }
        console.log(timestamps);
        console.log(values);
        $scope.chartData.labels = timestamps;
        $scope.chartData.datasets[0].data = values;

    };

    var timeStampToDate = function (timestamp) {
        return new Date(timestamp*1000);
    }
}]);