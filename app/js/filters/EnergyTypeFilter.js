app.filter("EnergyTypeFilter", function(){
    return function(input){
        var a = input.split("_").slice(1);
        if(a.indexOf('energy')>=0){
            a.splice(1,1);
        }
        return a.join(" ");
    }
});