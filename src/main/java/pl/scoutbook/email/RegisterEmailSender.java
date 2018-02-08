package pl.scoutbook.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import pl.scoutbook.service.EmailServiceImpl;

@Component
public class RegisterEmailSender {
    @Autowired
	private EmailServiceImpl emailService;
    
    @Value("${registerEmailSubject}")
	private String emailSubject;
    @Value("${registerEmailImageName}")
	private String imageName;
	@Value("${registerEmailImagePath}")
	private String imagePath;
	@Value("${registerAttachmentEmailPattern}")
	private String attachmentEmailPattern;
	
	public void sendHTMLEmailWithAttachment(String sendTo){
		String emailContent = attachmentEmailContent();
		emailService.sendHTMLMessageWithAttachment(sendTo, emailSubject, emailContent, imagePath, imageName);
	}

	public String attachmentEmailContent(){
		return String.format(attachmentEmailPattern, imageName);
	}

}
