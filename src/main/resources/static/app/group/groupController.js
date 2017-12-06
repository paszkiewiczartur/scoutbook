angular.module('scoutbookApp')
.constant("groupUrl", "http://localhost:8080/api/groups/")
.controller('groupController', function($rootScope, $scope, $http, $stateParams, groupUrl) {

	(function () {
        $http.get(groupUrl + $stateParams.groupId)
        .then(function(response) {
            $scope.groupData = response.data;
            preparePostOwners();
        }, function(response) {
            $scope.info = "Something went wrong";
        });
    })();
	
/*	var preparePostOwners = function(){
		var add = (function (data) {
		    var counter = 0;
		    return function (data) { 
		    	counter += 1;
		    	$scope.groupData.posts[counter].owner = data;
		    }
		})();
		for(x in $scope.groupData.posts){
			
			
			$http.get($scope.groupData.posts[x]._links.owner.href)
			.then(function(response){
				//$scope.groupData.posts[x].owner = response.data;
				add(response.data);
			}, function(response){
				$scope.profileErrors = []; 
				$scope.profileErrors[x] = "Wystąpił błąd";
			});
			
		}
	};*/
	
	var preparePostOwners = function(){
		$scope.profileErrors = []; 
		angular.forEach($scope.groupData.posts, function(post){
			$http.get(post._links.owner.href)
			.then(function(response){
				post.owner = response.data;
			}, function(response){
				$scope.profileErrors.push("Wystąpił błąd");
			});			
		});
	};
	
	function makeFunction (x, data){
		return function(){
			$scope.groupData.posts[x].owner = data;
		};
	}
	
	$scope.test = function(){
		var i = 0;
		for(x in $scope.groupData.posts){
			if($scope.groupData.posts[x].owner)
				console.log(i+" "+$scope.groupData.posts[x].owner.firstname + " " + $scope.groupData.posts[x].owner.lastname);			
			i++;
		}		
	};
});