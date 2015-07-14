/**
 * Created by wojtek on 7/14/15.
 */
app.controller("EnergyNowCtrl", ['$scope', function(){
    $scope.$on('$viewContentLoaded', function(event){
        setTimeout(function () {
            var icon = document.getElementById("loading-img");
            icon.className = "power-cord";
        }, 500);
    });
}]);