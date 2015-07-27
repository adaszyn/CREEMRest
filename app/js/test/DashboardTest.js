/**
 * Created by root on 7/24/15.
 */
//sprawdza dzialanie funkcji pobierajacej temperatury
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
        scope.cityForecast();
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

});