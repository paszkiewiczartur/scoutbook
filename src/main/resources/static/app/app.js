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
    
    $locationProvider.html5Mode({
        enabled: true,
        requireBase: false
      });
    
    $stateProvider
        .state('register', {
            url: '/register',
            templateUrl: 'app/register/register.html',
            controller: 'registerController',
            data: {
                freeRide: true
            }
        })
        .state('failedLogin', {
            url: '/failedLogin',
            templateUrl: 'app/failedLogin/failedLogin.html',
            controller: 'failedLoginController',
            data: {
                freeRide: true
            }
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
        .state('home.group', {
        	url: '/group',
        	templateUrl: '/app/group/group.html',
        	controller: 'groupController'
        })
        ;
});