'use strict';

var scoutbookApp = angular.module('scoutbookApp', ['ui.router', 'ngResource']);
var authenticated = false;
console.log('module created');
scoutbookApp.config(function($stateProvider, $urlRouterProvider, $locationProvider) {

    $urlRouterProvider.otherwise('/register');

    $urlRouterProvider.when('/home', '/home/wall');
    
    $stateProvider.state('admin', {
        url: '/admin',
        template: 'Redirecting...<script>window.location="http://localhost:8080/secret"</script>'
   });

    $stateProvider.state('start', {
        url: '/start',
        template: 'Redirecting...<script>window.location="http://localhost:8080/register"</script>'
   });
    
    $locationProvider.html5Mode({
        enabled: true,
        requireBase: false
      });
    
    $stateProvider
        .state('register', {
            url: '/register',
            templateUrl: 'app/register/register.html',
            controller: 'registerController'
        })
        .state('failedLogin', {
            url: '/failedLogin',
            templateUrl: 'app/failedLogin/failedLogin.html',
            controller: 'failedLoginController'
        })
        .state('changePassword', {
            url: '/changePassword/:code',
            templateUrl: 'app/failedLogin/changePassword.html',
            controller: 'changePasswordController'
        })
        .state('retrievePassword', {
            url: '/retrievePassword',
            templateUrl: 'app/failedLogin/retrievePassword.html',
            controller: 'retrievePasswordController'
        })
        .state('home', {
        	url: '/home',
            data: {
                freeRide: false
            },
        	views: {
                '': { templateUrl: '/app/home/home.html' },
                'nav@home': { 
                    templateUrl: '/app/nav/nav.html',
                    controller: 'navController'
                }
        	}
        })
        .state('home.wall', {
        	url: '/wall',
        	templateUrl: '/app/wall/wall.html',
        	controller: 'wallController',
        	resolve: {
                security: ['$q', function($q){
                    if(authenticated == false){
                    	console.log("Not Authorized");
                       return $q.reject("Not Authorized");
                    }
                }]
             }
        })
/*        .state('home.group', {
        	url: '/group',
        	templateUrl: '/app/group/group.html',
        	controller: 'groupController'
        })*/
        .state('home.group', {
            url: '/group/:groupId',
            templateUrl: 'app/group/group.html',
            controller: 'groupController'
        })
        .state('home.profile', {
        	url: '/profile/:profileId',
        	templateUrl: '/app/profile/profile.html',
        	controller: 'profileController'
        })
        .state('home.event', {
        	url: '/event/:eventId',
        	templateUrl: '/app/event/event.html',
        	controller: 'eventController'
        })
        ;
});