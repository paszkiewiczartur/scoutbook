package pl.scoutbook.controller;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
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

import pl.scoutbook.entities.UserProfile;
import pl.scoutbook.model.ConversationUserDTO;
import pl.scoutbook.model.UserId;
import pl.scoutbook.repository.SavedMessagesRepository;
import pl.scoutbook.repository.UserProfileRepository;
import pl.scoutbook.service.ConversationService;
import pl.scoutbook.service.GeneratorService;
import pl.scoutbook.validation.UserIdValidator;

@Controller
@RequestMapping("/api/friends")
public class FriendController {
	private final static long PAGE_SIZE = 2;
	
	@Autowired
	private UserProfileRepository userProfileRespository;
	
	@Autowired
	private ConversationService conversationService;
	
	@Autowired
	private SavedMessagesRepository savedMessageRepository;
	
	@Autowired
	private GeneratorService generator;
	
	@PostMapping("/messenger")
	public ResponseEntity<List<ConversationUserDTO>> messengerFriends(@Valid @RequestBody UserId userId){
		Long id = new Long(userId.getUserId());
		UserProfile user = userProfileRespository.findOne(id);
		List<ConversationUserDTO> messengerFriends = new LinkedList<>();
		messengerFriends.add(generator.getGeneratorUserProfile());
		ModelMapper modelMapper = new ModelMapper();
		for(UserProfile friend: user.getFriends()){
    		ConversationUserDTO conversationUser = modelMapper.map(friend, ConversationUserDTO.class);
			Long conversationId = conversationService.getConversationId(user.getId(), friend.getId());
			if(conversationId != null){
				conversationUser.setConversation(conversationId);
				messengerFriends.add(conversationUser);
				Long messagesAmount = savedMessageRepository.countMessages(conversationId);
				conversationUser.setPage((long)(Math.ceil((double)(messagesAmount) / (double)(PAGE_SIZE))));
			}
		}
		return new ResponseEntity<List<ConversationUserDTO>>(messengerFriends, new HttpHeaders(), HttpStatus.ACCEPTED);
	}
	
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