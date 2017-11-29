angular.module('scoutbookApp')
.constant('RETRIEVEPASSWORD_ENDPOINT', '/api/retrievePassword')
.controller('retrievePasswordController', function($rootScope, $scope, $http, RETRIEVEPASSWORD_ENDPOINT) {
	$scope.encodedAddress = encodeAddress($rootScope.wrongEmail);
	
	function encodeAddress(wrongEmail) {
		console.log(wrongEmail);
		var email = wrongEmail;
	    var index = email.indexOf("@");
	    var begin = email.substr(0, index);
	    var first = begin.substr(0, 1);
	    var last = begin.substr(begin.length - 1, begin.length -1);
	    var encodedEmail = "";
	 	encodedEmail += first;
	    for(var i=0; i<begin.length -2; i++){
	    	encodedEmail += "*";
	    };
	    encodedEmail += last;
	    encodedEmail += email.substr(index, email.length);
	    return encodedEmail;
	};
	
	$scope.sendEmailRetrievingPassword = function(){
		var message = {};
		message.email = $rootScope.wrongEmail;
		$http
		.post(RETRIEVEPASSWORD_ENDPOINT, message)
		.then(function success(successValue) {
			console.log("successValue:" + successValue);
			$scope.emailSent = true;
		}, function error(errorValue) {
			console.log("errorValue:" + errorValue);
			$scope.emailSentError = true;
		});
	};
	
});