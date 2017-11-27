angular.module('scoutbookApp')
.constant('LOGIN_ENDPOINT', '/login')
.service('AuthenticationService', function($http, LOGIN_ENDPOINT, $rootScope, $state) {
	this.authenticate = function(credentials) {
		var authHeader = {Authorization: 'Basic ' + btoa(unescape(encodeURIComponent(credentials.email+':'+credentials.password)))};
		var config = {headers: authHeader};
		$http
		.post(LOGIN_ENDPOINT, {}, config)
		.then(function success(value) {
			console.log("autoryzacja " + authHeader.Authorization);
			$http.defaults.headers.post.Authorization = authHeader.Authorization;
			$rootScope.authenticated = true;
			authenticated = true;
			$state.go("home");
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
		authenticated = false;
		$state.go("register");
	}
});