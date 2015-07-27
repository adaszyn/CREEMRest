app.controller("HomepageCtrl", ['$scope', '$rootScope', '$state', function($scope, $rootScope, $state){
    $scope.$on('$viewContentLoaded', function(event){
        setTimeout(function () {
            var icon = document.getElementById("loading-img");
            icon.className = "power-cord";
        }, 500);
    });
    $rootScope.isLoggedIn = false;
    $scope.logIn = function () {
        if ($scope.login === "admin" && $scope.password === "admin") {
            $rootScope.isLoggedIn = true;
            $state.go('root.dashboard');
        }
    }
}]);