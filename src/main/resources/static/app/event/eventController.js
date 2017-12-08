angular.module('scoutbookApp')
.constant("eventsUrl", "http://localhost:8080/api/events/")
.controller('eventController', function($rootScope, $scope, $http, $stateParams, eventsUrl, pageSize) {
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
	
});