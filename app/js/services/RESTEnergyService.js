/**
 * Created by wojtek on 7/13/15.
 */
app.service("RESTEnergyService", ['$http', function($http) {
    this.REST_URL = "http://localhost:8080/";
    this.getLatestMeasures = function(id){
        return $http.get(this.REST_URL + "energy/stat/latest/" + id)
    };
    this.getDailyForId = function(id){
        var currDate = new Date();
        return $http.get(this.REST_URL + "energy/stat/total_active_consumed/" + id + "/" + reformatDate(currDate));
    };
    var reformatDate = function(date){
        return date.getFullYear() + "-"
            + (Number(date.getMonth())+1) + "-"
            + date.getDate().toString();
    };
    this.getDaily = function () {
        return $http.get(this.REST_URL + "/energy/latest/" + reformatDate(new Date));
    }
}]);