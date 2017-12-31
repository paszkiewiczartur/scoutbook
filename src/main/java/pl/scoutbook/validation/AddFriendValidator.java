package pl.scoutbook.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import pl.scoutbook.model.AddFriend;
import pl.scoutbook.repository.UserProfileRepository;

@Component("beforeCreateAddFriendValidator")
public class AddFriendValidator implements Validator{
	private UserProfileRepository userProfileRepository;
	
	public AddFriendValidator(UserProfileRepository userProfileRepository){
		this.userProfileRepository = userProfileRepository;
	}
	
    @Override
    public boolean supports(Class<?> clazz) {
        return AddFriend.class.equals(clazz);
    }
    
    @Override
    public void validate(Object obj, Errors errors) {
        AddFriend addFriend = (AddFriend) obj;
        if (addFriend.getUserId() == null) {
            errors.rejectValue("userId", "emptyUserId");
            System.out.println("emptyUserId");
        } else {
        	Long id = new Long(addFriend.getUserId());
        	if(userProfileRepository.findOne(id) == null){
        		System.out.println("notFoundUserId");
        		errors.rejectValue("userId", "notFoundUserId");
        	}
        }
        if (addFriend.getUserId() == null) {
            errors.rejectValue("friendId", "emptyFriendId");
            System.out.println("emptyFriendId");
        } else {
        	Long id = new Long(addFriend.getUserId());
        	if(userProfileRepository.findOne(id) == null){
        		System.out.println("notFoundFriendId");
        		errors.rejectValue("friendId", "notFoundFriendId");
        	}
        }
    }
}