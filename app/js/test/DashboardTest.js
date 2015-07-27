/**
 * Created by root on 7/24/15.
 */
describe("Dashboard page", function() {

    beforeEach(function () {
        module('app');
    });

    var DashboardCtrl,
        scope,
        $httpBackend;

    beforeEach(inject(function ($rootScope, $controller) {
        scope = $rootScope.$new();
        DashboardCtrl = $controller('DashboardCtrl', {
            $scope: scope
        });
    }));

    beforeEach(inject(function(_$rootScope_, $injector) {
        $rootScope = _$rootScope_;
        $httpBackend = $injector.get('$httpBackend');
    }));

    afterEach(function() {
        $httpBackend.verifyNoOutstandingExpectation();
        $httpBackend.verifyNoOutstandingRequest();
    });

    it('Valid weather', function() {
        scope.weather.name = 'Genoa';
        $httpBackend.whenGET(/http:\/\/localhost.*/)
            .respond(function () {
                return [200, []];
            });
        $httpBackend.whenGET(/http:\/\/api\.openweathermap\.org\/data\/2\.5\/.*/)
            .respond(function () {
                return [200, {"coord":{"lon":8.96,"lat":44.41},"weather":[{"id":802,"main":"Clouds","description":"scattered clouds"
                    ,"icon":"03d"}],"base":"stations","main":{"temp":299.89,"pressure":1006,"humidity":57,"temp_min":298.15,"temp_max":302.04},"visibility":10000,
                    "wind":{"speed":6.2,"deg":120},"clouds":{"all":40},"dt":1437985894
                    ,"sys":{"type":1,"id":5798,"message":0.01,"country":"IT","sunrise":1437969935,"sunset":1438023306},"id"
                        :6542282,"name":"Genoa","cod":200}];
            });
        $httpBackend.flush();
        expect(scope.weather.main.temp).toEqual(26);
        expect(scope.weather.wind.dir).toEqual("ESE");
    });

    it('Valid device usage', function() {
        scope.deviceUsage.measured = 10;
        $httpBackend.whenGET(/http:\/\/localhost:8080\/energy\/stat\/total_active_consumed\/.*/)
            .respond(function () {
                return [200, [
                    {"id": "1913061376", "value": 5312.224999999999, "timestamp": 1437948000000, "type": "", "delta": 1.8160523904248667, "prediction": false},
                    {"id": "1913061376", "value": 5313.983333333333, "timestamp": 1437951600000, "type": "", "delta": 1.8160523904248667, "prediction": false},
                    {"id": "1913061376", "value": 5315.740000000001, "timestamp": 1437955200000, "type": "", "delta": 1.8160523904248667, "prediction": false},
                    {"id": "1913061376", "value": 5317.498333333333, "timestamp": 1437958800000, "type": "", "delta": 1.8160523904248667, "prediction": false},
                    {"id": "1913061376", "value": 5319.2525, "timestamp": 1437962400000, "type": "", "delta": 1.8160523904248667, "prediction": false},
                    {"id": "1913061376", "value": 5321.013333333332, "timestamp": 1437966000000, "type": "", "delta": 1.8160523904248667, "prediction": false},
                    {"id": "1913061376", "value": 5322.538333333334, "timestamp": 1437969600000, "type": "", "delta": 1.8160523904248667, "prediction": false},
                    {"id": "1913061376", "value": 5324.263333333333, "timestamp": 1437973200000, "type": "", "delta": 1.8160523904248667, "prediction": false},
                    {"id": "1913061376", "value": 5326.036666666666, "timestamp": 1437976800000, "type": "", "delta": 1.8160523904248667, "prediction": false},
                    {"id": "1913061376", "value": 5327.796666666666, "timestamp": 1437980400000, "type": "", "delta": 1.8160523904248667, "prediction": false},
                    {"id": "1913061376", "value": 5329.559166666665, "timestamp": 1437984000000, "type": "", "delta": 1.8160523904248667, "prediction": false},
                    {"id": "1913061376", "value": 5331.987499999999, "timestamp": 1437987600000, "type": "", "delta": 1.8160523904248667, "prediction": false},
                    {"id": "1913061376", "value": 5333.576666666667, "timestamp": 1437991200000, "type": "", "delta": 1.8160523904248667, "prediction": false},
                    {"id": "1913061376", "value": 5335.392719057092, "timestamp": 1437994800000, "type": "", "delta": 1.8160523904248667, "prediction": true},
                    {"id": "1913061376", "value": 5337.208771447517, "timestamp": 1437998400000, "type": "", "delta": 1.8160523904248667, "prediction": true},
                    {"id": "1913061376", "value": 5339.024823837943, "timestamp": 1438002000000, "type": "", "delta": 1.8160523904248667, "prediction": true},
                    {"id": "1913061376", "value": 5340.840876228368, "timestamp": 1438005600000, "type": "", "delta": 1.8160523904248667, "prediction": true},
                    {"id": "1913061376", "value": 5342.656928618793, "timestamp": 1438009200000, "type": "", "delta": 1.8160523904248667, "prediction": true},
                    {"id": "1913061376", "value": 5344.4729810092185, "timestamp": 1438012800000, "type": "", "delta": 1.8160523904248667, "prediction": true},
                    {"id": "1913061376", "value": 5346.289033399644, "timestamp": 1438016400000, "type": "", "delta": 1.8160523904248667, "prediction": true},
                    {"id": "1913061376", "value": 5348.105085790069, "timestamp": 1438020000000, "type": "", "delta": 1.8160523904248667, "prediction": true},
                    {"id": "1913061376", "value": 5349.921138180494, "timestamp": 1438023600000, "type": "", "delta": 1.8160523904248667, "prediction": true},
                    {"id": "1913061376", "value": 5351.73719057092, "timestamp": 1438027200000, "type": "", "delta": 1.8160523904248667, "prediction": true},
                    {"id": "1913061376", "value": 5353.553242961345, "timestamp": 1438030800000, "type": "", "delta": 1.8160523904248667, "prediction": true}
                ]];
            });
        $httpBackend.whenGET(/http:\/\/api\.openweathermap\.org\/data\/2\.5\/.*/)
            .respond(function () {
                return [200, {"coord":{"lon":8.96,"lat":44.41},"weather":[{"id":802,"main":"Clouds","description":"scattered clouds"
                    ,"icon":"03d"}],"base":"stations","main":{"temp":299.89,"pressure":1006,"humidity":57,"temp_min":298.15,"temp_max":302.04},"visibility":10000,
                    "wind":{"speed":6.2,"deg":120},"clouds":{"all":40},"dt":1437985894
                    ,"sys":{"type":1,"id":5798,"message":0.01,"country":"IT","sunrise":1437969935,"sunset":1438023306},"id"
                        :6542282,"name":"Genoa","cod":200}];
            });
        $httpBackend.whenGET(/http:\/\/.*/)
            .respond(function () {
                return [200, []];
            });
        $httpBackend.flush();

        expect(scope.deviceUsage.measured.toPrecision(4)).toEqual('21.35');
        expect(scope.deviceUsage.predicted.toPrecision(4)).toEqual('41.33');
    });
});