// Include gulp
var gulp = require('gulp'); 

// Include Our Plugins
var jshint = require('gulp-jshint');
var concat = require('gulp-concat');
var uglify = require('gulp-uglify');
var rename = require('gulp-rename');
var webserver = require('gulp-webserver');

// Lint Task
gulp.task('lint', function() {
    return gulp.src('js/*.js')
        .pipe(jshint())
        .pipe(jshint.reporter('default'));
});

// Concatenate & Minify JS
gulp.task('vendor', function(){
    return gulp.src([
        "bower_components/angularjs/angular.js",
        "bower_components/angular-ui-router/release/angular-ui-router.js",
        "bower_components/Chart.js/Chart.js",
        "js/canvasjs.min.js",
        "bower_components/canvasjsDirective/canvasjsDirective.js",
        "bower_components/angular-notify/dist/angular-notify.min.js",
        "bower_components/ng-range-slider/dist/ng-range-slider.js"
        ])
        .pipe(concat('vendor.js'))
        .pipe(gulp.dest('dist'))
        .pipe(rename('vendor.min.js'))
        .pipe(uglify())
        .pipe(gulp.dest('dist'));
});
gulp.task('scripts', function() {
    return gulp.src([
        "js/app.js",
        "js/controllers/ForecastCtrl.js",
        "js/controllers/HeaderCtrl.js",
        "js/controllers/HomepageCtrl.js",
        "js/controllers/EnergyNowCtrl.js",
        "js/controllers/DashboardCtrl.js",
        "js/state.js",
        "js/services/RESTUrlService.js",
        "js/services/WeatherService.js",
        "js/services/ChartFactory.js",
        "js/services/RESTEnergyService.js",
        "js/filters/EnergyTypeFilter.js",
        "js/ScrollMenu.js"])
        .pipe(concat('application.js'))
        .pipe(gulp.dest('dist'))
        .pipe(rename('application.min.js'))
        .pipe(uglify())
        .pipe(gulp.dest('dist'));
});
gulp.task('stylesheets', function() {
    return gulp.src('css/**/*.css')
    .pipe(concat('stylesheets.css'))
    .pipe(gulp.dest('dist'));
});
gulp.task('watch', function () {
    console.log("preparing stylesheets...");
    gulp.watch('css/**/*.css', ['stylesheets']);
    gulp.watch('js/**/*.js', ['scripts']);
});
gulp.task('webserver', function() {
    gulp.src('.')
        .pipe(webserver({
            livereload: true,
            directoryListing: true,
            open: true
        }));
});
gulp.task('copy', function () {
   gulp.src([
       'css/fonts/*'
   ]).pipe(gulp.dest('dist/css/fonts'));

    gulp.src([
        'js/partials/**/*'
    ]).pipe(gulp.dest('dist/js/partials'));

    gulp.src([
        'img/**/*'
    ]).pipe(gulp.dest('dist/img'));
});

// Default Task
gulp.task('default', ['scripts', 'vendor', 'stylesheets', 'copy']);