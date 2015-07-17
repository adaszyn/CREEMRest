
'use strict';
// 1.0.2

app.directive("canvasjsPlot", function ($timeout) {
    function link(scope, element, attrs) {
        var chart
            ,isChartReady = false
            ,updateChart
            ,i;
        updateChart = function(value) {
            if (isChartReady) {
                chart.render();
            }
        };
        for (i=0; i < scope[attrs.conf].data.length; i++){
            scope.$watchCollection(attrs.conf + '.data['+ i +'].dataPoints', updateChart);
        }
        $timeout(function () {
            chart = new CanvasJS.Chart("canvasjselem", scope[attrs.conf]);
            isChartReady = true;
        },0);

    }
    return {
        restrict: "E",
        template: "<div id='canvasjselem' style='height: 300px; width: 100%'></div>",
        link: link
    }
});