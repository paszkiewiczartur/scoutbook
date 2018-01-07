package pl.scoutbook.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pl.scoutbook.service.EmailServiceImpl;

@Component
public class RetrieveEmailSender {
	private final String emailSubject = "Odzyskiwanie hasła w Scoutbook!";
	private final String emailContentBegin = "<html><body>Aby odzyskać hasło kliknij w ten link: <a href=\"http://localhost:8080/changePassword/";
	private final String emailContentEnd = "\">Scoutbook</a>"
			+ "<br>Ta wiadomość została wygenerowana automatycznie. Prosimy nie odpowiadać na tę wiadomość.</body></html>";
	private final String imageName = "logo";
	//private final String imagePath = "C:/work/spring/workspace/scoutbook/src/main/resources/logo.png";
	private final String imagePath = "logo.png";
	private final String emailHTMLBegin = "<html><body><img src=\"cid:" + imageName + "\"/><br>"
			+ "Aby odzyskać hasło kliknij w ten link: <a href=\"http://145.239.90.229:8080/changePassword/";
	private final String emailHTMLEnd = "\">http://Scoutbook/changePassword</a>"
			+ "<br>Ta wiadomość została wygenerowana automatycznie. Prosimy nie odpowiadać na tę wiadomość.</body></html>";
	
	@Autowired
	private EmailServiceImpl emailService;
	
	public void sendSimpleEmail(String sendTo, String code){
		String emailContent = emailContentBegin + code + emailContentEnd;
		emailService.sendSimpleMessage(sendTo, emailSubject, emailContent);
	}
	
	public void sendHTMLEmailWithAttachment(String sendTo, String code){
		String emailContent = emailHTMLBegin + code + emailHTMLEnd;
		
		emailService.sendHTMLMessageWithAttachment(sendTo, emailSubject, emailContent, imagePath, imageName);
	}
}
