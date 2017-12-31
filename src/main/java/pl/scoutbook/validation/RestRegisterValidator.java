package pl.scoutbook.validation;

import java.time.LocalDate;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import pl.scoutbook.entities.User;
import pl.scoutbook.model.RegisterDTO;
import pl.scoutbook.repository.UserRepository;


@Component("beforeCreateWebsiteRegisterValidator")
public class RestRegisterValidator implements Validator {
	
	UserRepository userRepo;
	
	public RestRegisterValidator(UserRepository userRepo){
		this.userRepo = userRepo;
	}
	
    @Override
    public boolean supports(Class<?> clazz) {
        return RegisterDTO.class.equals(clazz);
    }
 
    @Override
    public void validate(Object obj, Errors errors) {
        RegisterDTO registerDTO = (RegisterDTO) obj;
        if (checkInputString(registerDTO.getFirstname())) {
            errors.rejectValue("firstname", RegisterError.FIRSTNAMEEMPTY.toString());
        }
        if (checkInputString(registerDTO.getLastname())) {
            errors.rejectValue("lastname", RegisterError.LASTNAMEEMPTY.toString());
        }
        if (checkInputString(registerDTO.getPassword())){
        	errors.rejectValue("password", RegisterError.PASSWORDEMPTY.toString());
        }
        if (checkInputString(registerDTO.getEmail())){
        	errors.rejectValue("email", RegisterError.EMAILEMPTY.toString());
        }
        if (checkEmailPattern(registerDTO.getEmail())){
        	errors.rejectValue("email", RegisterError.EMAILWRONGPATTERN.toString());
        }
        if (checkDuplicate(registerDTO.getEmail())) {
        	errors.rejectValue("email", RegisterError.EMAILDUPLICATE.toString());
        }
        if (registerDTO.getGender() == null) {
            errors.rejectValue("gender", RegisterError.GENDEREMPTY.toString());
        }
        if (registerDTO.getBirthday() == null) {
            errors.rejectValue("birthday", RegisterError.BIRTHDAYEMPTY.toString());
        }
        if (checkAge(registerDTO.getBirthday())){
        	errors.rejectValue("birthday", RegisterError.BIRTHDAYNOTPAST.toString());
        }    
    }
 
    private boolean checkInputString(String input) {
        return (input == null || input.trim().length() == 0);
    }

    private boolean checkEmailPattern(String input){
    	Pattern emailRegex = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = emailRegex .matcher(input);
        return !matcher.find();	
    }

    private boolean checkDuplicate(String input) {
    	User user = userRepo.findByEmail(input);
        return Optional.ofNullable(user).isPresent();
    }

    private boolean checkAge(LocalDate input){
    	LocalDate today = LocalDate.now();
    	return input.isAfter(today);
    }
}
