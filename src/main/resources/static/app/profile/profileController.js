angular.module('scoutbookApp')
.constant("profileUrl", "http://localhost:8080/api/userProfiles/")
.constant("userWallPostsUrl", "http://localhost:8080/api/posts/search/findByUserWall?user_profile=")
.controller('profileController', function($rootScope, $scope, $http, $stateParams, profileUrl, userWallPostsUrl, pageSize, postsUrl) {
	$scope.backgroundImage = "http://u.profitroom.pl/hotelmazuria.pl/thumb/1600x900/uploads/ognisko.jpg";
	$scope.profileImage = "https://upload.wikimedia.org/wikipedia/commons/thumb/4/42/Simpleicons_Interface_user-black-close-up-shape.svg/1000px-Simpleicons_Interface_user-black-close-up-shape.svg.png";

	$scope.loadPostsOwners = function(){
		$scope.ownersErrors = [];
		angular.forEach($scope.posts, function(item){
			$http.get(postsUrl + item.content.id + "/owner")
			.then(function(response){
				item.owner = response.data;
			}, function(response){
				$scope.ownersErrors.push("Wystąpił błąd z pobranie właścicieli postów.");
			});			
		});		
	};
	
	$scope.loadNextUserWallPosts = function(page){
		console.log(userWallPostsUrl+$stateParams.profileId+"&page="+page+"&size="+pageSize+"&sort=desc");
		$http.get(userWallPostsUrl+$stateParams.profileId+"&page="+page+"&size="+pageSize+"&sort=desc")
		.then(function(response){
			console.log(response);
			$scope.posts = response.data._embedded.posts;			
			console.log($scope.posts);
			$scope.loadPostsOwners();
		}, function(response){	
			$scope.userWallError = "Wystąpił błąd z pobraniem postów.";
			console.log(response);
		});
	};
	
	(function () {
		console.log(profileUrl + $stateParams.profileId);
        $http.get(profileUrl + $stateParams.profileId)
        .then(function(response) {
            $scope.profile = angular.copy(response.data);
            console.log($scope.profile);
        }, function(response) {
            $scope.infoProfile = "Something went wrong with profile";
        });
    })();

	(function () {
		$scope.loadNextUserWallPosts(0);
	})();
	
});