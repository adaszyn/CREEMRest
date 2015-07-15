/**
 * Created by wojtek on 7/13/15.
 */
app.service("RESTEnergyService", ['$http', function($http) {
    this.REST_URL = "http://localhost:8080/";
    this.getLatestMeasures = function(id){
        return $http.get(this.REST_URL + "energy/stat/latest/" + id)
    };
    this.getTotalActiveConsumed = function(id, date){
        var currDate = new Date();
        return $http.get(this.REST_URL + "energy/stat/total_active_consumed/" + id + "/" + reformatDate(currDate));
    };
    this.reformatDate = function(date){
        return date.getFullYear() + "-"
            + (Number(date.getMonth())+1) + "-"
            + date.getDate().toString();
    }
    this.createUrl = function(args) {
        /**
         *
         * @param args
         * args contains: deviceID, dateFrom, dateTo, type, dateOption
         */
        var dateFromString = "",
            dateToString = "",
            deviceID = "",
            type = "";

        if (args.type) {
            type = "/" + args.type;
        }

        if (args.dateFrom) {
            dateFromString = "/" + args.dateFrom;
        }

        if (args.dateTo) {
            dateToString = "/" + args.dateTo;
        }

        if (args.deviceID) {
            deviceID = "/" + args.deviceID;
        }

        return "/energy/stat" + type + deviceID + dateFromString + dateToString;
    };

    this.getChartData = function(data){
        var timestamps = [];
        var values = [];
        for (var i = 0; i < data.length; i++){
            if(data[i].hasOwnProperty('timestamp')) {
                timestamps.push(timeStampToDate(data[i]['timestamp']).toLocaleDateString());
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
        return new Date(timestamp);
    };
}]);