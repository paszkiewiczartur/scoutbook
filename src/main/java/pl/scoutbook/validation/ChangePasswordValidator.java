package pl.scoutbook.validation;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import pl.scoutbook.model.ChangePassword;
import pl.scoutbook.model.NewPasswordCode;
import pl.scoutbook.repository.NewPasswordCodeRepository;


@Component("beforeCreateWebsiteChangePasswordValidator")
public class ChangePasswordValidator implements Validator {
	
	@Autowired
	NewPasswordCodeRepository newPasswordCodeRepository;
	
	public ChangePasswordValidator(){}
	
    @Override
    public boolean supports(Class<?> clazz) {
        return ChangePassword.class.equals(clazz);
    }
 
    @Override
    public void validate(Object obj, Errors errors) {
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
        	errors.rejectValue("time", ChangePasswordError.HOURPASSED.toString());
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
    	LocalDateTime timeEmailSent = newPasswordCode.getTime();
    	LocalDateTime hourAgo = LocalDateTime.now().minusMinutes(1);
    	Duration duration = Duration.between(hourAgo, timeEmailSent);
    	System.out.println("duration.getSeconds():" + duration.getSeconds());
    	System.out.println("duration.toMinutes():" + duration.toMinutes());
    	System.out.println("hourAgo.isAfter(timeEmailSent)" + hourAgo.isAfter(timeEmailSent));
    	return hourAgo.isAfter(timeEmailSent);
    }
}
