app.service("WeatherService", ['$http', function($http){

    this.API_KEY = "22c328d54df08fc83f1b8c94826ffc0a";
    this.city = "Genoa";

    this.getCurrentTemperature = function (city) {
        return $http.get("http://api.openweathermap.org/data/2.5/weather?q="+city+",it");
    };

    this.convertDegToDirection = function(degrees){
        var directions = ["N","NNE", "NE", "ENE", "E", "ESE", "SE", "SSE",
            "S", "SSW", "SW", "WSW", "W", "WNW", "NW", "NNW"];
        var i = (degrees + 11.25)/22.5;

        return directions[Math.floor(i) % 16];
    };

    this.getLongForecast = function (days) {
        return $http.get('http://api.openweathermap.org/data/2.5/forecast/daily?q=Genoa&mode=json&units=metric&cnt=' + days);
    };

    this.getForecastFromDates = function (date1, date2) {
        var dates = [];
        $http.get("http://api.openweathermap.org/data/2.5/history/city?q=Genoa,IT&start=1436954400&end=1437559200")
            .success(function (data) {

            });
    }
}]);