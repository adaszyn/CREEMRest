/**
 * Created by wojtek on 7/7/15.
 */
app.service("RESTUrlService", function(){
    this.REST_URL = "http://localhost:8080";
    this.createUrl = function(args) {
        /**
         *
         * @param args
         * args contains: deviceID, date, limit, type
         */
        var dateString = "",
            deviceID = "",
            limit = "",
            type = "";

        if (args.type) {
            type = "/" + args.type;
        }

        if (args.date) {
            dateString = "/"
                + args.date.getFullYear() + "-"
                + (Number(args.date.getMonth())+1) + "-"
                + args.date.getDate().toString();
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

        return type + deviceID + dateString + limit;
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
    }
});