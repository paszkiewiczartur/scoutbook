package pl.scoutbook.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import pl.scoutbook.entities.UserProfile;
import pl.scoutbook.model.AddFriend;
import pl.scoutbook.repository.UserProfileRepository;
import pl.scoutbook.validation.AddFriendValidator;

@Controller
public class AddFriendController {

	@Autowired
	private UserProfileRepository userProfileRepository;
	
	@PostMapping("/api/friends/addFriend")
    @ResponseStatus(HttpStatus.CREATED)
	public void addProposalFriend(@Valid @RequestBody AddFriend addFriend){
		UserProfile user = userProfileRepository.findOne(addFriend.getUserId());
		UserProfile friend = userProfileRepository.findOne(addFriend.getFriendId());
		user.getFriends().add(friend);
		friend.getFriends().add(user);
		userProfileRepository.save(user);
		userProfileRepository.save(friend);
	}
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(new AddFriendValidator(userProfileRepository));
    }
}
