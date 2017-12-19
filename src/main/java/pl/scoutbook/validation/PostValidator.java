package pl.scoutbook.validation;

import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import pl.scoutbook.model.Post;


@Component("beforeCreatePostValidator")
public class PostValidator implements Validator {
	
	
	public PostValidator(){
	}
	
    @Override
    public boolean supports(Class<?> clazz) {
        return Post.class.equals(clazz);
    }
 
    @Override
    public void validate(Object obj, Errors errors) {
    	System.out.println("inside postValidator");
        Post post = (Post) obj;
        if (checkInputString(post.getContent())) {
            errors.rejectValue("content", PostError.CONTENTEMPTY.toString());
        }
        if (checkInputString(post.getContent())) {
            errors.rejectValue("owner", PostError.OWNEREMPTY.toString());
        }
        if (checkInputString(post.getContent())) {
            errors.rejectValue("category", PostError.CATEGORYEMPTY.toString());
        }
        if(checkCategories(post)){
        	errors.rejectValue("category", PostError.TOOMANYCATEGORIES.toString());
        }
    }
 
    private boolean checkInputString(String input) {
        return (input == null || input.trim().length() == 0);
    }

    private boolean checkCategories(Post post){
    	boolean valid = false;
    	int categories = 0;
    	if(Optional.ofNullable(post.getEvent()).isPresent()) categories++;
    	if(Optional.ofNullable(post.getGroup()).isPresent()) categories++;
    	if(Optional.ofNullable(post.getUserProfile()).isPresent()) categories++;
    	if(categories > 1) valid = true;
    	return valid;
    }
    
}
