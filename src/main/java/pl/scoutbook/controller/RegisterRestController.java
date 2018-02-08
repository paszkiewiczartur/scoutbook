package pl.scoutbook.controller;

import java.util.LinkedList;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import pl.scoutbook.email.RegisterEmailSender;
import pl.scoutbook.entities.Conversation;
import pl.scoutbook.entities.Event;
import pl.scoutbook.entities.Group;
import pl.scoutbook.entities.Post;
import pl.scoutbook.entities.SavedMessage;
import pl.scoutbook.entities.User;
import pl.scoutbook.entities.UserProfile;
import pl.scoutbook.entities.UserWall;
import pl.scoutbook.model.RegisterDTO;
import pl.scoutbook.repository.ConversationsRepository;
import pl.scoutbook.repository.EventsRepository;
import pl.scoutbook.repository.GroupsRepository;
import pl.scoutbook.repository.PostRepository;
import pl.scoutbook.repository.SavedMessagesRepository;
import pl.scoutbook.repository.UserProfileRepository;
import pl.scoutbook.repository.UserRepository;
import pl.scoutbook.repository.UserWallRepository;
import pl.scoutbook.validation.RestRegisterValidator;


@RestController
@RequestMapping("/api/register")
public class RegisterRestController {
    private UserRepository userRepository;
    private UserProfileRepository userProfileRepository;
    
    @Autowired
    private GroupsRepository groupsRepository;
    @Autowired
    private EventsRepository eventsRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserWallRepository userWallRepository;
    @Autowired
    private ConversationsRepository conversationsRepository;
    @Autowired
    private SavedMessagesRepository savedMessagesRepository;
    @Autowired
    private RegisterEmailSender emailSender;
    
    public RegisterRestController(UserRepository userRepository, UserProfileRepository userProfileRepository) {
        this.userRepository = userRepository;
        this.userProfileRepository = userProfileRepository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void newRegisterDTO(@Valid @RequestBody RegisterDTO registerDTO) {

    		ModelMapper modelMapper = new ModelMapper();
    		UserProfile userProfile = modelMapper.map(registerDTO, UserProfile.class);
    		User user = modelMapper.map(registerDTO, User.class);
    		if(userProfile.getProfileImage() == null)
    			userProfile.setProfileImage("https://upload.wikimedia.org/wikipedia/commons/thumb/4/42/Simpleicons_Interface_user-black-close-up-shape.svg/1000px-Simpleicons_Interface_user-black-close-up-shape.svg.png");
    		if(userProfile.getBackgroundImage() == null)
    			userProfile.setBackgroundImage("http://u.profitroom.pl/hotelmazuria.pl/thumb/1600x900/uploads/ognisko.jpg");
    		userProfile = userProfileRepository.save(userProfile);
    		user.setUserProfile(userProfile);
    		userRepository.save(user);
    		emailSender.sendHTMLEmailWithAttachment(user.getEmail());
    		
    		initUserProfile(userProfile);
    }

    private void initUserProfile(UserProfile user){
		UserProfile friend1 = userProfileRepository.findOne(Long.valueOf(1));
		UserProfile friend2 = userProfileRepository.findOne(Long.valueOf(4));
		user.setFriends(new LinkedList<UserProfile>());
		user.getFriends().add(friend1);
		user.getFriends().add(friend2);
		friend1.getFriends().add(user);
		friend2.getFriends().add(user);
		
		Group group1 = groupsRepository.findOne(Long.valueOf(1));
		Group group2 = groupsRepository.findOne(Long.valueOf(2));
		user.setGroups(new LinkedList<Group>());
		user.getGroups().add(group1);
		user.getGroups().add(group2);
		
		Event event = eventsRepository.findOne(Long.valueOf(1));
		user.setEvents(new LinkedList<Event>());
		user.getEvents().add(event);
		
		userProfileRepository.save(user);
		userProfileRepository.save(friend1);
		userProfileRepository.save(friend2);
		
		Post[] posts = new Post[7];
		for(int i=0; i<7; i++){
			posts[i] = postRepository.findOne(new Long(i+1));
			userWallRepository.save(new UserWall(user, posts[i], false));
		}
		
		Conversation conversation1 = new Conversation();
		conversation1.setUser(user.getId());
		conversation1.setFriend(friend1.getId());

		Conversation conversation2 = new Conversation();
		conversation2.setUser(user.getId());
		conversation2.setFriend(friend2.getId());
		
		Conversation savedConversation1 = conversationsRepository.save(conversation1);
		Conversation savedConversation2 = conversationsRepository.save(conversation2);
		
		SavedMessage[] messages = new SavedMessage[6];
		messages[0] = new SavedMessage("Witaj w Scoutbooku!", friend1.getId(), savedConversation1.getId());
		messages[1] = new SavedMessage("Cześć Michał!", user.getId(), savedConversation1.getId());
		messages[2] = new SavedMessage("Co u Ciebie?", friend1.getId(), savedConversation1.getId());
		messages[3] = new SavedMessage("Witaj w Scoutbooku!", friend2.getId(), savedConversation2.getId());
		messages[4] = new SavedMessage("Cześć Michał!", user.getId(), savedConversation2.getId());
		messages[5] = new SavedMessage("Co u Ciebie?", friend2.getId(), savedConversation2.getId());
		
		for(int i=0; i<6; i++){
			savedMessagesRepository.save(messages[i]);
		}
    }
    
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(new RestRegisterValidator(userRepository));
    }
}