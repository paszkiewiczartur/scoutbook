package pl.scoutbook.controller;

import java.time.Duration;
import java.time.LocalDateTime;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import pl.scoutbook.email.CodeGenerator;
import pl.scoutbook.email.RetrieveEmailSender;
import pl.scoutbook.model.ChangePassword;
import pl.scoutbook.model.NewPasswordCode;
import pl.scoutbook.model.RetrievePasswordMessage;
import pl.scoutbook.model.User;
import pl.scoutbook.repository.NewPasswordCodeRepository;
import pl.scoutbook.repository.UserRepository;

@Controller
public class RetrievePasswordController {

	@Autowired
	private RetrieveEmailSender emailSender;
	
	@Autowired
	private NewPasswordCodeRepository newPasswordRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@PostMapping
	@RequestMapping("/api/retrievePassword")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void retrievePassword(@Valid @RequestBody RetrievePasswordMessage message) {
		String code = CodeGenerator.generateCode();
		NewPasswordCode newPasswordCode = new NewPasswordCode();
		newPasswordCode.setCode(code);
		newPasswordCode.setEmail(message.getEmail());
		newPasswordRepository.save(newPasswordCode);
		emailSender.sendSimpleEmail(message.getEmail(), code);
    }

	@PostMapping
	@RequestMapping("/api/changePassword")
	@ResponseStatus(HttpStatus.ACCEPTED)
    public void changePassword(@Valid @RequestBody ChangePassword changePassword) {
		String code = changePassword.getCode();
		NewPasswordCode newPasswordCode = newPasswordRepository.findByCode(code);
		String email = newPasswordCode.getEmail();
		User user = userRepository.findByEmail(email);
		user.setPassword(changePassword.getPassword());
		userRepository.save(user);
	}

}