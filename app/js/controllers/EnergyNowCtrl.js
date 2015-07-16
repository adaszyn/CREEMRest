/**
 * Created by wojtek on 7/14/15.
 */
app.controller("EnergyNowCtrl", ['$scope', '$http', 'RESTEnergyService', 'ChartFactory', function($scope, $http, RESTEnergyService, ChartFactory){
    $scope.title = "Energy statistics";
    $scope.datasets = [];
    $scope.dataLimit = 10;
    $scope.deviceId = "1913061376";
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
    };

    $scope.getDate = function(days) {
        var date = new Date();
        date.setDate(date.getDate() + days);
        return date;
    };

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

    /*$scope.updateCharts = function updateCharts(data){
        console.log(data);
        var chartData = RESTEnergyService.getChartData(data);
        console.log(data);
        $scope.chartData.labels = chartData.labels;
        $scope.chartData.datasets[0].data = chartData.values;
        var len = data.length;
        var nulls = [];
        var measured = data.filter(function (obj) {
            return !obj.prediction;
        });
        var predicted = data.filter(function (obj) {
            return obj.prediction;
        });
        var measuredLength = measured.length;
        var predictedData = [];
        var measuredData = [];
        for(var j = 0; j < len; j++){
            if(!data[j].prediction){
                measuredData.push(data[j].value);
                predictedData.push(null);
            }
            else{
                measuredData.push(null);
                predictedData.push(data[j].value);
            }
        }
        $scope.chartData.datasets = [];
        $scope.chartData.datasets.push({
            label: 'measured',
            fillColor: "rgba(0, 191, 255, 0.31)",
            strokeColor: "rgb(0, 191, 255)",
            pointColor: "rgb(0, 76, 102)",
            pointStrokeColor: "#fff",
            pointHighlightFill: "#fff",
            pointHighlightStroke: "rgba(151,187,205,1)",
            data: measuredData
        });
        $scope.chartData.datasets.push({
            label: 'measured',
            fillColor: "rgba(0, 191, 255, 0.9)",
            strokeColor: "rgb(0, 191, 255)",
            pointColor: "rgb(0, 76, 102)",
            pointStrokeColor: "#fff",
            pointHighlightFill: "#fff",
            pointHighlightStroke: "rgba(151,187,205,1)",
            data: predictedData
        })
        console.log('pred',predictedData);
        console.log('meas',measuredData);

    };
    */

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