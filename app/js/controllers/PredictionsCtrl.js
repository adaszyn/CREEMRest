app.controller("PredictionsCtrl", ['$scope', '$http', function ($scope, $http) {
    $scope.title = "Predictions";
    $scope.chartData = {

        labels: ["January", "February", "March", "April", "May", "June", "July"],
        datasets: [
            {
                label: "Temperature",
                fillColor: "rgba(220,220,220,0.2)",
                strokeColor: "rgba(220,220,220,1)",
                pointColor: "rgba(220,220,220,1)",
                pointStrokeColor: "#fff",
                pointHighlightFill: "#fff",
                pointHighlightStroke: "rgba(220,220,220,1)",
                data: [65, 59, 80, 81, 56, 55, 40]
            },
            {
                label: "Pressure",
                fillColor: "rgba(151,187,205,0.2)",
                strokeColor: "rgba(151,187,205,1)",
                pointColor: "rgba(151,187,205,1)",
                pointStrokeColor: "#fff",
                pointHighlightFill: "#fff",
                pointHighlightStroke: "rgba(151,187,205,1)"
            }
        ]
    };
    $scope.updateForecast = function(data){
        var domain = [];
        var temperatures = [];
        var pressure = [];

        for (var i = 0; i < data.list.length; i++){
            domain.push(new Date(data.list[i].dt * 1000).toLocaleDateString());
            temperatures.push(data.list[i].temp.day);
            pressure.push(data.list[i].pressure);
        }

        $scope.chartData.labels = domain;
        $scope.chartData.datasets[0].data = temperatures;
        $scope.chartData.datasets[1].data = pressure;
    };

    $scope.chartOptions = {
        responsive : true
    };
    $scope.getLongForecast = function () {
        $http.get('http://api.openweathermap.org/data/2.5/forecast/daily?q=Genoa&mode=json&units=metric&cnt=7')
            .success(function (data) {
                console.log(data);
                $scope.updateForecast(data);
            });
    };
    $scope.getLongForecast();
}]);