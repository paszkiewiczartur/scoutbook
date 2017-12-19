package pl.scoutbook.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import pl.scoutbook.model.Post;
import pl.scoutbook.model.UserProfile;
import pl.scoutbook.model.UserWall;
import pl.scoutbook.repository.PostRepository;
import pl.scoutbook.repository.UserWallRepository;

@Controller
public class PostController {
	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private UserWallRepository userWallRepository;
	
    @PostMapping("/api/posts")
    @ResponseStatus(HttpStatus.CREATED)
    public void addPost(@Valid @RequestBody Post post) {
    	Post savedPost = postRepository.save(post);
    	if(Optional.ofNullable(post.getUserProfile()).isPresent()){
    		UserWall userWall = new UserWall();
    		userWall.setUserProfile(post.getUserProfile());
    		userWall.setPost(savedPost);
    		userWall.setShown(false);
    		userWallRepository.save(userWall);
    		List<UserProfile> friends = post.getUserProfile().getFriends();
    		for(UserProfile friend: friends){
    			UserWall userWallFriend = new UserWall();
    			userWallFriend.setUserProfile(friend);
    			userWallFriend.setPost(savedPost);
    			userWallFriend.setShown(false);
    			userWallRepository.save(userWallFriend);
    		}
    	} else if(Optional.ofNullable(post.getGroup()).isPresent()){
    		List<UserProfile> members = post.getGroup().getUsers();
    		for(UserProfile member: members){
    			UserWall userWall = new UserWall();
    			userWall.setUserProfile(member);
    			userWall.setPost(savedPost);
    			userWall.setShown(false);
    			userWallRepository.save(userWall);
    		}
    	} else {
    		List<UserProfile> members = post.getEvent().getUsers();
    		for(UserProfile member: members){
    			UserWall userWall = new UserWall();
    			userWall.setUserProfile(member);
    			userWall.setPost(savedPost);
    			userWall.setShown(false);
    			userWallRepository.save(userWall);
    		}    		
    	}
    }   
}