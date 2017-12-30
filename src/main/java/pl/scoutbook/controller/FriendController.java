package pl.scoutbook.controller;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import pl.scoutbook.model.UserId;
import pl.scoutbook.model.UserProfile;
import pl.scoutbook.repository.UserProfileRepository;
import pl.scoutbook.validation.UserIdValidator;

@Controller
@RequestMapping("/api/friends")
public class FriendController {

	@Autowired
	private UserProfileRepository userProfileRespository;
	
	//@PostMapping("/addFriend")
	
	
	@PostMapping("/proposition")
	public ResponseEntity<List<UserProfile>> friendProposition(@Valid @RequestBody UserId userId){
		Long id = new Long(userId.getUserId());
		UserProfile user = userProfileRespository.findOne(id);
		List<UserProfile> proposalFriends = new LinkedList<>();
		for(long i = userId.getUserId() -1; i>1 && proposalFriends.size() < 3; i--){
			Optional<UserProfile> proposalFriend = Optional.ofNullable(userProfileRespository.findOne(new Long(i)));
			if(proposalFriend.isPresent())
				if(!isAlreadyAFriend(user, proposalFriend.get())){
					proposalFriend.get().setEvents(new LinkedList<>());
					proposalFriend.get().setGroups(new LinkedList<>());
					proposalFriend.get().setFriends(new LinkedList<>());
					proposalFriends.add(proposalFriend.get());
				}
		}
		return new ResponseEntity<List<UserProfile>>(proposalFriends, new HttpHeaders(), HttpStatus.ACCEPTED);
	}
	
	private boolean isAlreadyAFriend(UserProfile user, UserProfile proposalFriend){
		boolean found = false;
		for(UserProfile friend : user.getFriends()){
			if(friend.getId() == proposalFriend.getId()){
				found = true;
				break;
			}
		}
		
		return found;
	}
	
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(new UserIdValidator(userProfileRespository));
    }
}