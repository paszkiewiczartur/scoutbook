angular.module('scoutbookApp')
.constant('CHANGEPASSWORD_ENDPOINT', '/api/changePassword')
.controller('changePasswordController', function($rootScope, $scope, $stateParams, $http, CHANGEPASSWORD_ENDPOINT) {
	
	$scope.responseErrors = {};
	
	$scope.sendPassword = function(){
		if($scope.password1 == $scope.password2){
			
			var message = {};
			message.code = $stateParams.code;
			message.password = $scope.password1;
			$http
			.post(CHANGEPASSWORD_ENDPOINT, message)
			.then(function success(successValue) {
				console.log("successValue:" + successValue);
				$scope.passwordSent = true;
			}, function error(errorValue) {
				var error = angular.copy(errorValue);
				console.log("errorValue:" + error);
				prepareInputErrors(errorValue);
				$scope.errorPassword = true;
			});
		} else{
			$scope.differentPassword = true;
		}
	};
	
    var prepareInputErrors = function(response){
    	var errors = angular.copy(response);
    	for(var i = 0; i < errors.length; i++){
    		if(errors[i] == "codeEmpty") $scope.responseErrors.code = true;
    		if(errors[i] == "codeNotFound") $scope.responseErrors.code = true;
    		if(errors[i] == "passwordEmpty") $scope.responseErrors.password = true;
    		if(errors[i] == "hourPassed") $scope.responseErrors.hourPassed = true;
    	}
    };

});