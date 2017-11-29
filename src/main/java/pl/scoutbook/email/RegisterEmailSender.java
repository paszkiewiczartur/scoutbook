package pl.scoutbook.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pl.scoutbook.service.EmailServiceImpl;

@Component
public class RegisterEmailSender {
	private final String emailSubject = "Rejestracja w Scoutbook!";
	private final String emailContent = "Ta wiadomość została wygenerowana automatycznie. "
			+ "Ten adres email będzie służył do odzyskania hasła"
			+ " w razie jego utraty. Prosimy nie odpowiadać na tę wiadomość.";
	private final String emailAttachment = "C:/work/spring/workspace/scoutbook/src/main/resources/krzyz.jpg";
	@Autowired
	private EmailServiceImpl emailService;
	
	public void sendSimpleEmail(String sendTo){
		emailService.sendSimpleMessage(sendTo, emailSubject, emailContent);
	}
	
	public void sendEmailWithAttachment(String sendTo){
		
		emailService.sendMessageWithAttachment(sendTo, emailSubject, emailSubject, emailAttachment);
	}
}
