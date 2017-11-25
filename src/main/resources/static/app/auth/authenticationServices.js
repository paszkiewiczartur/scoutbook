angular.module('scoutbookApp')
.constant('LOGIN_ENDPOINT', '/login')
.service('AuthenticationService', function($http, LOGIN_ENDPOINT, $rootScope, $state) {
	this.authenticate = function(credentials) {
		var authHeader = {Authorization: 'Basic ' + btoa(credentials.email+':'+credentials.password)};
		var config = {headers: authHeader};
		$http
		.post(LOGIN_ENDPOINT, {}, config)
		.then(function success(value) {
			$http.defaults.headers.post.Authorization = authHeader.Authorization;
			$rootScope.authenticated = true;
			$state.go("home.wall");
		}, function error(reason) {
			console.log('Login error');
			console.log(reason);
			$rootScope.credentials = {};
			$state.go("failedLogin");
		});
	}
	this.logout = function() {
		delete $http.defaults.headers.post.Authorization;
		$rootScope.authenticated = false;
		$state.go("register");
	}
});