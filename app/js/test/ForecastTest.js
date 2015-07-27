describe("Forecast page", function() {

    beforeEach(function () {
        module('app');
    });

    var ForecastCtrl,
        scope,
        $httpBackend;

    beforeEach(inject(function ($rootScope, $controller) {
        scope = $rootScope.$new();
        ForecastCtrl = $controller('ForecastCtrl', {
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

    it('Valid DateRange changed on daysRange change', function () {
        scope.daysRange.to = 0;
        scope.daysRange.from = -2;
        $httpBackend.whenGET(/http:\/\/api\.openweathermap\.org\/data\/2\.5\/.*/).respond(function () {
            return [200, ['success'], {}];
        });
        scope.$digest();
        $httpBackend.flush();
        expect(scope.dateRange.to.getDate()).toEqual((new Date()).getDate());
        expect(scope.dateRange.from.getDate()).toEqual((new Date()).getDate() - 2);
    });

    it('Invalid DateRange changed on daysRange change', function () {
        $httpBackend.whenGET(/http:\/\/api\.openweathermap\.org\/data\/2\.5\/.*/).respond(function () {
                return [200, ['success'], {}];
        });
        scope.daysRange.to = 5;
        scope.$digest();
        $httpBackend.flush();
        expect(scope.dateRange.to.getDate()).not.toBe((new Date()).getDate());
    });
    it('Correctly parses todays weather', function () {
        var todaysDate = new Date(),
            todaysTimestamp = Math.floor(todaysDate.getTime() / 1000),
            fakeResponse = {
                cod: 200,
                list: [
                    {"main":{"temp":299.09,"pressure":1014,"humidity":69,"temp_min":296.71,"temp_max":301.15},"wind":{"speed":1.5,"deg":20,"var_beg":340,"var_end":40},"clouds":{"all":0},"weather":[{"id":800,"main":"Clear","description":"Sky is Clear","icon":"01n"}],"dt": todaysTimestamp},
                ]
            };
        $httpBackend.whenGET(/http:\/\/api\.openweathermap\.org\/data\/2\.5\/.*/).respond(function () {
            return [200, ['success'], {data: fakeResponse}];
        });
        scope.daysRange.to = 0;
        scope.daysRange.from = 0;
        scope.$digest();
        $httpBackend.flush();
        console.log(scope.config);
        expect(scope.config.data[1].dataPoints[0].x).toBe(todaysDate.getDate());
    })
});
