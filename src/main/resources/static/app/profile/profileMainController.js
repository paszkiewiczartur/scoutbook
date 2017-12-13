angular.module('scoutbookApp')
.constant("userWallPostsUrl", "http://localhost:8080/api/posts/search/findByUserWall?user_profile=")
.controller('profileMainController', function($rootScope, $scope, $http, $stateParams, 
		profileUrl, userWallPostsUrl, pageSize, postsUrl) {

	$scope.loadPostsOwners = function(){
		$scope.ownersErrors = [];
		angular.forEach($scope.posts, function(item){
			$http.get(postsUrl + item.content.id + "/owner")
			.then(function(response){
				item.owner = response.data;
			}, function(response){
				$scope.ownersErrors.push("There was an error with loading posts owners.");
			});			
		});		
	};
	
	$scope.loadNextUserWallPosts = function(page){
		$http.get(userWallPostsUrl+$stateParams.profileId+"&page="+page+"&size="+pageSize+"&sort=desc")
		.then(function(response){
			$scope.posts = response.data._embedded.posts;			
			$scope.loadPostsOwners();
		}, function(response){	
			$scope.userWallError = "There was an error with loading posts.";
			console.log(response);
		});
	};
	
	(function () {
		$scope.loadNextUserWallPosts(0);
	})();
	
	
    $scope.selectedPage = 0;
    
    $scope.nextPage = function(){
    	$scope.selectedPage = $scope.selectedPage + 1;
    	$scope.loadNextUserWallPosts($scope.selectedPage, false);
    };

    $scope.previousPage = function(){
    	$scope.selectedPage = $scope.selectedPage - 1;
    	$scope.loadNextUserWallPosts($scope.selectedPage, false);
    };
    
    $scope.newPostText = "";
    
    $scope.sendNewPost = function(){
    	if($scope.newPostText != "" && $scope.newPostText != null){
    		var post = {};
    		post.content = $scope.newPostText;
        	post.owner_id = Number($rootScope.profileId);
        	post.category = "USERWALL";
        	post.user_profile_id = Number($stateParams.profileId);
            $http({
                method : 'POST',
                url : postsUrl,
                data : post
            }).then(function(response) {
                console.log(response.data);
                $scope.newPostText = "";
                $scope.loadNextUserWallPosts(0, false);
            }, function(response) {
            	console.log(response.data);
            });
    	}
    };

});