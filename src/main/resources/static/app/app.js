'use strict';

var scoutbookApp = angular.module('scoutbookApp', ['ui.router', 'ngResource']);
console.log('module created');
scoutbookApp.config(function($stateProvider, $urlRouterProvider, $locationProvider) {

    $urlRouterProvider.otherwise('/register');

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
            controller: 'registerController'
        })
        .state('failedLogin', {
            url: '/failedLogin',
            templateUrl: 'app/failedLogin/failedLogin.html',
            controller: 'failedLoginController'
        })
        .state('home', {
        	url: '/home',
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
        	controller: 'wallController'
        })
        .state('home.group', {
        	url: '/group',
        	templateUrl: '/app/group/group.html',
        	controller: 'groupController'
        })
        ;
});