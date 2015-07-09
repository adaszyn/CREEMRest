/**
 * Created by wojtek on 7/9/15.
 */
app.factory("ChartFactory", function () {
   return {
       getChartConfiguration: function(args){
            return{
                labels: args.domain,
                datasets: [
                    {
                        label: args.label,
                        fillColor: "rgba(151,187,205,0.2)",
                        strokeColor: "rgba(151,187,205,1)",
                        pointColor: "rgba(151,187,205,1)",
                        pointStrokeColor: "#fff",
                        pointHighlightFill: "#fff",
                        pointHighlightStroke: "rgba(151,187,205,1)",
                        data: args.data
                    }
                ]
            };
       }
   };
});