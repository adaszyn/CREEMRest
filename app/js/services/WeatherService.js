app.service("WeatherService", ['$http', '$q', function($http, $q){
    var SERVICE_SCOPE = this;
    this.API_KEY = "22c328d54df08fc83f1b8c94826ffc0a";
    this.city = "Genoa";
    this.isCached = false;
    this.cachedData = [];
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

    this.cacheForecast = function(d1, d2){
        //d1, d2 are dates for promise
        //date1, date2 are dates to cache data
        var todayTimestamp = Math.floor(Date.now() / 1000)
            ,daysForecast = []
            ,date1 = new Date()
            ,date2 = new Date()
            ,i
            ,daysDelta
            ,dayMeasures
            ,startTimestamp;
        date1.setDate(date1.getDate() - 7);
        date2.setDate(date2.getDate() + 7);
        startTimestamp = Math.floor(date1.getTime() / 1000);
        daysDelta = Math.floor(Math.abs(date1 - date2) / (1000 * 60 * 60 * 24));
        for (i = 0; i <= daysDelta; i++) {
            daysForecast.push({x: new Date((new Date()).setDate(date1.getDate() + i)), y: 0})
        }
        return $q(function(resolve, reject){
            var allData = [];
            $http.get("http://api.openweathermap.org/data/2.5/history/city?q=Genoa,IT&start=" + startTimestamp + "&end=" + todayTimestamp)
                .success(function(pastData){
                    allData = allData.concat(pastData.list);
                    $http.get("http://api.openweathermap.org/data/2.5/forecast/daily?q=Genoa&mode=json&units=metric&cnt=16")
                        .success(function(futureData){
                            allData = allData.concat(futureData.list);
                            allData = allData.filter(function(n){ return n != undefined }); //remove all falsy values
                            if (allData.length === 0) {
                                reject([]);
                                return;
                            }
                            daysForecast.forEach(function(day, index, array){
                                console.log(allData);
                                dayMeasures = allData.filter(function(measure){
                                    if ((Math.floor(measure.dt / (60 * 60 *24))) === (Math.floor(day.x / (1000 * 60 * 60 *24)))){
                                        return 1
                                    }
                                    else{
                                        return 0;
                                    }
                                });
                                if (dayMeasures.length === 0){
                                    day.y = undefined;
                                }
                                else{
                                    if (dayMeasures[0].main){
                                        day.type = "history";
                                        day.y = dayMeasures[Math.floor(dayMeasures.length / 2)].main.temp - 273.1;
                                        day.pressure = dayMeasures[0].main.pressure;
                                    }
                                    else {
                                        day.type = "forecast";
                                        day.y = dayMeasures[0].temp.day;
                                        day.pressure = dayMeasures[0].pressure;
                                    }
                                }
                            });
                        })
                        .error(function () {
                            reject([]);
                        });
                    SERVICE_SCOPE.cachedData = daysForecast;
                    SERVICE_SCOPE.isCached = true;
                    var forecast = [];
                    for (i = 0; i < SERVICE_SCOPE.cachedData.length; i++) {
                        var tdate = new Date(SERVICE_SCOPE.cachedData[i].x);
                        if (tdate.getDate() >= d1.getDate() && tdate.getDate() <= d2.getDate()){
                            forecast.push(SERVICE_SCOPE.cachedData[i]);
                        }
                    }
                    resolve(forecast);
                })
                .error(function () {
                    reject([]);
                })
        })

    };
    this.getForecastForDates = function (date1, date2) {
        if (this.isCached) {
            return $q(function (resolve, reject) {
                var forecast = [];
                for (var i = 0; i < SERVICE_SCOPE.cachedData.length; i++) {
                    var tdate = new Date(SERVICE_SCOPE.cachedData[i].x);
                    if (tdate.getTime() >= date1.getTime() && tdate.getTime() <= date2.getTime()){
                        forecast.push(SERVICE_SCOPE.cachedData[i]);
                    }
                }
                resolve(forecast);
            });
        }
        else{
            return this.cacheForecast(date1, date2);
        }
    }
}]);