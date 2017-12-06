angular.module('scoutbookApp')
.constant("registerUrl", "http://localhost:8080/api/register")
.controller('registerController', function($rootScope, $state, $scope, $http, registerUrl, AuthenticationService) {
	$scope.credentials = {};
	$scope.days = [];
	for(var i = 1; i < 32; i++ ){
		$scope.days.push(i);
	}
	$scope.months = ["styczeń", "luty", "marzec", "kwiecień", "maj", "czerwiec", "lipiec", "sierpień", "wrzesień", "październik", "listopad", "grudzień" ];
	$scope.years = [];
	var today = new Date();
	var currentYear = today.getFullYear();
	for(var i = currentYear; i > 1904; i-- ){
		$scope.years.push(i);
	}
	$scope.birthday = {};
	$scope.birthday.day = today.getDate(); 
	$scope.birthday.month = $scope.months[today.getMonth()];
	$scope.birthday.year = currentYear - 25;
	
	$scope.registerData = {};
	$scope.successRegister = false;
	$scope.inputErrors = {};	
	
	$scope.login = function() {
		AuthenticationService.authenticate($scope.credentials);
	}
	
    var sendRegisterData = function (registerData) {
    	$scope.inputErrors  = {};
        var data = angular.copy(registerData);
        $http.post(registerUrl, data)
          .then(
            function (successData) {
            	console.log(successData);
            	$scope.successRegister = true;
            	$scope.birthday = {};
            	$scope.registerData = {};              
            },
            function (errors) {
            	var response = errors.data.errors;
            	console.log(response);
                prepareInputError(response);
            });
    };
    

    var prepareInputError = function(response){
    	var errors = angular.copy(response);
    	for(var i = 0; i < errors.length; i++){
    		if(errors[i] == "birthdayEmpty") $scope.inputErrors.birthday = true;
    		if(errors[i] == "birthdayNotPast") $scope.inputErrors.birthday = true;
    		if(errors[i] == "genderEmpty") $scope.inputErrors.gender = true;
    		if(errors[i] == "emailWrongPattern") $scope.inputErrors.emailWrongPattern = true;
    		if(errors[i] == "emailEmpty") $scope.inputErrors.emailEmpty = true;
    		if(errors[i] == "emailDuplicate") $scope.inputErrors.emailDuplicate = true;
    		if(errors[i] == "passwordEmpty") $scope.inputErrors.password = true;
    		if(errors[i] == "lastnameEmpty") $scope.inputErrors.lastname = true;
    		if(errors[i] == "firstnameEmpty") $scope.inputErrors.firstname = true;
    	}
    };
    
    $scope.prepareAndSendRegisterData = function(){
    	var birthday = "";
    	birthday += $scope.birthday.year;
    	birthday += "-";
    	var monthNumber;
    	for(monthNumber = 0; monthNumber < 12; monthNumber++){
    		if($scope.birthday.month == $scope.months[monthNumber]) break;
    	}
    	monthNumber++;
    	if(monthNumber < 10) birthday += "0";
    	birthday += monthNumber;
    	birthday += "-";
    	if($scope.birthday.day < 10) birthday += "0";
    	birthday += $scope.birthday.day;
    	
    	$scope.registerData.birthday = birthday;
    	
    	sendRegisterData($scope.registerData);
    };
    

});