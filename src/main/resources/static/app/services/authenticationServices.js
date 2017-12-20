angular.module('scoutbookApp')
.constant('LOGIN_ENDPOINT', '/login')
.constant("usersUrl", "http://localhost:8080/api/users/search/findByEmail?email=")
.service('AuthenticationService', function($http, LOGIN_ENDPOINT, $rootScope, $state, usersUrl) {
	this.authenticate = function(credentials) {
		prepareProfileId(credentials);
	};
	this.logout = function() {
		delete $http.defaults.headers.post.Authorization;
		$rootScope.authenticated = false;
		authenticated = false;
		$state.go("register");
	};

	var prepareProfileId = function (credentials) {
        $http.get(usersUrl + credentials.email)
        .then(function(response) {
        	$rootScope.profileId = getId(response.data._links.user.href);
        	authorize(credentials);
        }, function(response) {
            $rootScope.infoProfile = "Something went wrong with profile";
        });
    };

	var getId = function (data){
	    var index = data.indexOf("users") + 6;
	    var id = data.substr(index, data.length);
		return id;
	};

	var authorize = function(credentials){
		var authHeader = {Authorization: 'Basic ' + btoa(unescape(encodeURIComponent(credentials.email+':'+credentials.password)))};
		var config = {headers: authHeader};
		$http
		.post(LOGIN_ENDPOINT, {}, config)
		.then(function success(value) {
			var response = angular.copy(value);
			$http.defaults.headers.post.Authorization = authHeader.Authorization;
			$rootScope.authenticated = true;
			authenticated = true;			
			$state.go("home");
		}, function error(reason) {
			console.log('Login error');
			console.log(reason);
			$rootScope.wrongEmail = credentials.email;
			$rootScope.credentials = {};
			$state.go("failedLogin");
		});
	};
});