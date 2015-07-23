describe("Login page", function() {

    beforeEach(function () {
        module('app');
    });

    var HomepageCtrl,
        scope;

    beforeEach(inject(function ($rootScope, $controller) {
        scope = $rootScope.$new();
        HomepageCtrl = $controller('HomepageCtrl', {
            $scope: scope
        });
    }));

    beforeEach(inject(function(_$rootScope_) {
        $rootScope = _$rootScope_;
    }));

    it('User is initally not logged in.', function () {
        expect($rootScope.isLoggedIn).toEqual(false);
    });

    it('Does not accept wrong credentials', function () {
        var password = "admin",
            login = "wrong";
        scope.login = login;
        scope.password = password;
        scope.logIn();
        expect($rootScope.isLoggedIn).toEqual(false);
    });

    it('Accepts valid credentials', function () {
        var password = "admin",
            login = "admin";
        scope.login = login;
        scope.password = password;
        scope.logIn();
        expect($rootScope.isLoggedIn).toEqual(true);
    });
});
