package pl.scoutbook.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import pl.scoutbook.model.UserId;
import pl.scoutbook.repository.UserProfileRepository;

@Component("beforeCreateUserIdValidator")
public class UserIdValidator implements Validator{
	private UserProfileRepository userProfileRepository;
	
	public UserIdValidator(UserProfileRepository userProfileRepository){
		this.userProfileRepository = userProfileRepository;
	}
	
    @Override
    public boolean supports(Class<?> clazz) {
        return UserId.class.equals(clazz);
    }
    
    @Override
    public void validate(Object obj, Errors errors) {
    	System.out.println("inside userIdValidator");
        UserId userId = (UserId) obj;
        if (userId.getUserId() == null) {
            errors.rejectValue("userId", "emptyUserProfileId");
            System.out.println("emptyUserProfileId");
        } else {
        	Long id = new Long(userId.getUserId());
        	if(userProfileRepository.findOne(id) == null){
        		System.out.println("notFoundUserProfileId");
        		errors.rejectValue("userId", "notFoundUserProfileId");
        	}
        }
    }
}
