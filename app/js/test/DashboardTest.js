/**
 * Created by root on 7/24/15.
 */
//request api zeby zwrocilo temp i sprawdz czy siewartosci zgadzajaw scopie
describe("Dashboard page", function() {

    beforeEach(function () {
        module('app');
    });

    var DashboardCtrl,
        scope,
        $httpBackend,
        WeatherService;

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

    it('Valid temperature', function() {
        $httpBackend.get(/http:\/\/api\.openweathermap\.org\/data\/2\.5\/forecast\/daily\?*/);
        $httpBackend.flush();
    });
});