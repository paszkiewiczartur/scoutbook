package pl.scoutbook.validation;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import pl.scoutbook.entities.NewPasswordCode;
import pl.scoutbook.model.ChangePassword;
import pl.scoutbook.repository.NewPasswordCodeRepository;


//@Component("beforeCreateChangePasswordValidator")
@Component
public class ChangePasswordValidator implements Validator {
	
	NewPasswordCodeRepository newPasswordCodeRepository;
	
	public ChangePasswordValidator(NewPasswordCodeRepository newPasswordCodeRepository){
		this.newPasswordCodeRepository = newPasswordCodeRepository;
	}
	
    @Override
    public boolean supports(Class<?> clazz) {
        return ChangePassword.class.equals(clazz);
    }
 
    @Override
    public void validate(Object obj, Errors errors) {
    	System.out.println("inside Validate");
        ChangePassword changePassword = (ChangePassword) obj;
        if (checkInputString(changePassword.getCode())) {
            errors.rejectValue("code", ChangePasswordError.CODEEMPTY.toString());
        }
        if (checkInputString(changePassword.getPassword())) {
            errors.rejectValue("password", ChangePasswordError.PASSWORDEMPTY.toString());
        }
        if (checkCodePresence(changePassword.getCode())){
        	errors.rejectValue("code", ChangePasswordError.CODENOTFOUND.toString());
        }
        if( checkIfHourPassed(changePassword.getCode())){
        	System.out.println("reject HOURPASSED");
        	errors.rejectValue("code", ChangePasswordError.HOURPASSED.toString());
        }
    }
 
    private boolean checkInputString(String input) {
        return (input == null || input.trim().length() == 0);
    }

    private boolean checkCodePresence(String input){
    	NewPasswordCode newPasswordCode = newPasswordCodeRepository.findByCode(input);
    	return !Optional.ofNullable(newPasswordCode).isPresent();
    }
    
    private boolean checkIfHourPassed(String input){
    	NewPasswordCode newPasswordCode = newPasswordCodeRepository.findByCode(input);
    	if(Optional.ofNullable(newPasswordCode).isPresent()){
        	LocalDateTime timeEmailSent = newPasswordCode.getTime();
        	LocalDateTime hourAgo = LocalDateTime.now().minusHours(1);
        	return hourAgo.isAfter(timeEmailSent);    		
    	} else {
    		return false;
    	}
    	
    }
}
