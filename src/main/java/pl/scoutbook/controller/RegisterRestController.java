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
import pl.scoutbook.entities.Event;
import pl.scoutbook.entities.Group;
import pl.scoutbook.entities.Post;
import pl.scoutbook.entities.User;
import pl.scoutbook.entities.UserProfile;
import pl.scoutbook.entities.UserWall;
import pl.scoutbook.model.RegisterDTO;
import pl.scoutbook.repository.EventsRepository;
import pl.scoutbook.repository.GroupsRepository;
import pl.scoutbook.repository.PostRepository;
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
    		UserProfile savedUserProfile = userProfileRepository.save(userProfile);
    		user.setUserProfile(savedUserProfile);
    		userRepository.save(user);
    		emailSender.sendEmailWithAttachment(user.getEmail());
    		
    		initUserProfile(savedUserProfile);
    }

    private void initUserProfile(UserProfile user){
		UserProfile friend1 = userProfileRepository.findOne(new Long(1));
		UserProfile friend2 = userProfileRepository.findOne(new Long(4));
		user.setFriends(new LinkedList<UserProfile>());
		user.getFriends().add(friend1);
		user.getFriends().add(friend2);
		friend1.getFriends().add(user);
		friend2.getFriends().add(user);
		
		Group group1 = groupsRepository.findOne(new Long(1));
		Group group2 = groupsRepository.findOne(new Long(2));
		user.setGroups(new LinkedList<Group>());
		user.getGroups().add(group1);
		user.getGroups().add(group2);
		
		Event event = eventsRepository.findOne(new Long(1));
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
    }
    
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(new RestRegisterValidator(userRepository));
    }
}