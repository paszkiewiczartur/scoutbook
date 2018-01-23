package pl.scoutbook.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

import pl.scoutbook.entities.SavedMessage;
import pl.scoutbook.model.ChatMessage;
import pl.scoutbook.model.Notification;
import pl.scoutbook.repository.SavedMessagesRepository;
import pl.scoutbook.service.ConversationService;
import pl.scoutbook.service.GeneratorService;

@Controller
public class MessengerController {	
	@Autowired
	private SimpMessagingTemplate messaging;
	
	@Autowired
	private SavedMessagesRepository savedMessagesRepository;

	@Autowired
	private ConversationService conversationService;
	
	@Autowired
	private GeneratorService generator;
	
	@SubscribeMapping("/subscription")
	public Notification subscription(Principal principal){
		System.out.println("-------------------");
		System.out.println("Użytkownik: "+principal.getName()+" rozpoczął subskrypcję.");
		return new Notification("Rozpoczęto subskrybcję.");
	}
	
	@MessageMapping("/messenger")
	public void chatMessenger(Principal principal, ChatMessage message){
		if(message.getUser() == Long.valueOf(0)){
			String principle = generator.generatePrinciple();
			messaging.convertAndSendToUser(principal.getName(), "/queue/receive", 
					new ChatMessage(message.getUser(), principle));
		} else {
			Long user = takeId(principal.getName());
			messaging.convertAndSendToUser("user"+message.getUser(), "/queue/receive",
					new ChatMessage(user, message.getMessage()));			

			Long conversation = conversationService.getConversationId(message.getUser(), user);
			if(conversation != null){
				if(message.getMessage().length() > 1000){
					System.out.println("Message is too long");
				} else{
					SavedMessage savedMessage = new SavedMessage();
					savedMessage.setConversation(conversation);
					savedMessage.setUser(user);
					savedMessage.setMessage(message.getMessage());
					savedMessagesRepository.save(savedMessage);
				}
			}
		}
	}
	
	private Long takeId(String principal){
		String userId = principal.substring(4, principal.length());
		return Long.parseLong(userId);
	}
}
