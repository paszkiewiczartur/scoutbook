angular.module('scoutbookApp')
.constant("eventsUrl", "http://localhost:8080/api/events/")
.controller('eventController', function($rootScope, $scope, $http, $stateParams, eventsUrl, pageSize, postsUrl) {
		
	var loadPosts = function(){
        $http.get(eventsUrl + $stateParams.eventId + "/posts")
        .then(function(response) {
            $scope.eventData.posts = response.data._embedded.posts;
            loadPostOwners();
        }, function(response) {
            $scope.infoPosts = "Something went wrong with posts";
        });
	};
	
	var loadOrganizer = function(){
        $http.get(eventsUrl + $stateParams.eventId + "/organizer")
        .then(function(response) {
            $scope.eventData.organizer = response.data;
        }, function(response) {
            $scope.infoOrganizer = "Something went wrong with organizer";
        });		
	}
	
	var loadPostOwners = function(){
		$scope.profileErrors = []; 
		angular.forEach($scope.eventData.posts, function(post){
			$http.get(post._links.owner.href)
			.then(function(response){
				post.owner = response.data;
			}, function(response){
				$scope.profileErrors.push("Wystąpił błąd");
			});			
		});
	};
	
    $scope.newPostText = "";
    
    $scope.sendNewPost = function(){
    	if($scope.newPostText != "" && $scope.newPostText != null){
    		var post = {};
    		post.content = $scope.newPostText;
        	post.owner_id = Number($rootScope.profileId);
        	post.category = "EVENT";
        	post.events_id = Number($stateParams.eventId);
            $http({
                method : 'POST',
                url : postsUrl,
                data : post
            }).then(function(response) {
                console.log(response.data);
                $scope.newPostText = "";
                loadPosts();
            }, function(response) {
            	console.log(response.data);
            });
    	}
    };

    (function () {
        $http.get(eventsUrl + $stateParams.eventId)
        .then(function(response) {
            $scope.eventData = response.data.content;
            loadOrganizer();
            loadPosts();
        }, function(response) {
            $scope.infoEvent = "Something went wrong with event";
        });		
    })();
	
	
});