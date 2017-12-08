angular.module('scoutbookApp')
.constant("groupUrl", "http://localhost:8080/api/groups/")
.controller('groupController', function($rootScope, $scope, $http, $stateParams, groupUrl) {

	(function () {
        $http.get(groupUrl + $stateParams.groupId + "/posts")
        .then(function(response) {
            $scope.groupData = response.data._embedded;
            loadPostOwners();
        }, function(response) {
            $scope.info = "Something went wrong";
        });
    })();
		
	var loadPostOwners = function(){
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
	
});