document.onscroll=function(e){
    var header = document.getElementById('header');
    var headerText = document.getElementById('header-text');
    if (window.pageYOffset !== 0){
        header.className = "header-moved";
        headerText.className = "header-text";
        document.getElementById("loading-img").className = "no-background";
    }
    else{
        header.className = "container heading";
        headerText.className = "logo";
        document.getElementById("loading-img").className = "power-cord";
    }
};