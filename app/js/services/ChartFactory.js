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
                        fillColor: "rgba(0, 191, 255, 0.31)",
                        strokeColor: "rgb(0, 191, 255)",
                        pointColor: "rgb(0, 76, 102)",
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