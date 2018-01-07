package pl.scoutbook.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.scoutbook.entities.Conversation;
import pl.scoutbook.repository.ConversationsRepository;

@Service
public class ConversationService {
	@Autowired
	ConversationsRepository conversationsRepository;
	
	public Long getConversationId(Long user, Long friend){
		Conversation conversation = null;
		conversation = conversationsRepository.findByUserAndFriend(user, friend);
		if(conversation == null){
			conversation = conversationsRepository.findByUserAndFriend(friend, user);
		}
		if(conversation != null){
			return conversation.getId();
		} else {
			return null;
		}
				
	}
}
