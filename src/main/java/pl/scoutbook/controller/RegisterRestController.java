package pl.scoutbook.controller;

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
import pl.scoutbook.model.RegisterDTO;
import pl.scoutbook.model.User;
import pl.scoutbook.model.UserProfile;
import pl.scoutbook.repository.UserProfileRepository;
import pl.scoutbook.repository.UserRepository;
import pl.scoutbook.validation.RestRegisterValidator;


@RestController
@RequestMapping("/api/register")
public class RegisterRestController {
    private UserRepository userRepository;
    private UserProfileRepository userProfileRepository;
    
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
    	
    		UserProfile savedUserProfile = userProfileRepository.save(userProfile);
    		user.setUserProfile(savedUserProfile);
    		userRepository.save(user);
    		emailSender.sendEmailWithAttachment(user.getEmail());
    		
    }

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(new RestRegisterValidator(userRepository));
    }
}