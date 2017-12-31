package pl.scoutbook.controller;

import java.time.LocalDate;
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
import org.springframework.web.bind.annotation.ResponseStatus;

import pl.scoutbook.entities.UserProfile;
import pl.scoutbook.model.AddFriend;
import pl.scoutbook.model.UserId;
import pl.scoutbook.repository.UserProfileRepository;
import pl.scoutbook.validation.AddFriendValidator;
import pl.scoutbook.validation.UserIdValidator;

@Controller
@RequestMapping("/api/friends")
public class FriendController {

	@Autowired
	private UserProfileRepository userProfileRespository;
	
	@PostMapping("/birthday")
	public ResponseEntity<List<UserProfile>> birthdayFriends(@Valid @RequestBody UserId userId){
		Long id = new Long(userId.getUserId());
		UserProfile user = userProfileRespository.findOne(id);
		List<UserProfile> birthdayFriends = new LinkedList<>();
		LocalDate today = LocalDate.now();
		for(UserProfile friend: user.getFriends()){
			LocalDate birthday = friend.getBirthday();
			if(birthday.getDayOfMonth() == today.getDayOfMonth() && birthday.getMonthValue() == today.getMonthValue()){
				addEmptyArrays(friend);
				birthdayFriends.add(friend);				
			}
		}
		return new ResponseEntity<List<UserProfile>>(birthdayFriends, new HttpHeaders(), HttpStatus.ACCEPTED);
	}
	
	@PostMapping("/proposition")
	public ResponseEntity<List<UserProfile>> friendProposition(@Valid @RequestBody UserId userId){
		Long id = new Long(userId.getUserId());
		UserProfile user = userProfileRespository.findOne(id);
		List<UserProfile> proposalFriends = new LinkedList<>();
		for(long i = userId.getUserId() - 1; i>1 && proposalFriends.size() < 3; i--){
			Optional<UserProfile> proposalFriend = Optional.ofNullable(userProfileRespository.findOne(new Long(i)));
			if(proposalFriend.isPresent())
				if(!isAlreadyAFriend(user, proposalFriend.get())){
					addOptionalEmptyArrays(proposalFriend);
					proposalFriends.add(proposalFriend.get());
				}
		}
		for(long i = userId.getUserId() + 1; i <= userProfileRespository.findMaxId() && proposalFriends.size() <3; i++){
			Optional<UserProfile> proposalFriend = Optional.ofNullable(userProfileRespository.findOne(new Long(i)));
			if(proposalFriend.isPresent())
				if(!isAlreadyAFriend(user, proposalFriend.get())){
					addOptionalEmptyArrays(proposalFriend);
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
	
	private void addOptionalEmptyArrays(Optional<UserProfile> proposalFriend){
		proposalFriend.get().setEvents(new LinkedList<>());
		proposalFriend.get().setGroups(new LinkedList<>());
		proposalFriend.get().setFriends(new LinkedList<>());		
	}

	private void addEmptyArrays(UserProfile proposalFriend){
		proposalFriend.setEvents(new LinkedList<>());
		proposalFriend.setGroups(new LinkedList<>());
		proposalFriend.setFriends(new LinkedList<>());		
	}
	
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(new UserIdValidator(userProfileRespository));
    }
}