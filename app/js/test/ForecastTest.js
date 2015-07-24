describe("Forecast page", function() {

    beforeEach(function () {
        module('app');
    });

    var ForecastCtrl,
        scope;

    beforeEach(inject(function ($rootScope, $controller) {
        scope = $rootScope.$new();
        ForecastCtrl = $controller('ForecastCtrl', {
            $scope: scope
        });
    }));

    beforeEach(inject(function(_$rootScope_) {
        $rootScope = _$rootScope_;
    }));

    it('Valid DateRange changed on daysRange change', function () {
        scope.daysRange.to = 0;
        expect(scope.dateRange.to.getDate()).toEqual((new Date()).getDate());
    });

    it('Invalid DateRange changed on daysRange change', function () {
        scope.daysRange.to = 5;
        console.log(scope.dateRange, scope.daysRange);
        expect(scope.dateRange.to.getDate()).toEqual((new Date()).getDate());
    });
});
