angular.module('scoutbookApp')
.constant("messengerUsersUrl", "/api/friends/messenger")
.constant("messagesUrl", "/api/messages/search/findByConversation?conversation=")
.constant("messagesPageSize", 2)
.controller('messengerController', function($rootScope, $scope, $http, $q, $state,
		messagesUrl, messengerUsersUrl, messagesPageSize) {
	
	$scope.loadMessages = function(){
		var promises = []
		$scope.messagesErrors = [];
		angular.forEach($scope.conversationUsers, function(conversationUser){
			if(conversationUser.id != 0){
				var promise = $http.get(getMessageUrl(conversationUser.conversation, conversationUser.page))
				.then(function(response){
					for(var i=0; i<$scope.conversationUsers.length; i++){
						if(response.data._embedded.messages.length > 0){
							if($scope.conversationUsers[i].conversation == response.data._embedded.messages[0].conversation){
								$scope.conversationUsers[i].conversationMessages = angular.copy(response.data._embedded.messages);
								break;
							}							
						}
					}
				}, function(response){
					console.log(conversationUser.conversation + " error");
					$scope.messagesErrors.push("There was an error with loading the post.");
				});
				promises.push(promise);
			}
		});
		return $q.all(promises);		
	};
	
	$scope.loadConversationUsers = function(){
		var userId = {};
		userId.userId = $rootScope.profileId;
 		$http({
            method : 'POST',
            url : messengerUsersUrl,
            data : userId
        }).then(function(response) {
        	$scope.conversationUsers = response.data;
        	//$scope.loadConversationMessages();
			$scope.loadMessages().then(function(response){
	        	$scope.setConversation($scope.conversationUsers[0]);
	        	hideLoading();
			}, function (error){	
				console.log("Scoutbook couldn't load history of messages.");
				console.log(error);
				console.log(error.data);
			});
        }, function(response) {
        	console.log("failure!");
        	console.log(response.data);
        	$scope.conversationUsersError = "Something went wrong with loading friends. Try again later.";
        });
	};
	
 	$scope.connect = function(){ 		
 		var url = "/stompMessenger";
 		var sock = new SockJS(url);
 		$scope.stomp = Stomp.over(sock);
 		var payload = JSON.stringify({ 'message': 'Wiadomość testowa!'});
		var connectHeader = {};
		connectHeader.login = "guest";
		connectHeader.passcode = "guest";
		connectHeader.userLogin = $rootScope.userLogin;
		connectHeader.userPassword = $rootScope.userPassword;
		connectHeader.userId = $rootScope.profileId;
 		$scope.stomp.connect(connectHeader, function(frame){
 			console.log("headers:");
 			console.log(frame.headers);
 			var subscription = $scope.stomp.subscribe("/user/queue/receive", handleChatMessage, {}); 			
 		}, function(frame){
 			console.log("Something failed.");
 			console.log(frame);
 		});
 	};
 	
 	var setMessengerUsersMaxHeight = function(){
 		var height = window.innerHeight;
 		console.log("height:"+height)
 		$(".messengerUsers").css("max-height", height-140);
 	};
 	
	(function () {
		$scope.connect();
		$scope.loadConversationUsers();
		setMessengerUsersMaxHeight();
	})();

	
	$scope.loadOlderMessages = function(){
		if($scope.dialogueUser.page > 1 && $scope.dialogueUser.id != 0){
			var page = $scope.dialogueUser.page -1;
			$http.get(getMessageUrl($scope.dialogueUser.conversation, page))
			.then(function(response){
				for(var i=0; i<$scope.conversationUsers.length; i++){
					if($scope.conversationUsers[i].conversation == response.data._embedded.messages[0].conversation){
						for(var j=response.data._embedded.messages.length -1; j>-1; j--){
							$scope.conversationUsers[i].conversationMessages.unshift(response.data._embedded.messages[j]);	
						}
						$scope.conversationUsers[i].page = $scope.conversationUsers[i].page - 1;
						break;
					}
				}
			}, function(response){
				console.log(conversationUser.conversation + " error");
			});
		}
	};
	
 	$scope.setConversation = function(user){
 		$scope.dialogueUser = user;	
 		user.unread = false;
 		scrollDown();
 	};
 	 	
 	$scope.sendMessage = function(){
 		var user = Number($scope.dialogueUser.id);
 		var message = $scope.message;
 		if(angular.isNumber(user) && message && message != ""){
 	 		var chatMessage = JSON.stringify({'user':user,'message':message});
 	 		$scope.stomp.send("/chat/messenger", {}, chatMessage);
 	 		$scope.message = "";
 	 		var messageToAdd = {};
 	 		messageToAdd.user = $rootScope.profileId;
 	 		messageToAdd.message = message;
 			angular.forEach($scope.conversationUsers, function(conversationUser){
 				if(conversationUser.id == user){
 		 	    	conversationUser.conversationMessages.push(messageToAdd);
 				}
 			});
 			scrollDown();
 			getToTheTop(user);
 		}
 	}
 	
 	var handleChatMessage = function(chatMessage){
 	    if (chatMessage.body) {
 	    	var message = JSON.parse(chatMessage.body);
 			angular.forEach($scope.conversationUsers, function(user){
 				if(user.id == message.user){
 					user.conversationMessages.push(message);
 					if($scope.dialogueUser.id != message.user){
 						user.unread = true;
 					} else{
 						scrollDown();
 					}
 				}
 			});
 	    	var id = message.user;
 	    	getToTheTop(id, true);
 	    } else {
 	       console.log("got empty message");
 	    }
 	};

 	var getMessageUrl = function(conversation, conversationPage){
 		//numeracja stron w spring query zaczyna się od 0
 		var page = conversationPage - 1 ;
 		var url = messagesUrl+conversation+"&page="+page+"&size="+messagesPageSize+"&sort=conversation&conversation.dir=asc";
 		return url;
 	};
 		
 	var getToTheTop = function(id, receive){
 		if($scope.conversationUsers[0].id != id){
 			for(var i=1; i<$scope.conversationUsers.length; i++){
 				if($scope.conversationUsers[i].id == id){
 					var user = angular.copy($scope.conversationUsers[i]);
 					$scope.conversationUsers.splice(i, 1);
 					$scope.conversationUsers.unshift(user);
 				}
 			}
 		}
 		if(receive == true){
 	 		$scope.$apply(); 					 			
 		}
 	};
 	
 	var scrollDown = function(){
		$(".conversation").animate({ scrollTop: ($('.conversation').get(0).scrollHeight + 2000) }, "fast");		
 	};
 	
 	var hideLoading = function(){
 		$(".loading").hide();
 	}
});
