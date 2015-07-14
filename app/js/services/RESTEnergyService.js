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
}]);