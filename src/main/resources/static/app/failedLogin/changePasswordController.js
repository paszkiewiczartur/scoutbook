angular.module('scoutbookApp')
.constant('changePasswordUrl', '/api/changePassword')
.controller('changePasswordController', function($rootScope, $scope, $stateParams, $http, changePasswordUrl) {
	
	$scope.responseErrors = {};
	
	$scope.sendPassword = function(){
		$scope.differentPassword = false;
		if($scope.password1 == $scope.password2){
			
			var message = {};
			message.code = $stateParams.code;
			message.password = $scope.password1;
			$http
			.post(changePasswordUrl, message)
			.then(function success(successValue) {
				console.log("successValue:" + successValue);
				$scope.password1 = "";
				$scope.password2 = "";
				$scope.passwordSent = true;
			}, function error(errorValue) {
				$scope.password1 = "";
				$scope.password2 = "";
				var error = angular.copy(errorValue);
				console.log(error);
				var response = errorValue.data.errors;
            	console.log(response);
                prepareInputErrors(response);
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