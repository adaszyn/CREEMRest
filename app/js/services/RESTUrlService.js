/**
 * Created by wojtek on 7/7/15.
 */
app.service("RESTUrlService", ['$http', function($http){
    this.REST_URL = "http://localhost:8080";
    this.createUrl = function(args) {
        /**
         *
         * @param args
         * args contains: deviceID, dateFrom, dateTo, limit, type
         */
        var dateFromString = "",
            dateToString = "",
            deviceID = "",
            limit = "",
            type = "";

        if (args.type) {
            type = "/" + args.type;
        }

        if (args.dateFrom) {
            dateFromString = "/"
                + args.dateFrom.getFullYear() + "-"
                + (Number(args.dateFrom.getMonth())+1) + "-"
                + args.dateFrom.getDate().toString();
        }

        if (args.dateTo) {
            dateToString = "/"
                + args.dateTo.getFullYear() + "-"
                + (Number(args.dateTo.getMonth())+1) + "-"
                + args.dateTo.getDate().toString();
        }

        if (args.deviceID) {
            deviceID = "/" + args.deviceID;
        }
        else {
            return type;
        }

        if (args.limit) {
            limit = "?limit=" + args.limit;
        }

        return "/energydata" + type + deviceID + dateFromString + dateToString + limit;
    };

    this.getChartData = function(data){
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
        return {
            labels: timestamps,
            values: values
        }
    };

    var timeStampToDate = function (timestamp) {
        return new Date(timestamp*1000);
    };

    this.getLatestData = function(){
        return $http.get(this.REST_URL + "/meters")
    }
}]);