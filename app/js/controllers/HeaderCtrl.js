app.controller("HeaderCtrl", ['$scope','$rootScope', function ($scope, $rootScope) {
    console.log($rootScope);
    $rootScope.$on('$viewContentLoading',
        function(event, viewConfig){
            var icon = document.getElementById("loading-img");
            icon.className = icon.className + " rot-anim";
        });
}]);