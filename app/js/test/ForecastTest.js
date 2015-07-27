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

    //it('Invalid DateRange changed on daysRange change', function () {
    //    $httpBackend.whenGET(/http:\/\/.*/).respond(function () {
    //        return [200, ['success'], {}];
    //    });
    //    $httpBackend.whenGET(/http:\/\/.*/).respond(function () {
    //        return [200, ['success'], {}];
    //    });
    //    scope.daysRange.to = 5;
    //    scope.$digest();
    //    $httpBackend.flush();
    //    expect(scope.dateRange.to.getDate()).not.toBe((new Date()).getDate());
    //});
});
