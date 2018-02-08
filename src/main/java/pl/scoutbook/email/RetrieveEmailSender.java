package pl.scoutbook.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import pl.scoutbook.service.EmailServiceImpl;

@Component
public class RetrieveEmailSender {
	
	@Autowired
	private EmailServiceImpl emailService;
	
	@Value("${hostAddress}")
	private String host;
	@Value("${retrieveEmailSubject}")
    private String emailSubject;
	@Value("${retrieveSimpleEmailPattern}")
	private String simpleEmailPattern;
    @Value("${retrieveEmailAttachment}")
    private String emailAttachment;	
    @Value("${retrieveEmailPattern}")
	private String attachmentEmailPattern;

	
	public void sendSimpleEmail(String sendTo, String code){
		String emailContent = simpleEmailContent(code);
		emailService.sendSimpleMessage(sendTo, emailSubject, emailContent);
	}
	
	public void sendEmailWithAttachment(String sendTo, String code){
		String emailContent = attachmentEmailContent(code);
		emailService.sendMessageWithAttachment(sendTo, emailSubject, emailContent, emailAttachment);
	}
	
	public String simpleEmailContent(String code){
		return String.format(simpleEmailPattern, host, code);
	}

	public String attachmentEmailContent(String code){
		return String.format(attachmentEmailPattern, host, code);
	}
}
